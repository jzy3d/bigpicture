package org.jzy3d.demos.drawing.vbo.barmodel.generators;

import java.util.List;

import org.jzy3d.demos.drawing.vbo.barmodel.builder.VBOBuilderTableColumnsScatter3d;
import org.jzy3d.io.KeyVal;
import org.jzy3d.plot3d.primitives.vbo.drawable.DrawableVBO;

public class GeneratorStarJoin {
    public static int MILION = 1000000;
    
    public static DrawableVBO drawable(int n) {
        int nRaws = n;
        int nPivotTheme = 3;
        int nPivotCol = 35 * nPivotTheme;
        int nCpCcCat = 12;
        int nCpCcCol = 2;
        
        
        return drawable(nRaws, nPivotCol, nCpCcCat, nCpCcCol);
    }

    public static DrawableVBO drawable(int nRaws, int nPivotCol, int nCpCcCat, int nCpCcCol) {
        GeneratorKeyValue generator = new GeneratorKeyValue();
        List<List<KeyVal<String,Float>>> rows = generator.vip(nRaws, nPivotCol, nCpCcCat, nCpCcCol);
        DrawableVBO drawable = new DrawableVBO(new VBOBuilderTableColumnsScatter3d(rows));
        //String sizing = "nRaws:" + nRaws + " nPivotCol:" + nPivotCol + " nCpCcCol:" + nCpCcCat * nCpCcCol;
        return drawable;
    }
}
