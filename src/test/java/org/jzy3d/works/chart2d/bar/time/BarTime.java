package org.jzy3d.works.chart2d.bar.time;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jblas.util.Random;
import org.joda.time.DateTime;
import org.jzy3d.chart.Chart;
import org.jzy3d.colors.Color;
import org.jzy3d.demos.BigPicture;
import org.jzy3d.demos.BigPicture.Type;
import org.jzy3d.works.chart2d.bar.time.HistogramDate.TimeMode;

// WARN : readability of bins will depend on time interval : very large interval = small bins
// TODO : make log transform work : readable with 100 but not with 100.000
public class BarTime {
    public static int N = 10000;
    public static TimeMode timeMode = TimeMode.HOUR;//SECOND MIGHT FAIL
    public static int WIDTH = 100;
    public static Type dimensions = Type.dd;
    
    public static int N_EARLY = 2;
    
    public static void main(String[] args) throws IOException {
        
        DateTime now = new DateTime();

        List<DateTime> events = new ArrayList<DateTime>();
        for (int i = 0; i < N; i++) {
            DateTime evt = randomEventAround(now);
            events.add(evt);
        }
        for (int i = 0; i < N_EARLY; i++) {
            events.add(eventBefore(now, - WIDTH * 4));
            
        }

        HistogramDate hist = new HistogramDate(events);
        HistogramDate2d histogram = new HistogramDate2d(hist);
        histogram.getDrawable().setWireframeColor(Color.BLACK);
        System.out.println(histogram.getDrawable().getBounds());
        
        // chart
        Chart chart = BigPicture.chart(histogram.getDrawable(), dimensions);
//chart.getView().setSquared(false);
        if(Type.dd.equals(dimensions))
            histogram.layout(chart);
    }


    private static DateTime randomEventAround(DateTime now) {
        int mm = (int) (Random.nextGaussian() * WIDTH);
        
        DateTime evt = now.plusDays(mm);
        
        if (TimeMode.DAY.equals(timeMode)) {
            evt = now.plusDays(mm);
        } else if (TimeMode.HOUR.equals(timeMode)) {
            evt = now.plusHours(mm);
        } else if (TimeMode.MINUTE.equals(timeMode)) {
            evt = now.plusMinutes(mm);
        } else if (TimeMode.SECOND.equals(timeMode)) {
            evt = now.plusSeconds(mm);
        }
        
        return evt;
    }

    private static DateTime eventBefore(DateTime now, int mm) {
        DateTime evt = now.plusDays(mm);
        
        if (TimeMode.DAY.equals(timeMode)) {
            evt = now.plusDays(mm);
        } else if (TimeMode.HOUR.equals(timeMode)) {
            evt = now.plusHours(mm);
        } else if (TimeMode.MINUTE.equals(timeMode)) {
            evt = now.minusMinutes(mm);
        } else if (TimeMode.SECOND.equals(timeMode)) {
            evt = now.plusSeconds(mm);
        }
        return evt;
    }

}
