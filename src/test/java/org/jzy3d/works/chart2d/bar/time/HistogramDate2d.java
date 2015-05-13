package org.jzy3d.works.chart2d.bar.time;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jzy3d.chart.Chart;
import org.jzy3d.colors.Color;
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
import org.jzy3d.works.chart2d.bar.time.HistogramDate.DateRange;
import org.jzy3d.works.chart2d.bar.time.HistogramDate.TimeMode;

public class HistogramDate2d {
    static DateTimeFormatter DAY_FORMATTER = DateTimeFormat.forPattern("yyyy/MM/dd");

    protected HistogramDate model;
    protected AbstractComposite drawable;
    
    public HistogramDate2d(HistogramDate model) {
        setModel(model);
    }
    
    /** Set global chart view settings to best draw this histogram. */
    public void layout(Chart chart){
        IAxeLayout layout = chart.getAxeLayout();
        int ymax = getModel().computeMaxCount();
        double[] ticks = {0, ymax/4, ymax/2, ymax/2 + ymax/4, ymax};
        layout.setYTickProvider(new StaticTickProvider(ticks));

        DateRange center = model.ranges[model.ranges.length/2];
        String dayString = DAY_FORMATTER.print(center.min);
        
        if (TimeMode.DAY.equals(model.timeMode)) {
            layout.setXTickProvider(new RegularTickProvider(5));
            layout.setXTickRenderer(new DateTickRenderer("yyyy/MM/dd HH:mm:ss"));
            layout.setXAxeLabel("Date");
        } else if (TimeMode.HOUR.equals(model.timeMode)) {
            layout.setXTickProvider(new RegularTickProvider(10));
            layout.setXTickRenderer(new DateTickRenderer("HH:mm:ss"));
            layout.setXAxeLabel(dayString);
        } else if (TimeMode.MINUTE.equals(model.timeMode)) {
            layout.setXTickProvider(new RegularTickProvider(10));
            layout.setXTickRenderer(new DateTickRenderer("HH:mm:ss"));
            layout.setXAxeLabel(dayString);
        } else if (TimeMode.SECOND.equals(model.timeMode)) {
            layout.setXTickProvider(new RegularTickProvider(10));
            layout.setXTickRenderer(new DateTickRenderer("HH:mm:ss"));
            layout.setXAxeLabel(dayString);
        }

        layout.setYAxeLabel("Count");
        
        /*SpaceTransformer spaceTransformer = new SpaceTransformer(null, new SpaceTransformLog(), null);
        AxeBox axe = (AxeBox)chart.getView().getAxe();
        axe.setSpaceTransformer(spaceTransformer);
        chart.getView().setSpaceTransformer(spaceTransformer);*/
    }
    
    public void addTo(Chart chart){
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

    protected AbstractComposite buildDrawable(HistogramDate model){
        AbstractComposite c = new AbstractComposite() {
        };
        for (int i = 0; i < model.ranges().length; i++) {
            DateRange range = model.ranges()[i];
            int count = model.getCount(i);
            
            Polygon p = makeCountBar(range, count);
            c.add(p);
        }
        return c;
    }

    private Polygon makeCountBar(DateRange range, int count) {
        float z = 0;
        float min = 0;//1431463260000f;//E12
        float div = 1;//1431463260000f;//10000000000f;
        
        float xmin = (range.min.getMillis()-min)/div;
        float xmax = (range.max.getMillis()-min)/div;
        
        Coord3d c1 = new Coord3d(xmin, 0, z);
        Coord3d c2 = new Coord3d(xmin, count, z);
        Coord3d c3 = new Coord3d(xmax, count, z);
        Coord3d c4 = new Coord3d(xmax, 0, z);

        Polygon p = new Polygon();
        p.add(new Point(c1));
        p.add(new Point(c2));
        p.add(new Point(c3));
        p.add(new Point(c4));
        p.setColor(Color.MAGENTA);
        p.setWireframeColor(Color.WHITE);
        return p;
    }
}
