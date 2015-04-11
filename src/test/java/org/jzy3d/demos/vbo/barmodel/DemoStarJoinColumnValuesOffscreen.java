package org.jzy3d.demos.vbo.barmodel;

import java.io.File;
import java.io.IOException;

import org.jzy3d.demos.BigPicture;
import org.jzy3d.demos.vbo.barmodel.generators.GeneratorStarJoin;
import org.jzy3d.plot3d.primitives.vbo.drawable.DrawableVBO;

public class DemoStarJoinColumnValuesOffscreen {
    public static int MILION = 1000000;
    public static File f = new File("./data/screenshots/" + DemoStarJoinColumnValuesOffscreen.class.getSimpleName() + ".png");

    public static void main(String[] args) throws IOException {
        int nRaws = MILION / 100;
        int nPivotTheme = 5;
        int nPivotCol = 35 * nPivotTheme;
        int nCpCcCat = 12;
        int nCpCcCol = 10;

        DrawableVBO drawable = GeneratorStarJoin.drawable(nRaws, nPivotCol, nCpCcCat, nCpCcCol);
        BigPicture.screenshot(drawable, BigPicture.Type.dd, 2000, 1700, f);
    }
}
