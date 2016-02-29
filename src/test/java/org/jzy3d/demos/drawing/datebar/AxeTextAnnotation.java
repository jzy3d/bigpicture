package org.jzy3d.demos.drawing.datebar;

import java.awt.Font;

import org.jzy3d.colors.Color;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.axes.AxeAnnotation;
import org.jzy3d.plot3d.primitives.axes.AxeBox;
import org.jzy3d.plot3d.rendering.compat.GLES2CompatUtils;
import org.jzy3d.plot3d.rendering.textures.BufferedImageTexture;
import org.jzy3d.plot3d.rendering.view.Renderer3d;
import org.jzy3d.plot3d.text.align.Halign;
import org.jzy3d.plot3d.text.drawable.cells.TextCellRenderer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;

/**
 * A text annotation that support vertical text!
 * 
 * that should be attached to an axe, and that remain insensitive to scene
 * scaling.
 * 
 * TODO : -unstretch labels when window size change (update mapping)
 * -TextCellRenderer n'arrive pas à dimensionner correctement l'image par
 * rapport à la taille du texte (trop long ou trop court) -horizontal text
 */
public class AxeTextAnnotation implements AxeAnnotation {
    protected Font font;
    protected TextCellRenderer cellRenderer;
    protected BufferedImageTexture resource = null;
    protected Coord3d pos = null;
    protected Color filter = Color.WHITE.clone();
    protected boolean horizontal = false;// only vertical OK
    
    protected Renderer3d renderer3d;

    static boolean CELL_HAS_BORDER = false;
    /**
     * Text will always fit in text cell, so font size will mainly affect font
     * shape and anti aliasing
     */
    static int FONT_SIZE = 20;

    public AxeTextAnnotation(String string, Coord3d pos) {
        this.pos = pos;
        makeRenderer(string);
    }

    private void makeRenderer(String string) {
        font = new Font("Arial", Font.PLAIN, FONT_SIZE);
        cellRenderer = new TextCellRenderer(5, string, font);// date 5
        cellRenderer.setHorizontalAlignement(Halign.LEFT);
        cellRenderer.setTextColor(java.awt.Color.GRAY);
        cellRenderer.setBorderDisplayed(CELL_HAS_BORDER);
    }

    public void draw(GL gl, AxeBox axe) {
        if (resource == null) {
            resource = cellRenderer.getImage();
            resource.mount(gl);
        }
        Texture texture = resource.getTexture(gl);
        texture.bind(gl);

        // Draw
        drawTexture(gl, axe, texture, resource.getCoords());

        if (false)
            drawDebugPoint(gl);
    }

    private void drawTexture(GL gl, AxeBox axe, Texture texture, TextureCoords coords) {
        callWithAlphaFactor(gl, filter, 1);
        before(gl);
        if (horizontal) {
            mapTextureHorizontal(gl, coords, computeTextureVertex(axe, texture, horizontal, pos));
        } else {
            mapTextureVertical(gl, coords, computeTextureVertex(axe, texture, horizontal, pos));
        }
        after(gl);
    }

    private void drawDebugPoint(GL gl) {
        gl.getGL2().glPointSize(5);
        gl.getGL2().glBegin(GL.GL_POINTS);
        gl.getGL2().glColor4f(Color.RED.r, Color.RED.g, Color.RED.b, Color.RED.a);
        gl.getGL2().glVertex3f(pos.x, pos.y, pos.z);
        gl.getGL2().glEnd();
    }

    /**
     * Compute a mapping to ensure :
     * <ul>
     * <li>text cell is vertical
     * <li>text cell height is 1/6 of axis bounds
     * <li>text cell image is stretched according to texture ratio (w/h when
     * horizontal, h/w when vertical layout) and scene scaling ratio (y/x)
     * </ul>
     * 
     * TODO : should consider renderer ratio as text is stretch on window resize
     * 
     * @param axe
     * @param texture
     * @param horizontal
     * @param pos
     * @return
     */
    protected Mapping computeTextureVertex(AxeBox axe, Texture texture, boolean horizontal, Coord3d pos) {
        BoundingBox3d bounds = axe.getBoxBounds();
        float yrange = bounds.getYRange().getRange();
        float worldHeight = yrange / 6;
        float worldWidth = 1;
        float textureRatio = ((float) texture.getWidth() / (float) texture.getHeight());
        float rendererRatio = Float.NaN;
        
        if(renderer3d!=null){
            rendererRatio = (float)renderer3d.getHeight() / (float)renderer3d.getWidth();
        }
        float scaleRatio = axe.getScale().y / axe.getScale().x;
        if (horizontal) {
            worldWidth = worldHeight * textureRatio * scaleRatio;
        } else {
            worldWidth = worldHeight * (1 / textureRatio) * scaleRatio;
            if(!Float.isNaN(rendererRatio))
                worldWidth*=rendererRatio;
            //System.out.println("rendererRatio : " + rendererRatio);
        }

        Mapping mapping = new Mapping();
        mapping.leftBottom = new Coord3d(pos.x, pos.y - worldHeight, pos.z);
        mapping.rightBottom = new Coord3d(pos.x + worldWidth, pos.y - worldHeight, pos.z);
        mapping.rightTop = new Coord3d(pos.x + worldWidth, pos.y, pos.z);
        mapping.leftTop = new Coord3d(pos.x, pos.y, pos.z);
        return mapping;
    }

    class Mapping {
        public Coord3d leftBottom;
        public Coord3d leftTop;
        public Coord3d rightBottom;
        public Coord3d rightTop;
    }

    protected void mapTextureHorizontal(GL gl, TextureCoords coords, Mapping mapping) {
        if (gl.isGL2()) {
            gl.getGL2().glBegin(GL2GL3.GL_QUADS);
            gl.getGL2().glTexCoord2f(coords.left(), coords.bottom()); // left
            gl.getGL2().glVertex3d(mapping.leftBottom.x, mapping.leftBottom.y, mapping.leftBottom.z);
            gl.getGL2().glTexCoord2f(coords.right(), coords.bottom());
            gl.getGL2().glVertex3d(mapping.rightBottom.x, mapping.rightBottom.y, mapping.rightBottom.z);
            gl.getGL2().glTexCoord2f(coords.right(), coords.top());
            gl.getGL2().glVertex3d(mapping.rightTop.x, mapping.rightTop.y, mapping.rightTop.z);
            gl.getGL2().glTexCoord2f(coords.left(), coords.top());
            gl.getGL2().glVertex3d(mapping.leftTop.x, mapping.leftTop.y, mapping.leftTop.z);
            gl.getGL2().glEnd();
        }
    }

    protected void mapTextureVertical(GL gl, TextureCoords coords, Mapping mapping) {
        if (gl.isGL2()) {
            gl.getGL2().glBegin(GL2GL3.GL_QUADS);
            gl.getGL2().glTexCoord2f(coords.right(), coords.top());
            gl.getGL2().glVertex3d(mapping.leftTop.x, mapping.leftTop.y, mapping.leftTop.z);
            gl.getGL2().glTexCoord2f(coords.right(), coords.bottom());
            gl.getGL2().glVertex3d(mapping.rightTop.x, mapping.rightTop.y, mapping.rightTop.z);
            gl.getGL2().glTexCoord2f(coords.left(), coords.bottom());
            gl.getGL2().glVertex3d(mapping.rightBottom.x, mapping.rightBottom.y, mapping.rightBottom.z);
            gl.getGL2().glTexCoord2f(coords.left(), coords.top());
            gl.getGL2().glVertex3d(mapping.leftBottom.x, mapping.leftBottom.y, mapping.leftBottom.z);
            gl.getGL2().glEnd();
        }
    }

    protected void before(GL gl) {
        if (gl.isGL2()) {
            gl.getGL2().glPushMatrix();
            // gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT,GL2.GL_NICEST);
            gl.getGL2().glPolygonMode(GL.GL_FRONT_AND_BACK, GL2GL3.GL_FILL);
            // gl.glEnable(GL2.GL_POLYGON_OFFSET_FILL);
            // gl.glPolygonOffset(1.0f, 1.0f);
            gl.glEnable(GL.GL_TEXTURE_2D);
            gl.getGL2().glTexEnvf(GL.GL_TEXTURE_2D, GL2ES1.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
        } else {
            GLES2CompatUtils.glPushMatrix();
            GLES2CompatUtils.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2GL3.GL_FILL);
            gl.glEnable(GL.GL_TEXTURE_2D);
            GLES2CompatUtils.glTexEnvf(GL.GL_TEXTURE_2D, GL2ES1.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
        }
    }

    protected void after(GL gl) {
        gl.glDisable(GL.GL_TEXTURE_2D);
        gl.getGL2().glTexEnvi(GL2ES1.GL_TEXTURE_ENV, GL2ES1.GL_TEXTURE_ENV_MODE, GL2ES1.GL_MODULATE);
        gl.getGL2().glPopMatrix();
    }

    protected void callWithAlphaFactor(GL gl, Color c, float alpha) {
        if (gl.isGL2()) {
            gl.getGL2().glColor4f(c.r, c.g, c.b, c.a * alpha);
        } else {
            GLES2CompatUtils.glColor4f(c.r, c.g, c.b, c.a * alpha);
        }
    }

    public Renderer3d getRenderer3d() {
        return renderer3d;
    }

    public void setRenderer3d(Renderer3d renderer3d) {
        this.renderer3d = renderer3d;
    }
    
    
}
