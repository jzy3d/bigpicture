package org.jzy3d.io.spark.csv.mapping;

public class ColumnMappingColor {
    public int rcolumn = 3;
    public int vcolumn = 4;
    public int bcolumn = 5;
    public int acolumn = 6;
    
    public ColumnMappingColor(int rcolumn, int vcolumn, int bcolumn, int acolumn) {
        super();
        this.rcolumn = rcolumn;
        this.vcolumn = vcolumn;
        this.bcolumn = bcolumn;
        this.acolumn = acolumn;
    }
    
    public ColumnMappingColor() {
    }
}
