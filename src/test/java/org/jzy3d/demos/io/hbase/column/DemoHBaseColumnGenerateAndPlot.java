package org.jzy3d.demos.io.hbase.column;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jzy3d.chart.Chart;
import org.jzy3d.demos.BigPicture;
import org.jzy3d.demos.drawing.vbo.barmodel.builder.VBOBuilderLineStrip;
import org.jzy3d.maths.Histogram;
import org.jzy3d.maths.Statistics;
import org.jzy3d.plot2d.primitives.Histogram2d;
import org.jzy3d.plot3d.primitives.vbo.drawable.DrawableVBO;

/**
 * Draws key-values of each row, where each column name is mapped to Y and a color, and each column value is mapped to Z.
 * @author martin
 *
 */
public class DemoHBaseColumnGenerateAndPlot{
    static String folder = "./data/screenshots/" + DemoHBaseColumnGenerateAndPlot.class.getSimpleName() + "/";
    static String f1 = folder + "hist.png";
    static String f2 = folder + "line.png";
    
    public static void main(String[] args) throws IOException {
        List<Float> rows = new ArrayList<Float>(100);
        for (int i = 0; i < 100; i++) {
            rows.add((float)Math.random());
        }
        File f = new File(f2);
        f.getParentFile().mkdirs();
        //line(rows);
        histogram(rows).screenshot(f);//.open("", 800, 800);
    }

    private static Chart histogram(List<Float> values) {
        // Density 
        float min = Statistics.min(values);
        float max = Statistics.max(values);

        Histogram model = new Histogram(min,max,10);
        
        for(Float value: values){
            model.add(value);
        }
        Histogram2d histogram = new Histogram2d(model);
        
        //Chart chart = AWTChartComponentFactory.chart("offscreen,600,600").black().view2d();
        Chart chart = BigPicture.offscreen(histogram.getDrawable(), BigPicture.Type.dd, 600,600);
        histogram.addTo(chart);
        return chart;
    }

    private static void line(List<Float> rows) {
        DrawableVBO drawable = new DrawableVBO(new VBOBuilderLineStrip(rows));
        BigPicture.chart(drawable, BigPicture.Type.dd).black();
    }
    
    public static Histogram testHistAdd(){
        Histogram h = new Histogram(0, 1, 10);
        for (int i = 0; i < 1000; i++) {
            h.add((float)Math.random());
        }
        return h;
    }

}
