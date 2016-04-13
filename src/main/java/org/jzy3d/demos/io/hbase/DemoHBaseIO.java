package org.jzy3d.demos.io.hbase;

import org.jzy3d.io.hbase.HBaseIO;
import org.jzy3d.utils.LoggerUtils;

/** Connects to a standalone HBase DB. 
 * 
 * 
 * @see https://hbase.apache.org/book.html#quickstart to install a standalone HBase database on your computer.
 */
public class DemoHBaseIO {
    
    public static void main(String[] agrs) {
        LoggerUtils.minimal();
        
        try {
            String tablename = "scores";
            String[] familys = { "grade", "course" };
    
            HBaseIO client = new HBaseIO();
    
            client.tableCreate(tablename, familys);
    
            // add record zkb
            client.put(tablename, "zkb", "grade", "", "5");
            client.put(tablename, "zkb", "course", "", "90");
            client.put(tablename, "zkb", "course", "math", "97");
            client.put(tablename, "zkb", "course", "art", "87");
            // add record baoniu
            client.put(tablename, "baoniu", "grade", "", "4");
            client.put(tablename, "baoniu", "course", "math", "89");
    
            System.out.println("===========get one record========");
            client.get(tablename, "zkb");
    
            System.out.println("===========show all record========");
            client.scanPrint(tablename);
    
            System.out.println("===========del one record========");
            client.delete(tablename, "baoniu");
            client.scanPrint(tablename);
    
            System.out.println("===========show all record========");
            client.scanPrint(tablename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

