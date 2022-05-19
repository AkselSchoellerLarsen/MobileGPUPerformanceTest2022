package dk.zealand.gpuperformancetest;

import android.opengl.GLES20;

public class Shaders {

    public static final String vertexShaderCodePolygon =
            "uniform mat4 perspectiveMatrix;" +
            "attribute vec3 vPosition;" +
            "void main() {" +
            "  gl_Position = perspectiveMatrix * vec4(vPosition, 1.0);" +
            "}";

    public static final String fragmentShaderCodePolygon =
            "precision mediump float;" +
            "uniform vec3 vColor;" +
            "void main() {" +
            "  gl_FragColor = vec4(vColor, 1.0);" +
            "}";

    public static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
