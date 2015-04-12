package org.jzy3d.demos.io.hbase.column;

import java.util.List;

import org.jzy3d.demos.BigPicture;
import org.jzy3d.demos.vbo.barmodel.builder.VBOBuilderLineStrip;
import org.jzy3d.io.hbase.HBaseIO;
import org.jzy3d.plot3d.primitives.vbo.drawable.DrawableVBO;

/**
 * Draws key-values of each row, where each column name is mapped to Y and a color, and each column value is mapped to Z.
 * @author martin
 *
 */
public class DemoHBaseColumnChartAWT{
    public static void main(String[] args) {
        //String columnName = "0.pivot.id";
        String columnName = "4pivot.col4";

        HBaseIO hbase = new HBaseIO();
        List<Float> rows = hbase.scanColumn(DemoHBaseColumnGenerate.TABLE, DemoHBaseColumnGenerate.FAMILY, columnName);

        DrawableVBO drawable = new DrawableVBO(new VBOBuilderLineStrip(rows));
        BigPicture.chartBlack(drawable, BigPicture.Type.dd);
    }
}
