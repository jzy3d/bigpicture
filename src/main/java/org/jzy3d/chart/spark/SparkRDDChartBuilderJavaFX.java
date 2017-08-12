package org.jzy3d.chart.spark;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.jzy3d.chart.AWTChart;
import org.jzy3d.javafx.JavaFXChartFactory;
import org.jzy3d.javafx.JavaFXRenderer3d;
import org.jzy3d.javafx.controllers.mouse.JavaFXCameraMouseController;

/**
 * Rendering is achieved by piping an offscreen calculated Jzy3d chart image to a JavaFX ImageView.
 * 
 * 3D views 
 * <ul>
 * <li>use mouse to rotate view
 * <li>support automatic rotation control with left mouse button hold+drag Scaling scene using
 * mouse wheel Animation (camera rotation with thread)
 * <li>TODO : Mouse right click shift Keyboard support (rotate/shift, etc)
 * </ul>
 * 
 * Components
 * {@link JavaFXChartFactory} delivers dedicated
 * {@link JavaFXCameraMouseController} and {@link JavaFXRenderer3d}
 * 
 * @author Martin Pernollet
 */
@SuppressWarnings("restriction")
public abstract class SparkRDDChartBuilderJavaFX extends SparkRDDChartBuilder {

    /** to be implemented */
    public abstract void loadDataAndBuildScene();

    public void onStart(Stage stage) {
        chart = chart(factory, "offscreen");
        loadDataAndBuildScene();
        show(stage, chart);
    }

    protected void show(Stage stage, AWTChart chart) {
        // Binding 1 : Chart -> JavaFX
        // The view will be notified if the chart redraws 
        // for internal reason (scene graph update, etc)
        ImageView imageView = factory.bindImageView(chart);

        // JavaFX container
        StackPane pane = new StackPane();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        pane.getChildren().add(imageView);

        // Binding 2 : JavaFX -> Chart
        // Listening to JavaFX size change to trigger repaint
        factory.addSceneSizeChangedListener(chart, scene);
    }
}
