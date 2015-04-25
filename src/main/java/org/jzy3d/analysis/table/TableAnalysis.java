package org.jzy3d.analysis.table;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jzy3d.chart.Chart;
import org.jzy3d.colors.Color;
import org.jzy3d.demos.BigPicture;
import org.jzy3d.demos.BigPicture.Type;
import org.jzy3d.demos.vbo.barmodel.builder.VBOBuilderLineStrip;
import org.jzy3d.demos.vbo.barmodel.builder.VBOBuilderTableColumnsScatter3d;
import org.jzy3d.io.KeyVal;
import org.jzy3d.maths.Histogram;
import org.jzy3d.maths.Statistics;
import org.jzy3d.plot2d.primitives.Histogram2d;
import org.jzy3d.plot3d.primitives.vbo.drawable.DrawableVBO;

import com.google.common.collect.ArrayListMultimap;

/**
 * Given a Table scan scheduler, will build all charts for an analysis and dump
 * pictures to disk.
 * 
 * @author martin
 */
public class TableAnalysis {
    protected DefaultTableScanScheduler scheduler;

    protected boolean black = false;

    public TableAnalysis(DefaultTableScanScheduler scheduler) {
        this.scheduler = scheduler;
    }

    /* REPORTING */

    /**
     * Delete input directory and generate all charts as file.
     * 
     * @throws IOException
     */
    public void report(File output) throws IOException {
        if (output.exists()) {
            output.delete();
        }
        output.mkdirs();

        List<List<KeyVal<String, Float>>> table = scheduler.getTable();

        int imgWidth = 600;
        int imgHeight = 600;

        StringBuffer html = new StringBuffer();
        if (black)
            html.append("<body bgcolor=\"000000\"><table>");
        else
            html.append("<body><table>");
        // Overview chart
        reportOverview(output, table, html, imgWidth, imgHeight);

        // Column charts
        ArrayListMultimap<String, Float> columns = scheduler.computeColumns();

        for (String columnName : columns.keySet()) {
            List<Float> values = columns.get(columnName);
            System.out.println("ADDING COL : " + columnName);

            reportColumn(output, columnName, values, html, imgWidth, imgHeight);
        }

        html.append("</table></body>");

        writeToFile(new File(output, "0-all.html"), html);
    }

    private void reportOverview(File output, List<List<KeyVal<String, Float>>> table, StringBuffer html, int width, int height) throws IOException {
        String imageName = "0-overview.png";
        File fileOverview = new File(output, imageName);
        DrawableVBO drawable = new DrawableVBO(new VBOBuilderTableColumnsScatter3d(table));
        offscreen2d(width, height, fileOverview, drawable);

        html.append("<tr><td>\n");
        html.append("<h1>Table Overview</h1>\n");
        html.append("<img src=\"" + imageName + "\"/>\n");
        html.append("</td></tr>\n");
    }

    private void reportColumn(File output, String columnName, List<Float> values, StringBuffer html, int width, int height) throws IOException {
        html.append("<tr><td><h1>Column : " + columnName + "</h1><td><tr>\n");

        html.append("<tr><td>\n");
        html.append("<h2>Values</h2>\n");
        try {
            reportLineChart(output, columnName, values, html, width, height);
        } catch (Exception e) {
            html.append("<p>" + org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e) + "</p>\n");
        }
        html.append("</td><td>\n");

        html.append("<h2>Density</h2>\n");
        try {
            reportBarChart(output, columnName, values, html, width, height);
        } catch (Exception e) {
            html.append("<p>" + org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e) + "</p>\n");
        }
        html.append("</td></tr>\n");

    }

    private void reportLineChart(File output, String columnName, List<Float> values, StringBuffer html, int width, int height) throws IOException {
        String imageName = "1-column-" + columnName + "-values.png";
        File fileLines = new File(output, imageName);

        DrawableVBO drawableLine = new DrawableVBO(new VBOBuilderLineStrip(values));

        offscreen2d(width, height, fileLines, drawableLine);

        html.append("<img src=\"" + imageName + "\"/>\n");
    }

    private void reportBarChart(File output, String columnName, List<Float> values, StringBuffer html, int width, int height) throws IOException {
        String imageName = "1-column-" + columnName + "-density.png";
        File fileBarChart = new File(output, imageName);

        float min = Statistics.min(values);
        float max = Statistics.max(values);
        Histogram hist = new Histogram(min, max, 20);
        hist.add(values);

        Histogram2d histogram = new Histogram2d(hist);
        if (black)
            histogram.getDrawable().setWireframeColor(Color.WHITE);
        else
            histogram.getDrawable().setWireframeColor(Color.BLACK);

        Chart chart = BigPicture.offscreen(histogram.getDrawable(), Type.dd, width, height).view2d();
        if (black)
            chart.black();
        histogram.layout(chart);
        chart.screenshot(fileBarChart);

        html.append("<img src=\"" + imageName + "\"/>\n");
    }

    private void offscreen2d(int width, int height, File file, DrawableVBO drawable) throws IOException {
        Chart chart = BigPicture.offscreen(drawable, Type.dd, width, height);
        if (black)
            chart.black();
        chart.screenshot(file);
    }

    /* */

    /** dump html index */
    public void writeToFile(File file, StringBuffer text) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(text.toString());
        out.flush();
        out.close();
    }

    public DefaultTableScanScheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(DefaultTableScanScheduler scheduler) {
        this.scheduler = scheduler;
    }
}
