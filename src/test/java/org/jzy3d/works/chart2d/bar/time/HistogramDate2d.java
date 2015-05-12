package org.jzy3d.works.chart2d.bar.time;

import org.jzy3d.chart.Chart;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.AbstractComposite;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Polygon;
import org.jzy3d.plot3d.primitives.axes.layout.IAxeLayout;
import org.jzy3d.plot3d.primitives.axes.layout.providers.StaticTickProvider;
import org.jzy3d.plot3d.primitives.axes.layout.renderers.DateTickRenderer;
import org.jzy3d.works.chart2d.bar.time.HistogramDate.DateRange;

public class HistogramDate2d {
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
        layout.setXTickRenderer(new DateTickRenderer("HH:mm:ss"));
        layout.setYAxeLabel("Count");
        layout.setXAxeLabel("Date");
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
        Coord3d c1 = new Coord3d(range.min.getMillis()-min, 0, z);
        Coord3d c2 = new Coord3d(range.min.getMillis()-min, count, z);
        Coord3d c3 = new Coord3d(range.max.getMillis()-min, count, z);
        Coord3d c4 = new Coord3d(range.max.getMillis()-min, 0, z);

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
