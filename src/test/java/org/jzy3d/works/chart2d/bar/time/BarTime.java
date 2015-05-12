package org.jzy3d.works.chart2d.bar.time;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jblas.util.Random;
import org.joda.time.DateTime;
import org.jzy3d.chart.Chart;
import org.jzy3d.colors.Color;
import org.jzy3d.demos.BigPicture;
import org.jzy3d.demos.BigPicture.Type;
import org.jzy3d.maths.Rectangle;

public class BarTime {
    public static int N = 10000;
    public static void main(String[] args) throws IOException {
        
        DateTime dateTime = new DateTime();// "2013-05-08 08:00:00"

        List<DateTime> events = new ArrayList<DateTime>();
        for (int i = 0; i < N; i++) {
            int mm = (int) (Random.nextGaussian() * 10);
            DateTime evt = dateTime.plusMinutes(mm);
            events.add(evt);
        }

        HistogramDate hist = new HistogramDate(events);

        HistogramDate2d histogram = new HistogramDate2d(hist);
        histogram.getDrawable().setWireframeColor(Color.BLACK);
        // chart
        Chart chart = BigPicture.chart(histogram.getDrawable(), Type.dd).view2d();
        //Chart chart = BigPicture.chart(histogram.getDrawable(), Type.ddd);
        histogram.layout(chart);
        //chart.open("bartime", new Rectangle(800, 600));
        System.out.println(chart.getView().getBounds());
        //image
        /*Chart chart = BigPicture.offscreen(histogram.getDrawable(), Type.dd, 1024, 768).view2d();
        histogram.layout(chart);
        File fileBarChart = new File("data/screenshots/", "bar-time.png");
        chart.screenshot(fileBarChart);*/

    }

}
