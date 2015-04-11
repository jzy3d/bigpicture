package org.jzy3d.demos.vbo.barmodel.model.color;

import org.jzy3d.colors.Color;
import org.jzy3d.demos.vbo.barmodel.model.KeyVal;

public interface KeyValColorMapper<K,V> {
    public Color getColor(KeyVal<K,V> keyVal);
}
