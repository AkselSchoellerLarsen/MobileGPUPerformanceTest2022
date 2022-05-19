package dk.zealand.gpuperformancetest.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Date;

import dk.zealand.gpuperformancetest.Shaders;
import dk.zealand.gpuperformancetest.model.matrix.FloatMatrix;
import dk.zealand.gpuperformancetest.model.matrix.Mat4f;
import dk.zealand.gpuperformancetest.model.vector.Vec3f;

public class Polygon {
    private Vec3f[] vertices;
    private short[] indices;
    private int shaderProgram;
    private FloatBuffer vertexBuffer;
    private float[] color;

    public Vec3f offset;
    public Vec3f scale;
    public Rotation rotation;

    public Polygon(int sides, Vec3f offset, Vec3f scale, Vec3f color) {
        defineShape(sides);

        this.color = color.getVector();
        this.offset = offset;
        this.scale = scale;
        this.rotation = new Rotation();

        setup();
    }

    public Polygon(int sides, Vec3f offset) {
        this(sides, offset, new Vec3f(1.0f, 1.0f, 1.0f), new Vec3f(0f, 0f, 0f));
    }

    private void defineShape(int sides) {
        vertices = new Vec3f[sides];
        for(int i=0; i<sides; i++) {
            float x = (float) Math.cos((Math.PI * 2 * i) / sides);
            float y = (float) Math.sin((Math.PI * 2 * i) / sides);
            float z = 0;

            vertices[i] = new Vec3f(x, y, z);
        }

        //fan triangulation: https://en.wikipedia.org/wiki/Fan_triangulation
        indices = new short[(sides-2)*3];
        for(int i=0; i<sides-2; i++) {
            indices[i*3] = 0;
            indices[i*3+1] = (short) (i+1);
            indices[i*3+2] = (short) (i+2);
        }
    }

    private void setup() {
        setupVertexBuffer();
    }

    public void setupShaderProgram() {
        int vertexShader = Shaders.loadShader(GLES20.GL_VERTEX_SHADER,
                Shaders.vertexShaderCodePolygon);
        int fragmentShader = Shaders.loadShader(GLES20.GL_FRAGMENT_SHADER,
                Shaders.fragmentShaderCodePolygon);

        String vertexShaderError = GLES20.glGetShaderInfoLog(vertexShader);
        String fragmentShaderError = GLES20.glGetShaderInfoLog(fragmentShader);
        if(vertexShaderError.length() > 0) {
            Log.e("TAG_NOT_USED_BY_OTHERS", vertexShaderError);
        }
        if(fragmentShaderError.length() > 0) {
            Log.e("TAG_NOT_USED_BY_OTHERS", fragmentShaderError);
        }

        shaderProgram = GLES20.glCreateProgram();

        GLES20.glAttachShader(shaderProgram, vertexShader);
        GLES20.glAttachShader(shaderProgram, fragmentShader);
        GLES20.glLinkProgram(shaderProgram);
    }

    private void setupVertexBuffer() {
        float[] polygonCords = new float[indices.length*3];
        for(int i=0; i<indices.length; i++) {
            polygonCords[i*3+0] = vertices[indices[i]].x();
            polygonCords[i*3+1] = vertices[indices[i]].y();
            polygonCords[i*3+2] = vertices[indices[i]].z();
        }

        ByteBuffer bb = ByteBuffer.allocateDirect(polygonCords.length * 4); // 4 bytes per float
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(polygonCords);
        vertexBuffer.position(0);
    }

    public void draw(Mat4f viewMatrix, Mat4f projectionMatrix) {
        GLES20.glUseProgram(shaderProgram);
        int positionHandle = GLES20.glGetAttribLocation(shaderProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);

        GLES20.glVertexAttribPointer( // Rather complex call, so let me quickly explain
                positionHandle, // A reference to the "vPosition" attribute of our shader
                3, // Amount of the given datatype we are sending to each kernel
                GLES20.GL_FLOAT, // The datatype we are sending to each kernel
                false,
                0, // How many bytes are between the end of one entry and the start of another
                vertexBuffer); // The bytebuffer with the data

        int colorHandle = GLES20.glGetUniformLocation(shaderProgram, "vColor");
        GLES20.glUniform3fv(colorHandle, 1, color, 0);
        int perspectiveHandle = GLES20.glGetUniformLocation(shaderProgram, "perspectiveMatrix");
        GLES20.glUniformMatrix4fv(perspectiveHandle, 1, false, perspectiveMatrix(viewMatrix, projectionMatrix).asColumnMajorVector(), 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, indices.length);

        GLES20.glDisableVertexAttribArray(positionHandle);
    }

    public void draw(Canvas canvas) {
        float[] xs = new float[vertices.length];
        float[] ys = new float[vertices.length];

        try {
            for(int i=0; i<vertices.length; i++) {
                FloatMatrix mat41f = new FloatMatrix(new float[][]{
                        new float[] {vertices[i].x()},
                        new float[] {vertices[i].y()},
                        new float[] {vertices[i].z()},
                        new float[] {1.0f}
                });

                FloatMatrix tmp = worldMatrix().multiply(mat41f);
                xs[i] = tmp.getMatrix()[0][0] + offset.x();
                ys[i] = tmp.getMatrix()[1][0] + offset.y();
            }
        } catch (DimensionException e) {
            String err = "";
            for(StackTraceElement trace : e.getStackTrace()) {
                err += trace + "\n";
            }
            Log.e("TAG_NOT_USED_BY_OTHERS", err);
        }

        Path path = new Path();

        path.moveTo(xs[0], ys[0]);
        for(int i=1; i<xs.length; i++) {
            path.lineTo(xs[i], ys[i]);
        }
        path.lineTo(xs[0], ys[0]);

        //test below
/*
        path.reset();
        path.moveTo(0.0f,0.0f);
        path.lineTo(100.0f,0.0f);
        path.lineTo(100.0f,100.0f);
        path.lineTo(0.0f,100.0f);
        path.lineTo(0.0f,0.0f);
*/
        //test above

        Paint paint = new Paint();
        //paint.setColor(Color.rgb(color[0], color[1], color[2]));
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawPath(path, paint);
    }

    private Mat4f perspectiveMatrix(Mat4f viewMatrix, Mat4f projectionMatrix) {
        Mat4f tmp = worldMatrix().multiply(viewMatrix);

        return tmp.multiply(projectionMatrix);
    }

    private Mat4f worldMatrix() {
        Mat4f tmp = translationMatrix().multiply(rotationMatrix());
        return tmp.multiply(scaleMatrix());
    }

    private Mat4f translationMatrix() {
        Mat4f re = Mat4f.getIdentityMatrix();
        re.setWX(offset.x());
        re.setWY(offset.y());
        re.setWZ(offset.z());

        return re;
    }

    private Mat4f rotationMatrix() {
        return rotation.getRotationMatrix();
    }

    private Mat4f scaleMatrix() {
        Mat4f re = Mat4f.getIdentityMatrix();
        re.setXX(re.getXX()*scale.x());
        re.setYY(re.getYY()*scale.y());
        re.setZZ(re.getZZ()*scale.z());

        return re;
    }
}