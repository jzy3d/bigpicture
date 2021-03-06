package org.jzy3d.demos.drawing.vbo.barmodel.color;

import org.jzy3d.colors.Color;
import org.jzy3d.io.KeyVal;

public interface KeyValColorMapper<K,V> {
    public Color getColor(KeyVal<K,V> keyVal);
}
