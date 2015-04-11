package org.jzy3d.demos.vbo.barmodel.builder;

import java.util.List;

import javax.media.opengl.GL;

import org.jzy3d.colors.Color;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.demos.vbo.barmodel.color.KeyRankColorMapper;
import org.jzy3d.demos.vbo.barmodel.model.KeyVal;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.vbo.buffers.FloatVBO;
import org.jzy3d.plot3d.primitives.vbo.builders.VBOBuilder;
import org.jzy3d.plot3d.primitives.vbo.drawable.DrawableVBO;

public class VBOBuilderBarModel extends VBOBuilder {
    List<List<KeyVal<String, Float>>> rows;

    public VBOBuilderBarModel(List<List<KeyVal<String, Float>>> rows) {
        super();
        this.rows = rows;
    }

    public void load(GL gl, DrawableVBO drawable) throws Exception {
        preConfigureDrawable(drawable);

        FloatVBO vbo = initFloatVBO(drawable, true, countKv(rows));
        fill(rows, vbo);
        drawable.setData(gl, vbo);
    }

    private void preConfigureDrawable(DrawableVBO drawable) {
        drawable.setGeometry(GL.GL_POINTS);
        drawable.setHasColorBuffer(true);
        drawable.setWidth(2);
       // drawable.setQuality(Quality.Fastest);
    }

    protected int countKv(List<List<KeyVal<String, Float>>> rows) {
        int n = 0;
        for (int i = 0; i < rows.size(); i++) {
            List<KeyVal<String, Float>> row = rows.get(i);
            for (int j = 0; j < row.size(); j++) {
                n++;
            }
        }
        return n;
    }

    protected void fill(List<List<KeyVal<String, Float>>> rows, FloatVBO vbo) {
        int size = 0;
        KeyRankColorMapper<String, Float> coloring = new KeyRankColorMapper<String, Float>(rows, new ColorMapRainbow());
        for (int i = 0; i < rows.size(); i++) {
            List<KeyVal<String, Float>> row = rows.get(i);

            for (int j = 0; j < row.size(); j++) {
                KeyVal<String, Float> k = row.get(j);
                Color color = coloring.getColor(k);
                Coord3d c1 = new Coord3d(i, j, k.val);
                putPoint(vbo, size++, color, c1);
            }
        }
        vbo.getVertices().rewind();
        vbo.getIndices().rewind();
    }
}
