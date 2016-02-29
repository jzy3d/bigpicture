package org.jzy3d.demos;

import java.io.File;
import java.io.IOException;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.maths.Rectangle;
import org.jzy3d.plot3d.primitives.AbstractDrawable;
import org.jzy3d.plot3d.primitives.axes.AxeBox;
import org.jzy3d.plot3d.primitives.axes.IAxe;
import org.jzy3d.plot3d.primitives.axes.layout.IAxeLayout;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.view.AWTRenderer3d;
import org.jzy3d.plot3d.rendering.view.Renderer3d;
import org.jzy3d.plot3d.rendering.view.View;
import org.jzy3d.plot3d.rendering.view.ViewportMode;
import org.jzy3d.plot3d.rendering.view.modes.ViewPositionMode;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.awt.AWTGLReadBufferUtil;

public class BigPicture {
    public static String TITLE = "BigPicture";
    public static Quality DEFAULT_QUALITY = Quality.Advanced;

    public enum Type {
        /** 2D */
        dd, /** 3D */
        ddd
    }

    public static Chart chart(AbstractDrawable drawable, Type type, Quality quality) {
        return chart(drawable, type, "awt", false, new Rectangle(1000, 1000), quality);
    }

    public static Chart chart(AbstractDrawable drawable, Type type) {
        return chart(drawable, type, "awt", false, new Rectangle(1000, 1000), DEFAULT_QUALITY);
    }

    public static Chart chartBlack(AbstractDrawable drawable, Type type) {
        return chart(drawable, type, "awt", true, new Rectangle(1000, 1000), DEFAULT_QUALITY);
    }

    public static Chart offscreen(AbstractDrawable drawable, Type type) {
        return chart(drawable, type, "offscreen,"+1000+"," + 1000, false, null, DEFAULT_QUALITY);
    }

    
    public static Chart offscreen(AbstractDrawable drawable, Type type, int width, int height) {
        return chart(drawable, type, "offscreen,"+width+"," + height, false, null, DEFAULT_QUALITY);
    }

    
    public static Chart screenshot(AbstractDrawable drawable, Type type, int width, int height, File output) throws IOException {
        Chart chart = chart(drawable, type, "offscreen,"+width+"," + height, false, null, DEFAULT_QUALITY);
        screenshot(chart, output);
        return chart;
    }

    protected static void screenshot(Chart chart, File output) throws IOException {
        if (output.exists())
            output.delete();
        if (!output.getParentFile().exists())
            output.mkdirs();
        chart.getCanvas().screenshot(output);
        System.out.println("Dumped screenshot in: " + output);
    }


    @SuppressWarnings("static-access")
    public static Chart chart(AbstractDrawable drawable, Type type, String wt, boolean black, Rectangle rect, Quality quality) {
        Chart chart = null;//AWTChartComponentFactory.chart(Quality.Intermediate, wt);
        chart = new AWTChartComponentFactory(){
            @Override
            public Renderer3d newRenderer(View view, boolean traceGL, boolean debugGL) {
                return new AWTRenderer3d(view, traceGL, debugGL){
                    @Override
                    public void display(GLAutoDrawable canvas) {
                        GL gl = canvas.getGL();

                        if (view != null) {
                            view.clear(gl);
                            view.render(gl, glu);

                            if (doScreenshotAtNextDisplay) {
                                AWTGLReadBufferUtil screenshot = new AWTGLReadBufferUtil(GLProfile.getGL2GL3(), true);
                                screenshot.readPixels(gl, false);
                                image = screenshot.getTextureData();
                                bufferedImage = screenshot.readPixelsToBufferedImage(gl, false);
                                
                                doScreenshotAtNextDisplay = false;
                            }
                        }
                    }

                };
            }
            
            @Override
            public IAxe newAxe(BoundingBox3d box, View view) {
                AxeBox axe = new AxeBox(box);
                axe.setView(view);
//                axe.setTextRenderer(new JOGLTextRenderer());
                return axe;
            }
        }.chart(quality, wt);
        
        AxeBox axe = (AxeBox)chart.getView().getAxe();
//        axe.setTextRenderer(new TxtRender());
        //axe.setTextRenderer(new JOGLTextRenderer());
        
        
        chart.getScene().getGraph().add(drawable);
        if (black)
            chart.black();
        chart.getView().getCamera().setViewportMode(ViewportMode.STRETCH_TO_FILL);
        chart.addMouseController();
        if(!wt.contains("offscreen")){
            if (rect != null)
                chart.open(TITLE, rect.width, rect.height);
            else
                chart.open(TITLE, rect);           
        }
        if (type.equals(Type.dd))
            layout2d(chart);
        return chart;
    }

    public static void layout2d(Chart chart) {
        View view = chart.getView();
        view.setViewPositionMode(ViewPositionMode.TOP);
        view.getCamera().setViewportMode(ViewportMode.STRETCH_TO_FILL);
        IAxeLayout axe = chart.getAxeLayout();
        axe.setZAxeLabelDisplayed(false);
        axe.setTickLineDisplayed(false);
    }

}
