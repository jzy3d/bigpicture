package org.jzy3d.demos;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.plot3d.primitives.AbstractDrawable;
import org.jzy3d.plot3d.primitives.axes.layout.IAxeLayout;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.view.View;
import org.jzy3d.plot3d.rendering.view.ViewportMode;
import org.jzy3d.plot3d.rendering.view.modes.ViewPositionMode;

public class BigPicture {
    public enum Type {
        /** 2D */
        dd, /** 3D */
        ddd
    }

    public static Chart chart(AbstractDrawable drawable, Type type) {
        return chart(drawable, type, false);
    }

    public static Chart chart(AbstractDrawable drawable, Type type, boolean black) {
        Chart chart = AWTChartComponentFactory.chart(Quality.Intermediate, "awt");
        chart.getScene().getGraph().add(drawable);
        if (black)
            chart.black();
        chart.getView().getCamera().setViewportMode(ViewportMode.STRETCH_TO_FILL);
        chart.addMouseController();
        chart.open("VBO Scatter demo", 1000, 1000);
        if (type.equals(Type.dd))
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
