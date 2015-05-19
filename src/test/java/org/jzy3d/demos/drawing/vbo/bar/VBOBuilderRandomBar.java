package org.jzy3d.demos.drawing.vbo.bar;

import javax.media.opengl.GL;

import org.apache.log4j.Logger;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.plot3d.primitives.vbo.buffers.FloatVBO;
import org.jzy3d.plot3d.primitives.vbo.builders.VBOBuilder;
import org.jzy3d.plot3d.primitives.vbo.drawable.DrawableVBO;

/**
 * A simple loader loading an existing collection of coordinates into a Vertex
 * Buffer Objects once GL initialization stage requires it to be loaded.
 * 
 * If a colormapper is given, color buffer will be filled according to coloring policy.
 * 
 * @author martin
 */
public class VBOBuilderRandomBar extends VBOBuilder {
    protected ColorMapper coloring = null;
    int n = 100;

    public VBOBuilderRandomBar() {
    }
    public VBOBuilderRandomBar(int n) {
        this.n = n;
    }
    
    public VBOBuilderRandomBar(ColorMapper coloring) {
        this.coloring = coloring;
    }

    // @Override
    // @SuppressWarnings("unchecked")
    public void load(GL gl, DrawableVBO drawable) throws Exception {
        FloatVBO vbo = initFloatVBO(drawable, false, n);
        fillWithRandomBar(n, drawable, vbo, null);
        drawable.setData(gl, vbo);
        Logger.getLogger(VBOBuilderRandomBar.class).info("done loading " + n + " coords");
    }
}
