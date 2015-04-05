package org.jzy3d.demos.vbo.bar;

import java.util.List;

import javax.media.opengl.GL;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.controllers.keyboard.lights.AWTLightKeyController;
import org.jzy3d.chart.controllers.keyboard.lights.NewtLightKeyController;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.demos.vbo.scatter.ScatterGenerator;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.AbstractDrawable;
import org.jzy3d.plot3d.primitives.CompileableComposite;
import org.jzy3d.plot3d.primitives.axes.layout.IAxeLayout;
import org.jzy3d.plot3d.primitives.vbo.BarVBO;
import org.jzy3d.plot3d.primitives.vbo.DrawableVBO;
import org.jzy3d.plot3d.primitives.vbo.ListCoord3dVBOLoader;
import org.jzy3d.plot3d.primitives.vbo.ScatterVBO;
import org.jzy3d.plot3d.rendering.canvas.CanvasAWT;
import org.jzy3d.plot3d.rendering.canvas.CanvasNewtAwt;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.lights.Light;
import org.jzy3d.plot3d.rendering.view.View;
import org.jzy3d.plot3d.rendering.view.ViewportMode;
import org.jzy3d.plot3d.rendering.view.modes.ViewPositionMode;

/**
 * 
 * http://www.songho.ca/opengl/gl_vbo.html
 * 
 * 
 * http://www.felixgers.de/teaching/jogl/vertexBufferObject.html
 * - verify VBO available
 */
public class DemoBarVBO {
    public static int MILION = 1000000;

    public static void main(String[] args) {
        float ratio = 5f;
        int size = (int) (ratio * MILION);
        List<Coord3d> coords = ScatterGenerator.getScatter(size);
        ColorMapper coloring = ScatterGenerator.coloring(coords);
        BarVBO bars = new BarVBO(new RandomBarVBOLoader(100));
        chart(bars);
    }

    public static Chart chart(AbstractDrawable drawable) {
        Chart chart = AWTChartComponentFactory.chart(Quality.Intermediate, "awt");
        chart.getScene().getGraph().add(drawable);
        chart.getView().setBackgroundColor(Color.BLACK);
        chart.getAxeLayout().setMainColor(Color.WHITE);
        chart.getView().getCamera().setViewportMode(ViewportMode.STRETCH_TO_FILL);
        chart.addMouseController();
        chart.open("VBO Scatter demo", 1000, 1000);
        //layout2d(chart);
        return chart;
    }
    
    public static void layout2d(Chart chart) {
        View view = chart.getView();
        view.setViewPositionMode(ViewPositionMode.TOP);
        view.getCamera().setViewportMode(ViewportMode.STRETCH_TO_FILL);
        IAxeLayout axe = chart.getAxeLayout();
        axe.setZAxeLabelDisplayed(false);
        axe.setTickLineDisplayed(false);
    }
}
