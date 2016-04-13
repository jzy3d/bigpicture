package org.jzy3d.demos.drawing.vbo.bar;

import org.jzy3d.chart.BigPicture;
import org.jzy3d.chart.Type;
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
    public static int MILLION = 1000000;

    public static void main(String[] args) {
        float ratio = .05f;
        int size = (int) (ratio * MILLION);
        BarVBO bars = new BarVBO(new VBOBuilderRandomBar(size));
        BigPicture.chart(bars, Type.dd);
    }
}
