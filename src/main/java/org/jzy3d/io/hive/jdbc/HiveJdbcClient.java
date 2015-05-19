package org.jzy3d.io.hive.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * export HIVE_CONF_DIR=/Users/martin/Dev/hadoop/spark/spark-trials
 * 
 * @author martin
 *
 */
public class HiveJdbcClient {
    private static String DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";

    /**
     * https://cwiki.apache.org/confluence/display/Hive/HiveServer2+Clients
     * http:
     * //chase-seibert.github.io/blog/2013/05/17/hive-insert-and-dump-csv-with
     * -map-datatype.html
     */
    public static void main(String[] args) throws SQLException {
        // String ip = "172.16.255.128"; // old
        // String user = "root";
        // String password = "hadoop";

        /*
         * String ip = "172.16.255.131"; String port = "10000"; String domain =
         * "default"; String user = "train"; String password = "hadoop";
         */

        String ip = "172.16.255.136";
        String port = "10000";
        String domain = "default";
        String user = "root";
        String password = "hadoop";
        // actions
        boolean load = false;
        boolean print = false;
        boolean insert = true;

        HiveJdbcClient hive = new HiveJdbcClient();
        Statement stmt = hive.connect(ip, port, domain, user, password).createStatement();

        // List tables
        // hive.showTables(stmt);

        // Create a table
        String tableName = "pouet";

        hive.createTable(stmt, tableName, " (key int, value string)");

        // load data into table
        if (load) {
            String filepath = "/tmp/a.txt";
            hive.loadData(stmt, tableName, filepath);
        }

        // Print a table
        if (print) {
            hive.describe(stmt, tableName);
            hive.count(stmt, tableName);
            hive.selectAll(stmt, tableName);
        }

        // Load data in table
        // load(hive, stmt, tableName);
        if (insert) {
            hive.insertInto(stmt, tableName, 0, "coucou1");
            hive.insertInto(stmt, tableName, 1, "coucou2");
            hive.insertInto(stmt, tableName, 2, "coucou3");
            hive.count(stmt, tableName);
            hive.selectAll(stmt, tableName);
        }
    }

    /* */

    public HiveJdbcClient() {
        loadDriver();
    }

    private static void loadDriver() {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection connect(String ip, String port, String domain, String user, String password) throws SQLException {
        String url = "jdbc:hive2://" + ip + ":" + port + "/default";
        System.out.print("connecting to " + url);
        Connection con = DriverManager.getConnection(url, user, password);
        System.out.println(" /");
        return con;
    }

    /* */

    /*
     * public void insert(Statement stmt) throws SQLException { exec(stmt,
     * "drop table if exists test"); exec(stmt,
     * "create table if not exists test(a int, b int) row format delimited fields terminated by ' '"
     * );
     * 
     * exec(stmt, "drop table if exists dual"); exec(stmt,
     * "create table dual as select 1 as one from test");
     * 
     * exec(stmt,
     * "insert into table test select stack(1,4,5) AS (a,b) from dual");
     * 
     * // String q2 = "select * from (values(" + key + ", '" + value + //
     * "')) as t"; // q = q + " (" + q2 + ")";
     * 
     * }
     */

    public void insertInto(Statement stmt, String tableName, int key, String value) throws SQLException {
        String q = "insert into table " + tableName;
        q = q + " values (" + key + ", \"" + value + "\")";
        exec(stmt, q);
    }

    /* */

    public void count(Statement stmt, String tableName) throws SQLException {
        String sql;
        ResultSet res;
        sql = "select count(1) from " + tableName;
        System.out.println("Running: " + sql);
        res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res.getString(1));
        }
    }

    public ResultSet selectAll(Statement stmt, String tableName) throws SQLException {
        return selectAll(stmt, tableName, false);
    }

    public ResultSet selectAll(Statement stmt, String tableName, boolean print) throws SQLException {
        String sql;
        ResultSet res;
        sql = "select * from " + tableName;
        System.out.println("Running: " + sql);
        res = stmt.executeQuery(sql);
        if (print)
            while (res.next()) {
                System.out.println(String.valueOf(res.getInt(1)) + "\t" + res.getString(2));
            }
        return res;
    }

    // NOTE: filepath has to be local to the hive server
    // NOTE: /tmp/a.txt is a ctrl-A separated file with two fields per line
    public void loadData(Statement stmt, String tableName, String localInpath) throws SQLException {
        String sql = "load data local inpath '" + localInpath + "' into table " + tableName;
        exec(stmt, sql);
    }

    /* */

    public void describe(Statement stmt, String tableName) throws SQLException {
        String sql;
        ResultSet res;
        sql = "describe " + tableName;
        System.out.println("Running: " + sql);
        res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res.getString(1) + "\t" + res.getString(2));
        }
    }

    public void showTables(Statement stmt) throws SQLException {
        String sql = "show tables";
        System.out.println("Running: " + sql);
        ResultSet res = stmt.executeQuery(sql);
        if (res.next()) {
            System.out.println(res.getString(1));
        }
    }

    /* */

    public void dropTable(Statement stmt, String tableName) throws SQLException {
        exec(stmt, "drop table if exists " + tableName);
    }

    public void createTable(Statement stmt, String tableName, String schema) throws SQLException {
        // exec(stmt, "create table if not exists " + tableName );
        exec(stmt, "create table " + tableName + " " + schema);
    }

    /* */

    public void exec(Statement stmt, String command) throws SQLException {
        exec(stmt, command, true);
    }

    public void exec(Statement stmt, String command, boolean print) throws SQLException {
        if (print)
            System.out.print("exec: " + command);
        stmt.execute(command);
        if (print)
            System.out.println("  /");
    }

}