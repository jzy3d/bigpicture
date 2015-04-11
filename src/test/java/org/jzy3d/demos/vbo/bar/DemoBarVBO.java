package org.jzy3d.demos.vbo.bar;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.plot3d.primitives.AbstractDrawable;
import org.jzy3d.plot3d.primitives.axes.layout.IAxeLayout;
import org.jzy3d.plot3d.primitives.vbo.drawable.BarVBO;
import org.jzy3d.plot3d.rendering.canvas.Quality;
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
        float ratio = .0005f;
        int size = (int) (ratio * MILION);
        BarVBO bars = new BarVBO(new VBOBuilderRandomBar(size));
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
        layout2d(chart);
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
