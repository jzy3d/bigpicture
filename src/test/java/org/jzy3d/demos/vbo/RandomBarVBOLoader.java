package org.jzy3d.demos.vbo;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Collection;

import javax.media.opengl.GL;

import org.apache.log4j.Logger;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.io.IGLLoader;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.vbo.DrawableVBO;

/**
 * A simple loader loading an existing collection of coordinates into a Vertex
 * Buffer Objects once GL initialization stage requires it to be loaded.
 * 
 * If a colormapper is given, color buffer will be filled according to coloring policy.
 * 
 * @author martin
 */
public class RandomBarVBOLoader implements IGLLoader<DrawableVBO> {
    protected ColorMapper coloring = null;
    int n = 100;

    public RandomBarVBOLoader() {
    }
    public RandomBarVBOLoader(int n) {
        this.n = n;
    }
    
    public RandomBarVBOLoader(ColorMapper coloring) {
        this.coloring = coloring;
    }

    // @Override
    // @SuppressWarnings("unchecked")
    public void load(GL gl, DrawableVBO drawable) throws Exception {
        // configure
        boolean hasNormal = false;
        int dimension = 3; // x, y, z
        int geometrySize = computeGeometrySize(drawable);
        int verticeBufferSize = computeVerticeBufferSize(n, dimension, geometrySize, hasNormal, coloring!=null);
        int indexBufferSize = n * geometrySize;

        // build and load buffers
        FloatBuffer vertices = FloatBuffer.allocate(verticeBufferSize);
        IntBuffer indices = IntBuffer.allocate(indexBufferSize);
        BoundingBox3d bounds = new BoundingBox3d();
        //fillBuffersWithRandom(drawable, vertices, indices, bounds);

        fillBuffersWithRandom(n, drawable, vertices, indices, bounds, null);

        
        // Store in GPU
        drawable.setData(gl, indices, vertices, bounds);
        Logger.getLogger(RandomBarVBOLoader.class).info("done loading " + n + " coords");
    }

    protected void fillBuffersWithRandom(int n, DrawableVBO drawable, FloatBuffer vertices, IntBuffer indices, BoundingBox3d bounds, ColorMapper colors) {
        drawable.setHasColorBuffer(colors!=null);
        
        int size = 0;
        for (int i=0; i<n; i++) {
            indices.put(size++);
            
            float v = (float)Math.random()*100;
            float d = (float)Math.random()*1;
            Coord3d c1 = new Coord3d(i, d, 0);
            Coord3d c2 = new Coord3d(i, d, v);
            putCoord(vertices, c1);
            putCoord(vertices, c2);
            bounds.add(c1);
            bounds.add(c2);
            
            /*if(colors!=null){
                putColor(vertices, colors.getColor(c1));
                putColor(vertices, colors.getColor(c2));
            }*/
        }
        vertices.rewind();
        indices.rewind();
    }
/*
    protected void fillBuffersWithCollection(DrawableVBO drawable, Collection<Coord3d> coordinates, FloatBuffer vertices, IntBuffer indices, BoundingBox3d bounds) {
        fillBuffersWithCollection(drawable, coordinates, null, vertices, indices, bounds);
    }

    protected void fillBuffersWithCollection(DrawableVBO drawable, Collection<Coord3d> coordinates, ColorMapper colors, FloatBuffer vertices, IntBuffer indices, BoundingBox3d bounds) {
        drawable.setHasColorBuffer(colors!=null);
        
        int size = 0;
        for (Coord3d c : coordinates) {
            indices.put(size++);
            putCoord(vertices, c);
            bounds.add(c);
            
            if(colors!=null){
                putColor(vertices, colors.getColor(c));
            }
        }
        vertices.rewind();
        indices.rewind();
    }*/

    
    // TODO to abstract vbo loader
    protected void putCoord(FloatBuffer vertices, Coord3d c) {
        vertices.put(c.x);
        vertices.put(c.y);
        vertices.put(c.z);
    }

    protected void putColor(FloatBuffer vertices, Color color) {
        vertices.put(color.r);
        vertices.put(color.g);
        vertices.put(color.b);
    }

    
    protected int computeVerticeBufferSize(int n, int dimension, int geometrysize, boolean hasNormal, boolean hasColor) {
        int verticeBufferSize = 0;
        
        if(hasColor){
            verticeBufferSize = n * (dimension * 2) * geometrysize;// *2 normals
        }
        
        if (hasNormal) {
            verticeBufferSize = n * (dimension * 2) * geometrysize;// *2 normals
        } else {
            verticeBufferSize = n * dimension * geometrysize;

        }

        if(hasColor){
            verticeBufferSize = n * (dimension * 2) * geometrysize;// *2 color
        }

        verticeBufferSize = n * (dimension * 2) * geometrysize;// *2 lines

        
        return verticeBufferSize;
    }

    protected int computeGeometrySize(DrawableVBO drawable) {
        int geomsize = 0; // triangle

        if (drawable.getGeometry() == GL.GL_POINTS) {
            geomsize = 1;
        }
        if (drawable.getGeometry() == GL.GL_LINES) {
            geomsize = 2;
        }
        else if (drawable.getGeometry() == GL.GL_TRIANGLES) {
            geomsize = 3;
        }
        return 2;
    }

}
