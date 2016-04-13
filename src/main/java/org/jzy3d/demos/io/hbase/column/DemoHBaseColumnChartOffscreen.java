package org.jzy3d.demos.io.hbase.column;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.jzy3d.chart.BigPicture;
import org.jzy3d.chart.Type;
import org.jzy3d.demos.drawing.vbo.barmodel.builder.VBOBuilderLineStrip;
import org.jzy3d.io.hbase.HBaseIO;
import org.jzy3d.plot3d.primitives.vbo.drawable.DrawableVBO;

/**
 * Draws key-values of each row, where each column name is mapped to Y and a color, and each column value is mapped to Z.
 * @author martin
 *
 */
public class DemoHBaseColumnChartOffscreen{
    public static void main(String[] args) throws IOException {
        HBaseIO hbase = new HBaseIO();

        resetScreenshots();
        
        
        Set<String> keys = hbase.scanColumnNames(DemoHBaseColumnGenerate.TABLE, DemoHBaseColumnGenerate.FAMILY);
        System.out.println(keys.size() + " columns");
        for(String k: keys){
            //System.out.println(k);
            columnScreenshot(hbase, k);
        }
        
        /*columnScreenshot(hbase, "0.pivot.id");
        columnScreenshot(hbase, "0pivot.col0");
        columnScreenshot(hbase, "1pivot.col1");
        columnScreenshot(hbase, "2pivot.col2");
        columnScreenshot(hbase, "4pivot.col4");*/
    }



    private static void resetScreenshots() {
        new File("./data/screenshots/columns/").delete();
        new File("./data/screenshots/columns/").mkdirs();
    }
    
    

    private static void columnScreenshot(HBaseIO hbase, String columnName) throws IOException {
        File f = new File("./data/screenshots/columns/" + columnName + ".png");
        
        List<Float> rows = hbase.scanColumn(DemoHBaseColumnGenerate.TABLE, DemoHBaseColumnGenerate.FAMILY, columnName);

        
        DrawableVBO drawable = new DrawableVBO(new VBOBuilderLineStrip(rows));
        BigPicture.screenshot(drawable, Type.dd, 2000, 1700, f);
    }
}
