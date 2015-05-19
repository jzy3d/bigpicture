package org.jzy3d.demos.drawing.vbo.barmodel.color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.jzy3d.colors.Color;
import org.jzy3d.colors.colormaps.IColorMap;
import org.jzy3d.io.KeyVal;

public class KeyRankColorMapper<K, V> implements KeyValColorMapper<K, V> {
    Map<K, Color> colors;

    public KeyRankColorMapper(List<List<KeyVal<K, V>>> keyVals, IColorMap colormap) {
        
        Set<K> keys = new TreeSet<K>();
        
        for(List<KeyVal<K, V>> row : keyVals){
            for(KeyVal<K, V> column : row){
                keys.add(column.key);
            }
        }
        
        initColormap(keys, colormap);
    }

    protected void initColormap(Set<K> keys, IColorMap colormap) {
        colors = new HashMap<K,Color>();
        MinMaxColormappable minmax = new MinMaxColormappable(0, keys.size());
        int i=0;
        for(K k: keys){
            Color c = colormap.getColor(minmax, i++);
            c.a = 0.75f;
            colors.put(k, c);//Color.RED);
        }
    }

    public Color getColor(KeyVal<K, V> keyVal) {
        return colors.get(keyVal.key);
    }
}
