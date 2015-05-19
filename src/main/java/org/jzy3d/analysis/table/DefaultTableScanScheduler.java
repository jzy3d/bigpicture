package org.jzy3d.analysis.table;

import java.util.List;

import org.jzy3d.io.KeyVal;

import com.google.common.collect.ArrayListMultimap;

public class DefaultTableScanScheduler {
    List<List<KeyVal<String,Float>>> table;

    public DefaultTableScanScheduler(List<List<KeyVal<String,Float>>> table) {
        this.table = table;
    }

    public List<List<KeyVal<String,Float>>> getTable() {
        return table;
    }

    public void setTable(List<List<KeyVal<String,Float>>> table) {
        this.table = table;
    }
    
    public ArrayListMultimap<String, Float> computeColumns(){
        ArrayListMultimap<String, Float> columns = ArrayListMultimap.create();

        for(List<KeyVal<String,Float>> row: table){
            for(KeyVal<String,Float> column: row){
                columns.put(column.key, column.val);
            }
        }
        return columns;
    }
    
}
