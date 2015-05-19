package org.jzy3d.demos.drawing.datebar;

import java.util.ArrayList;
import java.util.List;

import org.jblas.util.Random;
import org.joda.time.DateTime;
import org.jzy3d.demos.drawing.datebar.HistogramDate.TimeMode;

public class DateTimeGenerator {

    public List<DateTime> makeGaussianEvents(DateTime now, int n, int width, TimeMode timeMode, int nEarly) {
        System.out.println(n + " events around " + now + " with +-" + width + " " + timeMode);

        List<DateTime> events = new ArrayList<DateTime>();
        for (int i = 0; i < n; i++) {
            DateTime evt = randomEventAround(now, timeMode, width);
            events.add(evt);
        }
        for (int i = 0; i < nEarly; i++) {
            events.add(eventBefore(now, -width, timeMode));

        }
        return events;
    }

    public DateTime randomEventAround(DateTime now, TimeMode timeMode, int width) {
        int mm = (int) (Random.nextGaussian() * width);

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

    public DateTime eventBefore(DateTime now, int mm, TimeMode timeMode) {
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
    
    public DateTime shift(DateTime now, DateTime evt, TimeMode timeMode, int width) {
        int mm = width * 5;
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

}
