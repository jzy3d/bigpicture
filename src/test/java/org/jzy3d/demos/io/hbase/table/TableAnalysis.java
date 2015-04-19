package org.jzy3d.demos.io.hbase.table;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jzy3d.chart.Chart;
import org.jzy3d.demos.BigPicture;
import org.jzy3d.demos.BigPicture.Type;
import org.jzy3d.demos.vbo.barmodel.builder.VBOBuilderTableColumnsScatter3d;
import org.jzy3d.io.DefaultTableScanScheduler;
import org.jzy3d.io.KeyVal;
import org.jzy3d.plot3d.primitives.vbo.drawable.DrawableVBO;

/**
 * Given a Table scan scheduler, will build all charts for an analysis and dump pictures to disk.
 * 
 * @author martin
 */
public class TableAnalysis {
    Chart chartOverview;
    Map<String,Chart> chartColLines;
    Map<String,Chart> chartColDensities;
    
    protected DefaultTableScanScheduler scheduler;

    public TableAnalysis(DefaultTableScanScheduler scheduler) {
        this.scheduler = scheduler;
    }
    
    /**
     * Delete input directory and generate all charts as file.
     * @throws IOException 
     */
    public void report(File output) throws IOException{
        if(output.exists()){
           output.delete(); 
        }
        output.mkdirs();
        
        List<List<KeyVal<String,Float>>> table = scheduler.getTable();
        
        // Overview chart
        DrawableVBO drawable = new DrawableVBO(new VBOBuilderTableColumnsScatter3d(table));
        BigPicture.offscreen(drawable, Type.dd).black().screenshot(new File(output, "0-overview.png"));

    }

    public DefaultTableScanScheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(DefaultTableScanScheduler scheduler) {
        this.scheduler = scheduler;
    }
}
