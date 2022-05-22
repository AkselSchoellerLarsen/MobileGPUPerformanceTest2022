package dk.zealand.gpuperformancetest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import java.util.Date;

import dk.zealand.gpuperformancetest.model.Polygon;
import dk.zealand.gpuperformancetest.model.vector.Vec3f;

public class MainCPURender extends SurfaceView {

    private Polygon polygon;
    private int height;
    private int width;

    private int drawCounter;
    private long startTime;

    public MainCPURender(Context context) {
        super(context);

        //I have to tell the program not to use the GPU
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        this.setWillNotDraw(false);

        polygon = new Polygon(10000, new Vec3f(0, 0, 0), new Vec3f(100.0f, 100.0f, 100.0f), new Vec3f(0, 0, 0));

        drawCounter = 0;
        startTime = new Date().getTime();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        polygon.rotation.rotate(0,0,-0.1f);

        Log.v("TAG_NOT_USED_BY_OTHERS", "Average FPS: " + (drawCounter++ / ((new Date().getTime() - startTime)/1000.0f)));

        polygon.draw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;

        Log.i("TAG_NOT_USED_BY_OTHERS", "Screen size: " + width + ", " + height);

        int leastOfWidthAndHeight = 0;
        if(width > height) {
            leastOfWidthAndHeight = height;
        } else {
            leastOfWidthAndHeight = width;
        }

        polygon.scale = new Vec3f(leastOfWidthAndHeight/3.0f, leastOfWidthAndHeight/3.0f, 0.0f);

        polygon.offset = new Vec3f(width/2.0f, height/2.0f, 0.0f);
    }
}