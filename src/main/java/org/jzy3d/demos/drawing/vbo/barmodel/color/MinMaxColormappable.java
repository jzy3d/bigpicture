package org.jzy3d.demos.drawing.vbo.barmodel.color;

import org.jzy3d.colors.IColorMappable;

public class MinMaxColormappable implements IColorMappable{
    protected double min;
    protected double max;
    
    public MinMaxColormappable(double min, double max) {
        this.min = min;
        this.max = max;
    }
    public double getMin() {
        return min;
    }
    public double getMax() {
        return max;
    }
    public void setMin(double value) {
        min = value;
    }
    public void setMax(double value) {
        max = value;
    }

}
