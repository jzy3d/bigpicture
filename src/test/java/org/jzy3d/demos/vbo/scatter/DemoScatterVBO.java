package org.jzy3d.demos.vbo.scatter;

import java.util.List;

import org.jzy3d.colors.ColorMapper;
import org.jzy3d.demos.BigPicture;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.CompileableComposite;
import org.jzy3d.plot3d.primitives.vbo.builders.VBOBuilderListCoord3d;
import org.jzy3d.plot3d.primitives.vbo.drawable.DrawableVBO;
import org.jzy3d.plot3d.primitives.vbo.drawable.ScatterVBO;

/**
 * Shows a scatter built with Vertex Buffer Object, an efficient way of storing
 * geometry in GPU at program startup.
 * 
 * In contrast, drawable object that are neither of type {@link DrawableVBO} or
 * {@link CompileableComposite} will require that processor to send drawing
 * instructions to the GPU at <i>each</i> rendering call, thus implying useless work
 * for geometry that should not change in shape or color over time.
 * 
 * One can easily derive from {@link VBOBuilderListCoord3d} to support its
 * own datamodel rather than jzy3d {@link Coord3d} elements.
 * 
 * @author Martin Pernollet
 *
 */
public class DemoScatterVBO {
    public static int MILION = 1000000;

    public static void main(String[] args) {
        float ratio = .5f;
        int size = (int) (ratio * MILION);

        List<Coord3d> coords = ScatterGenerator.getScatter(size);
        ColorMapper coloring = ScatterGenerator.coloring(coords);
        ScatterVBO drawable = new ScatterVBO(new VBOBuilderListCoord3d(coords, coloring));
        BigPicture.chart(drawable, BigPicture.Type.ddd);
    }
}
