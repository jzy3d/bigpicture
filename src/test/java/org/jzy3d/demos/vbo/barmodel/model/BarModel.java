/**
 * 
 */
package org.jzy3d.demos.vbo.barmodel.model;

import org.jzy3d.demos.vbo.barmodel.color.KeyValColorMapper;
import org.jzy3d.plot3d.primitives.vbo.buffers.FloatVBO;

/**
 * @author martin
 *
 */
public class BarModel<K,V> {
    KeyVal<K, V>[] keyVals;
    KeyValColorMapper<K, V> colormap;
    
    
    public KeyVal<K, V>[] getKeyVals() {
        return keyVals;
    }
    public void setKeyVals(KeyVal<K, V>[] keyVals) {
        this.keyVals = keyVals;
    }
    public KeyValColorMapper<K, V> getColormap() {
        return colormap;
    }
    public void setColormap(KeyValColorMapper<K, V> colormap) {
        this.colormap = colormap;
    }
}
