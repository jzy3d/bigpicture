package org.jzy3d.io.hive.jdbc;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
/**
 * export HIVE_CONF_DIR=/Users/martin/Dev/hadoop/spark/spark-trials
 * 
 * @author martin
 *
 */
public class HiveJdbcClient {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    /**
     * https://cwiki.apache.org/confluence/display/Hive/HiveServer2+Clients
     * http://chase-seibert.github.io/blog/2013/05/17/hive-insert-and-dump-csv-with-map-datatype.html
     */
    public static void main(String[] args) throws SQLException {
        driver();

        String ip = "172.16.255.134";

        // replace "hive" here with the name of the user the queries should run
        // as
        Connection con = DriverManager.getConnection("jdbc:hive2://" + ip + ":10000/default", "train", "hadoop");
        Statement stmt = con.createStatement();

        String tableName = "testHiveDriverTable";
        //dropCreateTable(stmt, tableName);
        showTables(stmt, tableName);
        describe(stmt, tableName);

        // load data into table
        // NOTE: filepath has to be local to the hive server
        // NOTE: /tmp/a.txt is a ctrl-A separated file with two fields per line
        String csvInput = "/tmp/martin-10-06-au-12-26-comma.csv";

        String filepath = "/tmp/a.txt";
        // loadData(stmt, tableName, filepath);

        //insertInto(stmt, tableName, 0, "coucou");
        
        insert(stmt);
        //stmt.execute("select * from test");
        selectAll(stmt, "test");
        count(stmt, "test");
        //selectAll(stmt, tableName);
        //count(stmt, tableName);
    }

    private static void insert(Statement stmt) throws SQLException {
        stmt.execute("drop table if exists test");
        stmt.execute("create table if not exists test(a int, b int) row format delimited fields terminated by ' '");
        
        stmt.execute("drop table if exists dual");
        stmt.execute("create table dual as select 1 as one from test");
        
        stmt.execute("insert into table test select stack(1,4,5) AS (a,b) from dual");
    }

    private static void driver() {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(1);
        }
    }
    private static void dropCreateTable(Statement stmt, String tableName) throws SQLException {
        stmt.execute("drop table if exists " + tableName);
        stmt.execute("create table " + tableName + " (key int, value string)");
    }

    private static void insertInto(Statement stmt, String tableName, int key, String value) throws SQLException {
        String q = "insert into table " + tableName;// + " values (" + key + ", '" + value + "')";
        String q2 = "select * from (values(" + key + ", '" + value + "')) as t";
        //q = q + " (" + q2 + ")";
        q = q + " values (" + key + ", '" + value + "')";
        
        System.out.println(q);
        stmt.execute(q);// (key int, value string)");
    }

    
    private static void count(Statement stmt, String tableName) throws SQLException {
        String sql;
        ResultSet res;
        sql = "select count(1) from " + tableName;
        System.out.println("Running: " + sql);
        res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res.getString(1));
        }
    }

    private static void selectAll(Statement stmt, String tableName) throws SQLException {
        String sql;
        ResultSet res;
        sql = "select * from " + tableName;
        System.out.println("Running: " + sql);
        res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(String.valueOf(res.getInt(1)) + "\t" + res.getString(2));
        }
    }

    private static void loadData(Statement stmt, String tableName, String filepath) throws SQLException {
        String sql;
        sql = "load data local inpath '" + filepath + "' into table " + tableName;
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    private static void describe(Statement stmt, String tableName) throws SQLException {
        String sql;
        ResultSet res;
        sql = "describe " + tableName;
        System.out.println("Running: " + sql);
        res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res.getString(1) + "\t" + res.getString(2));
        }
    }

    private static void showTables(Statement stmt, String tableName) throws SQLException {
        String sql = "show tables '" + tableName + "'";
        System.out.println("Running: " + sql);
        ResultSet res = stmt.executeQuery(sql);
        if (res.next()) {
            System.out.println(res.getString(1));
        }
    }
}