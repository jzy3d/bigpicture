package org.jzy3d.chart.spark;

/**
 * Gather options that should be passed to a simple sparkchart command
 * 
 * Not implemented yet, spec only
 */
public class SparkChartOptions {
    /**
     * <ul>
     * <li><code>.</code> 2d scatter with dots
     * <li><code>o</code> 2d scatter with circle. Specify pixel width with
     * <code>o4</code>
     * <li><code>_</code> 2d line. Specify pixel width with
     * <code>_4</code>
     * <li><code>-</code> 2d dashed line
     * <li><code>surf</code> 3d surface, with optional <code>surf-delaunay</code>
     * able to deal with random mesh (no assumption such as regular X/Y values
     * building an orthogonal grid)
     * <li><code>bar</code> bar
     * 
     * <li><code>col.id.x</code> order of column providing X coordinate
     * <li><code>col.id.y</code> order of column providing Y coordinate
     * <li><code>col.id.z</code> order of column providing Z coordinate
     */
    public char type = '.';
    public int width = 1;

    // position
    public int xColumnId = 0;
    public int yColumnId = 1;
    public int zColumnId = 2;

    // color
    public int rColumnId = -1;
    public int vColumnId = -1;
    public int bColumnId = -1;
    public int aColumnId = -1;

    // dimensions
    public int dim = 2; // 2, 3
    
    

}
