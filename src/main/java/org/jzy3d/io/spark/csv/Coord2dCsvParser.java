package org.jzy3d.io.spark.csv;

import org.apache.spark.api.java.function.Function;
import org.jzy3d.maths.Coord2d;

public class Coord2dCsvParser extends CsvParser implements Function<String, Coord2d> {
    private static final long serialVersionUID = 114535243324747895L;
    
    public Coord2d call(String line) throws Exception {
        String[] parts = line.split(separator);
        Coord2d entry = parseCoord2d(parts);
        return entry;
    }
}
