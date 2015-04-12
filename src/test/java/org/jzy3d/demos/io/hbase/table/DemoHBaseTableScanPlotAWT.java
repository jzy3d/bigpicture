package org.jzy3d.demos.io.hbase.table;

import java.util.List;

import org.jzy3d.demos.BigPicture;
import org.jzy3d.demos.vbo.barmodel.builder.VBOBuilderTableColumnsScatter3d;
import org.jzy3d.io.KeyVal;
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
public class DemoHBaseTableScanPlotAWT {
    static {
        LoggerUtils.minimal();
    }

    public static void main(String[] args) throws Exception {
        // read from HBase table
        HBaseIO hbase = new HBaseIO();
        List<List<KeyVal<String, Float>>> rows = hbase.scanRows(DemoHBaseTableGenerate.TABLE);
        
        // draw data
        DrawableVBO drawable2 = new DrawableVBO(new VBOBuilderTableColumnsScatter3d(rows));
        BigPicture.chart(drawable2, BigPicture.Type.dd).black();
    }
}
