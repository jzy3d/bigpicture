package org.jzy3d.io.spark;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.jzy3d.io.spark.csv.Coord2dCsvParser;
import org.jzy3d.io.spark.csv.Coord3dCsvParser;
import org.jzy3d.maths.Coord2d;
import org.jzy3d.maths.Coord3d;

public class SparkChartIO {
    static int threads = 6;
    static JavaSparkContext sc = new JavaSparkContext("local["+threads+"]", "loadwholecsv", System.getenv("SPARK_HOME"), System.getenv("JARS"));

    public static JavaSparkContext context() {
        return sc;
    }

    public static JavaRDD<Coord3d> csv3d(String file) {
        return context().textFile(file).map(new Coord3dCsvParser());
    }

    public static JavaRDD<Coord2d> csv2d(String file) {
        return context().textFile(file).map(new Coord2dCsvParser());
    }
}
