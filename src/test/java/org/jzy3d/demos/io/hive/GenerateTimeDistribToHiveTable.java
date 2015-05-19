package org.jzy3d.demos.io.hive;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.joda.time.DateTime;
import org.jzy3d.demos.drawing.datebar.DateTimeGenerator;
import org.jzy3d.demos.drawing.datebar.HistogramDate.TimeMode;
import org.jzy3d.io.hive.jdbc.HiveJdbcClient;

public class GenerateTimeDistribToHiveTable {
    public static String TABLE = "employee_logins2";

    public static int MILLION = 1000000;
    public static int N = (int) (1 * MILLION);
    public static TimeMode timeMode = TimeMode.HOUR;
    public static int WIDTH = 10;
    public static int N_EARLY = 2;

    public static void main(String[] args) throws SQLException {
        String ip = "172.16.255.136";
        String port = "10000";
        String domain = "default";
        String user = "root";
        String password = "hadoop";

        // actions
        boolean insert = true;
        boolean print = false;

        // connect
        HiveJdbcClient hive = new HiveJdbcClient();
        Statement stmt = hive.connect(ip, port, domain, user, password).createStatement();

        // work
        if (insert) {
            DateTimeGenerator gen = new DateTimeGenerator();
            List<DateTime> events = gen.makeGaussianEvents(new DateTime(), N, WIDTH, timeMode, N_EARLY);

            //hive.createTable(stmt, TABLE, " (timestamp string)");

            StringBuffer sb = new StringBuffer();
            sb.append("insert into table " + TABLE + " values ");
            for (int i = 0; i < events.size(); i++) {
                DateTime d = events.get(i);
                sb.append("(\"" + d.toString() + "\")");                    
                if(i<events.size()-1){
                    sb.append(", ");
                }
            }
            hive.exec(stmt, sb.toString(), false);

            hive.count(stmt, TABLE);
            if(print){
                hive.selectAll(stmt, TABLE);               
            }
        }
    }
}
