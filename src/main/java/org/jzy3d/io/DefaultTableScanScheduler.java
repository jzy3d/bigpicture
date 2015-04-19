package org.jzy3d.io;

import java.util.List;

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
}
