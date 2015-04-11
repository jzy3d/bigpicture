package org.jzy3d.demos.vbo.bar;

import org.jzy3d.demos.BigPicture;
import org.jzy3d.maths.TicToc;
import org.jzy3d.plot3d.primitives.vbo.drawable.BarVBO;

/**
 * 
 * http://www.songho.ca/opengl/gl_vbo.html
 * 
 * 
 * http://www.felixgers.de/teaching/jogl/vertexBufferObject.html
 * - verify VBO available
 */
public class DemoBarVBO {
    public static int MILION = 1000000;

    public static void main(String[] args) {
        TicToc.T.tic();
        
        float ratio = .5f;
        int size = (int) (ratio * MILION);
        BarVBO bars = new BarVBO(new VBOBuilderRandomBar(size));
        BigPicture.chart(bars, BigPicture.Type.dd);
        
    }
}
