package org.jzy3d.chart.spark;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.spark.api.java.JavaRDD;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.javafx.JavaFXChartFactory;
import org.jzy3d.maths.Coord2d;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot2d.primitives.Serie2d;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.primitives.axes.layout.IAxeLayout;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.view.View;
import org.jzy3d.plot3d.rendering.view.ViewportMode;
import org.jzy3d.plot3d.rendering.view.modes.ViewBoundMode;
import org.jzy3d.plot3d.rendering.view.modes.ViewPositionMode;

public class SparkRDDChartBuilder {
    protected JavaFXChartFactory factory = new JavaFXChartFactory();
    protected AWTChart chart;

    protected Serie2d addSerie2d(final JavaRDD<Coord3d> coords, final String name, final Serie2d.Type type) {
        final Serie2d serie = factory.newSerie(name, type);
        serie.setWidth(1);
        draw(coords, name, type, serie);
        chart.addDrawable(serie.getDrawable());
        //drawInThread();
        return serie;
    }
    
    protected void draw(final JavaRDD<Coord3d> coords, final String name, final Serie2d.Type type, final Serie2d serie) {
        for(Coord3d item: coords.collect()){
            Coord2d coord = new Coord2d(item.x, item.y);
            serie.add(coord, Color.BLUE);
            //System.out.println(coord);
        }
        System.out.println("added " + coords.count() + " points in serie " + name + " " + type);
    }

    protected void drawInThread(final JavaRDD<Coord3d> coords, final String name, final Serie2d.Type type, final Serie2d serie) {
        Executor e = Executors.newFixedThreadPool(2);
        e.execute(new Runnable(){
            public void run() {
                draw(coords, name, type, serie);
            }
        });
    }

/* */
    
    public void layout2d() {
        IAxeLayout axe = chart.getAxeLayout();
        axe.setZAxeLabelDisplayed(false);
        axe.setTickLineDisplayed(false);

        View view = chart.getView();
        view.setViewPositionMode(ViewPositionMode.TOP);
        view.setSquared(true);
        view.getCamera().setViewportMode(ViewportMode.STRETCH_TO_FILL);
        view.setBoundMode(ViewBoundMode.AUTO_FIT);
    }
    
    protected AWTChart chart(JavaFXChartFactory factory, String toolkit) {
        Quality quality = Quality.Advanced;
        // quality.setSmoothPolygon(true);
        // quality.setAnimated(true);
        AWTChart chart = (AWTChart) factory.newChart(quality, toolkit);
        return chart;
    }

    protected Shape surface() {
        Mapper mapper = new Mapper() {
            public double f(double x, double y) {
                return x * Math.sin(x * y);
            }
        };
        Range range = new Range(-3, 3);
        int steps = 80;
        return surface(mapper, range, steps);
    }
    
    protected Shape surface(Mapper mapper, Range range, int steps) {
        // Create the object to represent the function over the given range.
        final Shape surface = Builder.buildOrthonormal(mapper, range, steps);
        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(false);
        return surface;
    }
}
