package org.jzy3d.demos.vbo.barmodel;

import org.jzy3d.demos.BigPicture;
import org.jzy3d.demos.vbo.barmodel.generators.GeneratorStarJoin;
import org.jzy3d.plot3d.primitives.vbo.drawable.DrawableVBO;

/**
 * Draws key-values of each row, where each column name is mapped to Y and a color, and each column value is mapped to Z.
 * @author martin
 *
 */
public class DemoStarJoinColumnValuesAWT{
    public static int MILION = 1000000;

    public static void main(String[] args) {
        int nRaws = MILION / 10;
        int nPivotTheme = 1;
        int nPivotCol = 15 * nPivotTheme;
        int nCpCcCat = 0;
        int nCpCcCol = 10;

        DrawableVBO drawable = GeneratorStarJoin.drawable(nRaws, nPivotCol, nCpCcCat, nCpCcCol);

        BigPicture.chartBlack(drawable, BigPicture.Type.ddd);
    }
}
