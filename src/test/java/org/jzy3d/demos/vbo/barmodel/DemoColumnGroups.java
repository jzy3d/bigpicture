package org.jzy3d.demos.vbo.barmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
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

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        float ratio = .0005f;
        int size = (int) (ratio * MILION);
        
        
        /*for (int i = 0; i < size; i++) {
            int width = 10;
            List<KeyVal<String,Float>> row = new ArrayList<KeyVal<String,Float>>(width);
            for (int j = 0; j < width; j++) {
                row.add(new KeyVal<String, Float>("col" + j, 0f));
            }
            rows.add(row);
        }*/
        List<List<KeyVal<String,Float>>> rows = new ArrayList<List<KeyVal<String,Float>>>();
        rows.add(rw(kv("id", 0f), kv("value", 0f), kv("app1.id", 0f), kv("app1.value", 0f)));        
        rows.add(rw(kv("id", 1f), kv("value", 0f), kv("app1.id", 0f), kv("app1.value", 10f)));
        rows.add(rw(kv("id", 2f), kv("value", 0f), kv("app1.id", 0f), kv("app1.value", 20f)));
        rows.add(rw(kv("id", 3f), kv("value", 0f), kv("app1.id", 0f), kv("app1.value", 30f)));
        rows.add(rw(kv("id", 4f), kv("value", 0f), kv("app1.id", 0f), kv("app1.value", 40f)));
        rows.add(rw(kv("id", 5f), kv("value", 0f), kv("app1.id", 0f), kv("app1.value", 50f)));
        rows.add(rw(kv("id", 6f), kv("value", 0f), kv("app1.id", 0f), kv("app1.value", 60f)));
        
        BarVBO bars = new BarVBO(new VBOBuilderBarModel(rows));
        chart(bars);
    }
    public static KeyVal<String,Float> kv(String key,Float value){
        return new KeyVal<String, Float>(key, value);
    }    
    public static List<KeyVal<String,Float>> rw(KeyVal<String,Float>... keys){
        List<KeyVal<String,Float>> row = Arrays.asList(keys);//new ArrayList<KeyVal<String,Float>>();
        return row;
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
