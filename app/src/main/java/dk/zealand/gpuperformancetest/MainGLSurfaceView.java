package dk.zealand.gpuperformancetest;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class MainGLSurfaceView  extends GLSurfaceView {

    private final MainGLRenderer renderer;

    public MainGLSurfaceView(Context context){
        super(context);

        setEGLContextClientVersion(2);

        renderer = new MainGLRenderer();

        setRenderer(renderer);

        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
