package dk.zealand.gpuperformancetest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.Date;

public class MainActivity extends Activity {

    private static boolean useGPU = false;

    private MainGLSurfaceView glSurfaceView;
    private MainCPURender cpuSurfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(useGPU) {
            glSurfaceView = new MainGLSurfaceView(this);
            setContentView(glSurfaceView);
        } else {
            cpuSurfaceView = new MainCPURender(this);

            setupInvalidater(cpuSurfaceView);

            setContentView(cpuSurfaceView);
        }
    }

    private void setupInvalidater(MainCPURender cpuSurfaceView) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(true) {
                    cpuSurfaceView.invalidate();
                }
            }
        };

        Thread thread = new Thread(runnable, "CONSTANT_DRAW_THREAD");
        thread.start();
    }
}