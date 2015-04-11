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
        DrawableVBO drawable = GeneratorStarJoin.makeDrawable(MILION / 100);
        BigPicture.screenshot(drawable, BigPicture.Type.dd, 2000, 1700, f);
    }
}
