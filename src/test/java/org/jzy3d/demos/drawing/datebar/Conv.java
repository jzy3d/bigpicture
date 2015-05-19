package org.jzy3d.demos.drawing.datebar;

import org.joda.time.DateTime;

public class Conv {

    public static void main(String[] args) {
        String s1 = "2015-05-02T08:22:00.000+02:00";
        String s2 = "2015-05-02T08:23:00.000+02:00";
        
        DateTime t1 = new DateTime(s1);
        DateTime t2 = new DateTime(s2);
        
        System.out.println(t2.getMillis() - t1.getMillis());
        System.out.println((float)t2.getMillis() - (float)t1.getMillis());
        System.out.println((double)t2.getMillis() - (double)t1.getMillis());
    }
}
