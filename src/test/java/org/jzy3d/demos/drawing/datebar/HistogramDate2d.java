package org.jzy3d.demos.drawing.datebar;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jzy3d.chart.Chart;
import org.jzy3d.colors.Color;
import org.jzy3d.demos.drawing.datebar.HistogramDate.DateRange;
import org.jzy3d.demos.drawing.datebar.HistogramDate.TimeMode;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.AbstractComposite;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Polygon;
import org.jzy3d.plot3d.primitives.axes.AxeBox;
import org.jzy3d.plot3d.primitives.axes.layout.IAxeLayout;
import org.jzy3d.plot3d.primitives.axes.layout.providers.RegularTickProvider;
import org.jzy3d.plot3d.primitives.axes.layout.providers.StaticTickProvider;
import org.jzy3d.plot3d.primitives.axes.layout.renderers.DateTickRenderer;
import org.jzy3d.plot3d.transform.space.SpaceTransformLog;
import org.jzy3d.plot3d.transform.space.SpaceTransformer;

/**
 * Build a drawable set of bar with id of date range as X position, and number
 * of item as bar height.
 * 
 * Draw vertical date label by adding {@link AxeTextAnnotation} (usual X ticks
 * are disabled).
 * 
 * 
 * @author martin
 *
 */
public class HistogramDate2d {
    static DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyy/MM/dd");
    static DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("hh:mm:ss");
    static DateTimeFormatter DATETIME_FORMATTER = DateTimeFormat.forPattern("yyyy/MM/dd hh:mm:ss");

    protected HistogramDate model;
    protected AbstractComposite drawable;

    protected Color bodyColor = Color.MAGENTA;
    protected Color borderColor = Color.BLACK;

    public HistogramDate2d(HistogramDate model) {
        setModel(model);
    }

    public HistogramDate2d(HistogramDate model, Color bodyColor, Color borderColor) {
        this.bodyColor = bodyColor;
        this.borderColor = borderColor;
        this.setModel(model);
    }

    /* MAKE LAYOUT WITH DATE LABEL ANNOTATIONS ON AXE */
    
    /** Set global chart view settings to best draw this histogram. */
    public void layout(Chart chart) {
        AxeBox axe = (AxeBox) chart.getView().getAxe();
        IAxeLayout axeLayout = chart.getAxeLayout();

        int ymax = getModel().computeMaxCount();
        double[] ticks = { 0, ymax / 4, ymax / 2, ymax / 2 + ymax / 4, ymax };
        int n = 5;
        
        layoutAxeLabels(axeLayout, DATE_FORMATTER.print(getCentralDateRange().min));
        layoutAxeDateAnnotations(axe, n);
        layoutAxeX(axeLayout);
        layoutAxeY(axeLayout, ticks);
        layoutAxeColor(axeLayout);
        
        // LOG transform not working :/
        boolean log = false;
        if (log) {
            SpaceTransformer spaceTransformer = new SpaceTransformer(null, new SpaceTransformLog(), null);
            axe.setSpaceTransformer(spaceTransformer);
            chart.getView().setSpaceTransformer(spaceTransformer);
        }
    }

    private DateRange getCentralDateRange() {
        DateRange center = model.ranges[model.ranges.length / 2];
        return center;
    }

    private void layoutAxeY(IAxeLayout axeLayout, double[] ticks) {
        axeLayout.setYTickProvider(new StaticTickProvider(ticks));
    }

    private void layoutAxeX(IAxeLayout axeLayout) {
        axeLayout.setXTickLabelDisplayed(false);
        axeLayout.setTickLineDisplayed(true);
    }

    private void layoutAxeDateAnnotations(AxeBox axe, int n) {
        for (int i = 0; i < model.ranges().length; i++) {
            if (i % n != 0)
                continue;
            DateRange range = model.ranges()[i];
            String s = DATETIME_FORMATTER.print(range.min);
            AxeTextAnnotation ata = new AxeTextAnnotation(s, new Coord3d(i, 0, 0));
            axe.addAnnotation(ata);
        }
    }

    private void layoutAxeColor(IAxeLayout axeLayout) {
        axeLayout.setGridColor(Color.GRAY);
        axeLayout.setXTickColor(Color.GRAY);
        axeLayout.setYTickColor(Color.GRAY);
    }

    private void layoutAxeLabels(IAxeLayout axeLayout, String dayString) {
        if (TimeMode.SECOND.equals(model.timeMode)) {
            axeLayout.setXAxeLabel(dayString);
        }
        axeLayout.setYAxeLabel("Count");
    }
    
    /* MAKE DRAWABLE */

    protected AbstractComposite buildDrawable(HistogramDate model) {
        AbstractComposite c = new AbstractComposite() {
        };
        for (int i = 0; i < model.ranges().length; i++) {
            DateRange range = model.ranges()[i];
            int count = model.getCount(i);
            Polygon p = makeCountBar(i, range, count);
            c.add(p);
        }
        return c;
    }

    /**
     * Using timestamp as long value casted to float yield to inaccurate
     * conversion that make bars have xmin and xmax overlapping, so we use bar
     * ID and AxeTextAnnotation to label id with bin date start.
     */
    private Polygon makeCountBar(int i, DateRange range, int count) {
        double z = 0;
        double xmin = i;// range.min.getMillis();
        double xmax = i + 1;// range.max.getMillis();
        //consoleRange(range, count, xmin, xmax);

        Coord3d c1 = new Coord3d(xmin, 0, z);
        Coord3d c2 = new Coord3d(xmin, count, z);
        Coord3d c3 = new Coord3d(xmax, count, z);
        Coord3d c4 = new Coord3d(xmax, 0, z);

        Polygon p = makeCountBarPolygon(c1, c2, c3, c4);
        return p;
    }

    private Polygon makeCountBarPolygon(Coord3d c1, Coord3d c2, Coord3d c3, Coord3d c4) {
        Polygon p = new Polygon();
        p.add(new Point(c1));
        p.add(new Point(c2));
        p.add(new Point(c3));
        p.add(new Point(c4));
        p.setColor(bodyColor);
        p.setWireframeColor(borderColor);
        return p;
    }

    private void consoleRange(DateRange range, int count, double xmin, double xmax) {
        if (xmax - xmin == 0)
            System.err.println((xmax - xmin) + "\t" + range.min.toString() + " " + range.max + "\t" + (range.max.getMillis() - range.min.getMillis()) + "\t" + xmin + "\t" + count);
        else
            System.out.println((xmax - xmin) + "\t" + range.min.toString() + " " + range.max + "\t" + (range.max.getMillis() - range.min.getMillis()) + "\t" + xmin + "\t" + count);
    }

    /* */

    public void addTo(Chart chart) {
        chart.add(drawable);
        layout(chart);
    }

    public void setModel(HistogramDate model) {
        this.model = model;
        this.drawable = buildDrawable(model);
    }

    public HistogramDate getModel() {
        return model;
    }

    public AbstractComposite getDrawable() {
        return drawable;
    }

    public Color getBodyColor() {
        return bodyColor;
    }

    public void setBodyColor(Color bodyColor) {
        this.bodyColor = bodyColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
}
