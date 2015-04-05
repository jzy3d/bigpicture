package org.jzy3d.demos.spark;

import java.util.List;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.controllers.keyboard.lights.AWTLightKeyController;
import org.jzy3d.chart.controllers.keyboard.lights.NewtLightKeyController;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.CompileableComposite;
import org.jzy3d.plot3d.primitives.vbo.DrawableVBO;
import org.jzy3d.plot3d.primitives.vbo.ListCoord3dVBOLoader;
import org.jzy3d.plot3d.primitives.vbo.ScatterVBO;
import org.jzy3d.plot3d.rendering.canvas.CanvasAWT;
import org.jzy3d.plot3d.rendering.canvas.CanvasNewtAwt;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.lights.Light;
import org.jzy3d.plot3d.rendering.view.ViewportMode;

/**
 * Shows a scatter built with Vertex Buffer Object, an efficient way of storing
 * geometry in GPU at program startup.
 * 
 * In contrast, drawable object that are neither of type {@link DrawableVBO} or
 * {@link CompileableComposite} will require that processor to send drawing
 * instructions to the GPU at <i>each</i> rendering call, thus implying useless work
 * for geometry that should not change in shape or color over time.
 * 
 * One can easily creates its {@link ListCoord3dVBOLoader} to support its
 * own datamodel rather than jzy3d {@link Coord3d} elements.
 * 
 * @author Martin Pernollet
 *
 */
public class DemoScatterVBO {
    public static int MILION = 1000000;

    public static void main(String[] args) {
        float ratio = .5f;
        int size = (int) (ratio * MILION);

        // Data
        List<Coord3d> coords = Generator.getScatter(size);
        ColorMapper coloring = Generator.coloring(coords);

        // Geometry
        ScatterVBO scatter = new ScatterVBO(new ListCoord3dVBOLoader(coords, coloring));
        scatter.rotator(false); 

        // Chart
        chart(scatter);
    }

    public static Chart chart(ScatterVBO scatter) {
        Quality quality = Quality.Intermediate;
        Chart chart = AWTChartComponentFactory.chart(quality, "awt");
        chart.getScene().getGraph().add(scatter);
        // addLight(chart, new Coord3d(5f, 5f, 50f), Color.BLUE, Color.WHITE,
        // new Color(0.8f, 0.8f, 0.8f));
        chart.getView().setBackgroundColor(Color.BLACK);
        chart.getAxeLayout().setMainColor(Color.WHITE);
        chart.getView().getCamera().setViewportMode(ViewportMode.STRETCH_TO_FILL);
        chart.addMouseController();
        chart.open("VBO Scatter demo", 800, 800);
        return chart;
    }

    public static Light addLight(Chart chart, Coord3d position, Color ambiantColor, Color specularColor, Color diffuseColor) {
        Light light = new Light();
        light.setPosition(position);
        light.setAmbiantColor(ambiantColor);
        light.setDiffuseColor(diffuseColor);
        light.setSpecularColor(specularColor);
        light.setRepresentationRadius(1);
        chart.getScene().add(light);
        if (chart.getCanvas() instanceof CanvasAWT)
            chart.getCanvas().addKeyController(new AWTLightKeyController(chart, light));
        else if (chart.getCanvas() instanceof CanvasNewtAwt)
            chart.getCanvas().addKeyController(new NewtLightKeyController(chart, light));
        return light;
    }
}
