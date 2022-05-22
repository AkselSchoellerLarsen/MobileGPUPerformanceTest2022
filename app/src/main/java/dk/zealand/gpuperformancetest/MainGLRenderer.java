package dk.zealand.gpuperformancetest;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import java.util.Date;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import dk.zealand.gpuperformancetest.model.Polygon;
import dk.zealand.gpuperformancetest.model.matrix.Mat4f;
import dk.zealand.gpuperformancetest.model.vector.Vec3f;

public class MainGLRenderer implements GLSurfaceView.Renderer {
    private Polygon polygon;

    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    private long startTime;
    private long frameCounter;

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glClearColor(0.9f, 0.9f, 0.9f, 1.0f);

        polygon = new Polygon(10000, new Vec3f(0, 0, 0), new Vec3f(0.5f, 0.5f, 0.5f), new Vec3f(0, 0, 0));
        polygon.setupShaderProgram();

        startTime = new Date().getTime();
        frameCounter = 0;
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        Log.v("TAG_NOT_USED_BY_OTHERS", "Frames per second " + (frameCounter++ / ((new Date().getTime() - startTime) / 1000.0f)));

        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        polygon.rotation.rotate(0.0f, 0.0f, 0.1f);

        polygon.draw(Mat4f.fromColumnMajorVector(viewMatrix), Mat4f.fromColumnMajorVector(projectionMatrix));
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }
}
