package org.jzy3d.demos.vbo.barmodel;

import org.jzy3d.demos.BigPicture;
import org.jzy3d.demos.vbo.barmodel.generators.GeneratorStarJoin;
import org.jzy3d.plot3d.primitives.vbo.drawable.DrawableVBO;

public class DemoStarJoinColumnValuesAWT{
    public static int MILION = 1000000;

    public static void main(String[] args) {
        DrawableVBO drawable = GeneratorStarJoin.makeDrawable(MILION/100);
        BigPicture.chart(drawable, BigPicture.Type.ddd);
    }
}
