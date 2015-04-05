package org.jzy3d.demos.spark;

import static java.lang.Math.E;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;

public class Generator {
    public static List<Coord3d> getScatter(int size) {
        List<Coord3d> coords = new ArrayList<Coord3d>(size);
        Random r = new Random();
        r.setSeed(0);
        double x;
        double y;
        double z;
        for (int i = 0; i < size; i++) {
            x = 2.0 * r.nextDouble();
            y = 2.0 * r.nextDouble();
            double s = r.nextDouble();

            if (s > 0.75) {
                x *= -1;
                y *= -1;
            }
            if (s > 0.5 && s <= 0.75) {
                x *= -1;
            }
            if (s > 0.25 && s <= 0.5) {
                y *= -1;
            }

            // exp( -(x**2 + y**2) ) * cos(x/4)*sin(y) * cos(2*(x**2+y**2))
            double f1 = cos(2 * (x * x + y * y));
            double f2 = cos(x / 4) * sin(y);
            double f3 = x * x + y * y;
            z = pow(E, -f3) * f2 * f1;

            coords.add(new Coord3d(x, y, z));
        }
        return coords;
    }
    
    public static ColorMapper coloring(List<Coord3d> coords) {
        Range zrange = Coord3d.getZRange(coords);
        ColorMapper coloring = new ColorMapper(new ColorMapRainbow(), zrange.getMin(), zrange.getMax(), new Color(1, 1, 1, .5f));
        return coloring;
    }

}
