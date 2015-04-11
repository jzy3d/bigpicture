package org.jzy3d.demos.vbo.barmodel;

import java.util.List;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.demos.vbo.barmodel.builder.VBOBuilderBarModel;
import org.jzy3d.demos.vbo.barmodel.model.KeyVal;
import org.jzy3d.plot3d.primitives.AbstractDrawable;
import org.jzy3d.plot3d.primitives.axes.layout.IAxeLayout;
import org.jzy3d.plot3d.primitives.vbo.drawable.BarVBO;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.view.View;
import org.jzy3d.plot3d.rendering.view.ViewportMode;
import org.jzy3d.plot3d.rendering.view.modes.ViewPositionMode;

public class DemoColumnGroups {
    public static int MILION = 1000000;

    public static void main(String[] args) {
        //rows7();
        
        int nPivot = MILION/10;
        int nPivotTheme = 3;
        int nPivotCol = 35 * nPivotTheme;
        int nCpCcCat = 12;
        int nCpCcCol = 2;
        
        KeyValueGenerator d = new KeyValueGenerator();
        List<List<KeyVal<String,Float>>> rows = d.vip(nPivot, nPivotCol, nCpCcCat, nCpCcCol);
        
        BarVBO bars = new BarVBO(new VBOBuilderBarModel(rows));
        
        chart(bars);
    }

    

    /* */
    
    public static Chart chart(AbstractDrawable drawable) {
        Chart chart = AWTChartComponentFactory.chart(Quality.Intermediate, "awt");
        chart.getScene().getGraph().add(drawable);
        //chart.black();
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
