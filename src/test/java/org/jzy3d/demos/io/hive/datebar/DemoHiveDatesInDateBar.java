package org.jzy3d.demos.io.hive.datebar;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.jzy3d.chart.Chart;
import org.jzy3d.colors.Color;
import org.jzy3d.demos.BigPicture;
import org.jzy3d.demos.BigPicture.Type;
import org.jzy3d.demos.drawing.datebar.HistogramDate;
import org.jzy3d.demos.drawing.datebar.HistogramDate2d;
import org.jzy3d.demos.io.hive.GenerateTimeDistribToHiveTable;
import org.jzy3d.io.hive.jdbc.HiveJdbcClient;
import org.jzy3d.maths.TicToc;
import org.jzy3d.plot3d.rendering.canvas.Quality;

public class DemoHiveDatesInDateBar {
    static String ip = "172.16.255.136";
    static String port = "10000";
    static String domain = "default";
    static String user = "root";
    static String password = "hadoop";
    static String table = "employee_logins";
    
    public static Type dims = Type.dd;

    public static void main(String[] args) throws SQLException {
        // Read
        List<DateTime> events = getDates(ip, port, domain, user, password, table);
        System.out.println("read " + events.size() + " events");
        plot(events);
    }

    private static void plot(List<DateTime> events) {
        // Model
        TicToc.T.tic();
        HistogramDate hist = new HistogramDate(events);
        System.out.println(hist.getRanges().length + " bins");
        TicToc.T.tocShow("gen hist");

        // Drawable
        TicToc.T.tic();
        HistogramDate2d histogram = new HistogramDate2d(hist, Color.CYAN, Color.GRAY);
        TicToc.T.tocShow("made drawable");

        // Chart
        Chart chart = BigPicture.chart(histogram.getDrawable(), dims, Quality.Nicest);
        histogram.layout(chart);
    }

    public static List<DateTime> getDates(String ip, String port, String domain, String user, String password, String table) throws SQLException {
        // connect
        HiveJdbcClient hive = new HiveJdbcClient();
        Statement stmt = hive.connect(ip, port, domain, user, password).createStatement();

        List<DateTime> dates = new ArrayList<DateTime>();
        ResultSet rs = hive.selectAll(stmt, table);
        while (rs.next()) {
            String value = rs.getString(1);
            dates.add(new DateTime(value));
        }
        return dates;
    }

}
