package org.jzy3d.demos.io.hbase.table;

import java.util.List;

import org.jzy3d.demos.BigPicture;
import org.jzy3d.demos.vbo.barmodel.builder.VBOBuilderTableColumnsScatter3d;
import org.jzy3d.demos.vbo.barmodel.generators.GeneratorKeyValue;
import org.jzy3d.io.KeyVal;
import org.jzy3d.io.Progress;
import org.jzy3d.io.hbase.HBaseIO;
import org.jzy3d.plot3d.primitives.vbo.drawable.DrawableVBO;
import org.jzy3d.utils.LoggerUtils;

/**
 * Draws key-values of each row, where each column name is mapped to Y and a
 * color, and each column value is mapped to Z.
 * 
 * @author martin
 *
 */
public class DemoHBaseTableWriteReadPlotAWT {
    public static int MILLION = 1000000;
    public static String TABLE = DemoHBaseTableWriteReadPlotAWT.class.getSimpleName();
    public static String FAMILY = "demo";
    
    
    static {
        LoggerUtils.minimal();
    }

    public static void main(String[] args) throws Exception {
        int nRaws = MILLION / 100;
        int nPivotTheme = 8;
        int nPivotCol = 15 * nPivotTheme;
        int nCpCcCat = 12;
        int nCpCcCol = 10;

        // Generate table data
        GeneratorKeyValue generator = new GeneratorKeyValue();
        final List<List<KeyVal<String, Float>>> rows = generator.vip(nRaws, nPivotCol, nCpCcCat, nCpCcCol);

        // show generated table
        DrawableVBO drawable = new DrawableVBO(new VBOBuilderTableColumnsScatter3d(rows));
        BigPicture.chart(drawable, BigPicture.Type.ddd).black();

        // dump in HBase table
        String[] families = { FAMILY };
        HBaseIO hbase = new HBaseIO();
        hbase.tableDelete(TABLE);
        hbase.tableCreate(TABLE, families);
        hbase.putAll(rows, TABLE, FAMILY, progress());
        //hbase.scanPrint(TABLE);
        
        // clear memory, read what we previously dumped
        rows.clear();
        hbase.scanRows(TABLE, rows);
        
        // draw read data
        DrawableVBO drawable2 = new DrawableVBO(new VBOBuilderTableColumnsScatter3d(rows));
        BigPicture.chart(drawable2, BigPicture.Type.ddd).black();
    }

    private static Progress progress() {
        Progress progress = new Progress(){
            public void progress(int value) {
                if(value%1000==0)
                    System.out.println(value + " inserted");
            }
        };
        return progress;
    }
}
