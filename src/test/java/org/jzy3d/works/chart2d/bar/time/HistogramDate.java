package org.jzy3d.works.chart2d.bar.time;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

public class HistogramDate {
    protected DateRange[] ranges ;
    protected Map<DateRange,Integer> data;
    
    public class DateRange{
        public DateTime min;
        public DateTime max;
        
        public DateRange(DateTime min, DateTime max) {
            this.min = min;
            this.max = max;
        }


        public boolean isIn(DateTime value){
            return isIn(value, false, true);
        }

        public boolean isIn(DateTime value, boolean includingMin, boolean includingMax){
            if(value.isBefore(min))//if(includingMax?value.isAfter(max)>max:value>=max)
                return false;
            if(value.isAfter(max))//if(includingMax?value.isAfter(max)>max:value>=max)
                return false;
            return true;
        }
    }
    
    public HistogramDate(List<DateTime> events){
        initBins(events);
    }

    private static final int MIN_SEEK = 3;

    private void initBins(List<DateTime> events) {
        data = new HashMap<DateRange,Integer>();
        DateTime dmin = min(events);
        DateTime dmax = max(events);

        int diffDay = Days.daysBetween(dmin.toLocalDate(), dmax.toLocalDate()).getDays();
      int diffHou = Hours.hoursBetween(dmin.toLocalDate(), dmax.toLocalDate()).getHours();

        /*if(diffDay>MIN_SEEK){
        
        }
        else if(diffHou>MIN_SEEK){
            
        }
        else if(diffMin>MIN_SEEK){
            
        }*/

        int diffMin = Minutes.minutesBetween(dmin, dmax).getMinutes();
        int diffSec = Seconds.secondsBetween(dmin.toLocalDate(), dmax.toLocalDate()).getSeconds();

        
        int diff = diffMin;
        int bins = diff;
        
        ranges = new DateRange[bins];

        DateTime rmin = dmin;
        for (int i = 0; i < bins - 1; i++) {
            DateTime next = rmin.plusMinutes(1);
            ranges[i] = new DateRange(rmin, next);
            data.put(ranges[i], 0);
            rmin = next;
        }
        ranges[bins-1] = new DateRange(rmin, dmax);
        data.put(ranges[bins-1], 0);
        
        add(events);
    }

    public void add(List<DateTime> values){
        for(DateTime v : values){
            add(v);
        }
    }
    
    public void add(DateTime value){
        for(Entry<DateRange,Integer> e: data.entrySet()){
            DateRange r = e.getKey();
            if(r.isIn(value)){
                e.setValue(e.getValue()+1);
                return;
            }
        }
        illegalValueException(value);
    }

    private void illegalValueException(DateTime value) {
        StringBuilder sb = new StringBuilder();
        String m = "value could not be added to any pre-configured bin. "
                + "Are you adding a value out of the min-max range you used to build " 
                + HistogramDate.class.getSimpleName() + "?";
        sb.append(m + "\n");
        sb.append("min:" + ranges[0].min + "\n");
        sb.append("max:" + ranges[ranges.length-1].max + "\n");
        sb.append("value:" + value + "\n");
        throw new IllegalArgumentException(sb.toString());
    }
    
    public DateRange[] ranges(){
        return ranges;
    }

    public int getCount(int bin) {
        return data.get(ranges[bin]);
    }
    
    public void setCount(int bin, int value) {
        data.put(ranges[bin], value);
    }

    public void console() {
        for (int i = 0; i < ranges.length; i++) {
            System.out.println(ranges[i] + " : " + data.get(ranges[0]));
        }
    }

    
    public int computeMaxCount() {
        int max = Integer.MIN_VALUE;
        for(Entry<DateRange,Integer> e : data.entrySet()){
            int v = e.getValue();
            if(v>max)
                max = v;
        }
        return max;
    }
    
    /* */

    public static DateTime min(List<DateTime> events) {
        DateTime min = null;
        for (DateTime d : events) {
            if (min == null) {
                min = d;
            } else {
                if (min.isAfter(d))
                    min = d;
            }
        }
        return min;
    }
    
    public static DateTime max(List<DateTime> events) {
        DateTime max = null;
        for (DateTime d : events) {
            if (max == null) {
                max = d;
            } else {
                if (max.isBefore(d))
                    max = d;
            }
        }
        return max;
    }
}
