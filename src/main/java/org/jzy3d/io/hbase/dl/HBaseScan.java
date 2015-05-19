package org.jzy3d.io.hbase.dl;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

/**
 *         String ip = "172.16.255.134";

 * @author martin
 *
 */
public class HBaseScan {
    public static void main(String[] args) throws IOException {
        String tableName = "myLittleHBaseTable";
        
        
        // You need a configuration object to tell the client where to connect.
        // When you create a HBaseConfiguration, it reads in whatever you've set
        // into your hbase-site.xml and in hbase-default.xml, as long as these
        // can be found on the CLASSPATH
        Configuration config = HBaseConfiguration.create();

        // This instantiates an HTable object that connects you to the
        // "myLittleHBaseTable" table.
        HTable table = new HTable(config, tableName);


        getRow(table);

        
        String family = "myLittleFamily";
        String qualifier = "someQualifier";
        
        scanColumn(table, family, qualifier);
    }

    private static void scanColumn(HTable table, String family, String qualifier) throws IOException {
        // Sometimes, you won't know the row you're looking for. In this case,
        // you use a Scanner. This will give you cursor-like interface to the
        // contents of the table. To set up a Scanner, do like you did above
        // making a Put and a Get, create a Scan. Adorn it with column names,
        // etc.
        Scan s = new Scan();
        s.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
        ResultScanner scanner = table.getScanner(s);
        try {
            // Scanners return Result instances.
            // Now, for the actual iteration. One way is to use a while loop
            // like so:
            for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
                // print out the row we found and the columns we were looking
                // for
                System.out.println("Found row: " + rr);
            }

            // The other approach is to use a foreach loop. Scanners are
            // iterable!
            // for (Result rr : scanner) {
            // System.out.println("Found row: " + rr);
            // }
        } finally {
            // Make sure you close your scanners when you are done!
            // Thats why we have it inside a try/finally clause
            scanner.close();
        }
    }

    private static void getRow(HTable table) throws IOException {
        // Now, to retrieve the data we just wrote. The values that come back
        // are Result instances. Generally, a Result is an object that will
        // package up the hbase return into the form you find most palatable.
        Get g = new Get(Bytes.toBytes("myLittleRow"));
        Result r = table.get(g);
        byte[] value = r.getValue(Bytes.toBytes("myLittleFamily"), Bytes.toBytes("someQualifier"));
        // If we convert the value bytes, we should get back 'Some Value', the
        // value we inserted at this location.
        String valueStr = Bytes.toString(value);
        System.out.println("GET: " + valueStr);
    }
}