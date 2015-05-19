package org.jzy3d.demos.drawing.vbo.barmodel.builder;

import java.util.List;

import javax.media.opengl.GL;

import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.vbo.buffers.FloatVBO;
import org.jzy3d.plot3d.primitives.vbo.builders.VBOBuilder;
import org.jzy3d.plot3d.primitives.vbo.drawable.DrawableVBO;
import org.jzy3d.plot3d.rendering.canvas.Quality;

/**
 * @see Reminder of GL geometry types : http://www.glprogramming.com/red/images/Image34.gif
 * 
 * @hack need to set size values.size()/2 to have no loop. Not having same result when changing computed buffer size.
 * 
 * @author martin
 */
public class VBOBuilderLineStrip extends VBOBuilder{
    List<Float> values;
    boolean colorPerVertex = true;
    
    public VBOBuilderLineStrip(List<Float> values) {
        super();
        this.values = values;
    }

    public void load(GL gl, DrawableVBO drawable) throws Exception {
        preConfigureDrawable(drawable);
        FloatVBO vbo = initFloatVBO(drawable, colorPerVertex, values.size()/2); // HACK /2 to have no LINE STRIP LOOP???
        fill(values, vbo);
        drawable.setData(gl, vbo);
    }
    
    private void preConfigureDrawable(DrawableVBO drawable) {
        drawable.setGeometry(GL.GL_LINE_STRIP);
        drawable.setHasColorBuffer(colorPerVertex);
        drawable.setWidth(5f);
        drawable.setQuality(Quality.Nicest);
    }

    private void fill(List<Float> values2, FloatVBO vbo) {
        int k = 0;
        for(Float f: values2){
            Coord3d c = new Coord3d(k, f, Math.random());
            Color col = Color.RED;
            
            putPoint(vbo, k, col, c);
            //System.out.println(c);
            
            k++;
        }
        vbo.getVertices().rewind();
        vbo.getIndices().rewind();
    }
}
