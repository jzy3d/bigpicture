package org.jzy3d.io.spark.csv;

import org.jzy3d.io.spark.csv.mapping.ColumnMappingCoord;
import org.jzy3d.maths.Coord2d;
import org.jzy3d.maths.Coord3d;

public class CsvParser {
    protected String separator = ",";
    protected ColumnMappingCoord mapping;

    public CsvParser() {
        this(",", new ColumnMappingCoord());
    }

    public CsvParser(ColumnMappingCoord mapping) {
        this(",", mapping);
    }
    
    public CsvParser(String separator, ColumnMappingCoord mapping) {
        this.separator = separator;
        this.mapping = mapping;
    }
    
    public Coord3d parseCoord3d(String[] line) {
        Coord3d coord = new Coord3d();
        parseXFloat(line, mapping.xcolumn, coord);
        parseYFloat(line, mapping.ycolumn, coord);
        parseZFloat(line, mapping.zcolumn, coord);
        return coord;
    }
    
    public Coord2d parseCoord2d(String[] parts) {
        Coord2d coord = new Coord2d();
        parseXFloat(parts, mapping.xcolumn, coord);
        parseYFloat(parts, mapping.ycolumn, coord);
        return coord;
    }

    public void parseXFloat(String[] parts, int id, Coord3d coord) {
        if (parts.length >= id+1) {
            try {
                coord.x = Float.parseFloat(parts[id].trim());
            } catch (Exception e) {
            }
        }
    }

    public void parseYFloat(String[] parts, int id, Coord3d coord) {
        if (parts.length >= id+1) {
            try {
                coord.y = Float.parseFloat(parts[id].trim());
            } catch (Exception e) {
            }
        }
    }

    public void parseZFloat(String[] parts, int id, Coord3d coord) {
        if (parts.length >= id+1) {
            try {
                coord.z = Float.parseFloat(parts[id].trim());
            } catch (Exception e) {
            }
        }
    }
    
    private void parseXFloat(String[] parts, int id, Coord2d coord) {
        if (parts.length >= id+1) {
            try {
                coord.x = Float.parseFloat(parts[id].trim());
            } catch (Exception e) {
            }
        }
    }

    private void parseYFloat(String[] parts, int id, Coord2d entry) {
        if (parts.length >= +1) {
            try {
                entry.y = Float.parseFloat(parts[id].trim());
            } catch (Exception e) {
            }
        }
    }
}
