package org.jzy3d.demos.vbo.barmodel;

import java.util.List;

import org.jzy3d.demos.BigPicture;
import org.jzy3d.demos.vbo.barmodel.builder.VBOBuilderColumnDatabase;
import org.jzy3d.demos.vbo.barmodel.model.KeyVal;
import org.jzy3d.maths.TicToc;
import org.jzy3d.plot3d.primitives.vbo.drawable.DrawableVBO;

public class DemoStarJoinColumnValues{
    public static int MILION = 1000000;

    public static void main(String[] args) {
        int nPivot = MILION/100;
        int nPivotTheme = 3;
        int nPivotCol = 35 * nPivotTheme;
        int nCpCcCat = 12;
        int nCpCcCol = 2;
        
        TicToc.T.tic();
        
        KeyValueGenerator generator = new KeyValueGenerator();
        List<List<KeyVal<String,Float>>> rows = generator.vip(nPivot, nPivotCol, nCpCcCat, nCpCcCol);
        DrawableVBO drawable = new DrawableVBO(new VBOBuilderColumnDatabase(rows));
        BigPicture.chart(drawable, BigPicture.Type.ddd);
        
        String sizing = "nPivot:" + nPivot + " nPivotCol:" + nPivotCol + " nCpCcCol:" + nCpCcCat * nCpCcCol;
        
        TicToc.T.tocShow("loading VBO in GPU | " + sizing);
    }
}
