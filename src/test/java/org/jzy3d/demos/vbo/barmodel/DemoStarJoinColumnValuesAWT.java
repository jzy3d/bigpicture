package org.jzy3d.demos.vbo.barmodel;

import org.jzy3d.demos.BigPicture;
import org.jzy3d.demos.vbo.barmodel.generators.GeneratorStarJoin;
import org.jzy3d.plot3d.primitives.vbo.drawable.DrawableVBO;

public class DemoStarJoinColumnValuesAWT{
    public static int MILION = 1000000;

    public static void main(String[] args) {
        int nRaws = MILION / 100;
        int nPivotTheme = 8;
        int nPivotCol = 35 * nPivotTheme;
        int nCpCcCat = 12;
        int nCpCcCol = 10;

        DrawableVBO drawable = GeneratorStarJoin.drawable(nRaws, nPivotCol, nCpCcCat, nCpCcCol);

        BigPicture.chartBlack(drawable, BigPicture.Type.ddd);
    }
}
