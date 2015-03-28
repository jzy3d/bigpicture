package org.jzy3d.demos.spark;

import javafx.application.Application;
import javafx.stage.Stage;

import org.apache.spark.api.java.JavaRDD;
import org.jzy3d.chart.spark.SparkRDDChartBuilderJavaFX;
import org.jzy3d.io.spark.SparkChartIO;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot2d.primitives.ScatterPointSerie2d;
import org.jzy3d.plot2d.primitives.Serie2d;

/**
 * Shows a 2D scatter plot given by a spark RDD loaded from a CSV file.
 * 
 * The scatter is rendered offscreen in an image that is later displayed in a JavaFX application.
 * 
 * @author Martin Pernollet
 */
@SuppressWarnings("restriction")
public class DemoSparkCsvFileJavaFXChart extends Application{
    
    public static void main(String[] args) {
        Application.launch(DemoSparkCsvFileJavaFXChart.class);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Jzy3d | JavaFX | Spark");

        new SparkRDDChartBuilderJavaFX(){
            @Override
            public void loadDataAndBuildScene() {
                layout2d();
                JavaRDD<Coord3d> coordinates = SparkChartIO.csv3d("data/random/random-4000.csv");
                makeScatterSerie2d(coordinates);
            }
        }.onStart(stage); // run actual data loading and chart generation
    }
}
