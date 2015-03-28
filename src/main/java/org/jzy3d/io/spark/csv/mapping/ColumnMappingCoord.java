package org.jzy3d.io.spark.csv.mapping;

public class ColumnMappingCoord {
    public int xcolumn = 0;
    public int ycolumn = 1;
    public int zcolumn = 2;
    
    public ColumnMappingCoord(int xcolumn, int ycolumn, int zcolumn) {
        this.xcolumn = xcolumn;
        this.ycolumn = ycolumn;
        this.zcolumn = zcolumn;
    }

    public ColumnMappingCoord() {
    }   
}
