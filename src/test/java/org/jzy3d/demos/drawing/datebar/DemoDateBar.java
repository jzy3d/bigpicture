package org.jzy3d.demos.drawing.datebar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jblas.util.Random;
import org.joda.time.DateTime;
import org.jzy3d.chart.Chart;
import org.jzy3d.colors.Color;
import org.jzy3d.demos.BigPicture;
import org.jzy3d.demos.BigPicture.Type;
import org.jzy3d.demos.drawing.datebar.HistogramDate.TimeMode;
import org.jzy3d.maths.TicToc;
import org.jzy3d.plot3d.primitives.axes.AxeBox;
import org.jzy3d.plot3d.rendering.canvas.Quality;

// WARN : readability of bins will depend on time interval : very large interval = small bins
// TODO : make log transform work : readable with 100 but not with 100.000
// TODO : SHOULD force number of bins : here depends on number of steps in a given time range
public class DemoDateBar {
    // data generation
    public static int MILLION = 1000000;
    public static int N = (int) (0.1 * MILLION);
    public static TimeMode timeMode = TimeMode.HOUR;// SECOND MIGHT FAIL
    public static int WIDTH = 10;
    public static int N_EARLY = 2;

    public static Type dimensions = Type.dd;

    public static void main(String[] args) throws IOException {
        DateTime start = new DateTime("2015-05-02T08:00:00");
        DateTime stop = new DateTime("2015-05-02T12:00:00");
        // evt = shift(now, evt);

        DateTimeGenerator gen = new DateTimeGenerator();
        List<DateTime> events = gen.makeGaussianEvents(start, N / 2, WIDTH, timeMode, N_EARLY);
        List<DateTime> events2 = gen.makeGaussianEvents(stop, N / 2, WIDTH, timeMode, N_EARLY);

        // Model
        TicToc.T.tic();
        HistogramDate hist = new HistogramDate(events);
        System.out.println(hist.ranges.length + " bins");
        //HistogramDate hist2 = new HistogramDate(events2);
        TicToc.T.tocShow("gen hist");

        // Drawable
        TicToc.T.tic();
        HistogramDate2d histogram = new HistogramDate2d(hist, Color.CYAN, Color.GRAY);
        //HistogramDate2d histogram2 = new HistogramDate2d(hist2, Color.MAGENTA, Color.GRAY);
        TicToc.T.tocShow("made drawable");

        // Chart
        Chart chart = BigPicture.chart(histogram.getDrawable(), dimensions, Quality.Nicest);
        //chart.add(histogram2.getDrawable());
        //if (Type.dd.equals(dimensions))
            histogram.layout(chart);
            chart.add(histogram.buildLine(hist));
        // chart.getView().setSquared(false);
        
        
    }




}
