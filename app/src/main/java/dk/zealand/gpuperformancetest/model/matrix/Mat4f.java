package dk.zealand.gpuperformancetest.model.matrix;

import androidx.annotation.NonNull;

import dk.zealand.gpuperformancetest.model.DimensionException;

public class Mat4f extends FloatMatrix {

    public Mat4f() {
        super(4, 4);
    }
    public Mat4f(float[][] matrix) {
        super(matrix);

        //should throw error if not the dimensions.
    }

    public static final Mat4f getIdentityMatrix() {
        Mat4f identity = new Mat4f(new float[][] {
                new float[] { 1.0f, 0.0f, 0.0f, 0.0f },
                new float[] { 0.0f, 1.0f, 0.0f, 0.0f },
                new float[] { 0.0f, 0.0f, 1.0f, 0.0f },
                new float[] { 0.0f, 0.0f, 0.0f, 1.0f }
        });
        return identity;
    }

    public static Mat4f fromRowMajorVector(float[] rowMajorVector) {
        float[][] re = new float[4][];

        for(int x=0; x<4; x++) {
            re[x] = new float[4];
            for(int y=0; y<4; y++) {
                re[x][y] = rowMajorVector[y*4+x];
            }
        }

        return new Mat4f(re);
    }

    public static Mat4f fromColumnMajorVector(float[] columnMajorVector) {
        float[][] re = new float[4][];

        for(int x=0; x<4; x++) {
            re[x] = new float[4];
            for(int y=0; y<4; y++) {
                re[x][y] = columnMajorVector[x*4+y];
            }
        }

        return new Mat4f(re);
    }

    public float getXX() { return matrix[0][0]; }
    public float getXY() { return matrix[0][1]; }
    public float getXZ() { return matrix[0][2]; }
    public float getXW() { return matrix[0][3]; }
    public float getYX() { return matrix[1][0]; }
    public float getYY() { return matrix[1][1]; }
    public float getYZ() { return matrix[1][2]; }
    public float getYW() { return matrix[1][3]; }
    public float getZX() { return matrix[2][0]; }
    public float getZY() { return matrix[2][1]; }
    public float getZZ() { return matrix[2][2]; }
    public float getZW() { return matrix[2][3]; }
    public float getWX() { return matrix[3][0]; }
    public float getWY() { return matrix[3][1]; }
    public float getWZ() { return matrix[3][2]; }
    public float getWW() { return matrix[3][3]; }

    public void setXX(float f) { matrix[0][0] = f; }
    public void setXY(float f) { matrix[0][1] = f; }
    public void setXZ(float f) { matrix[0][2] = f; }
    public void setXW(float f) { matrix[0][3] = f; }
    public void setYX(float f) { matrix[1][0] = f; }
    public void setYY(float f) { matrix[1][1] = f; }
    public void setYZ(float f) { matrix[1][2] = f; }
    public void setYW(float f) { matrix[1][3] = f; }
    public void setZX(float f) { matrix[2][0] = f; }
    public void setZY(float f) { matrix[2][1] = f; }
    public void setZZ(float f) { matrix[2][2] = f; }
    public void setZW(float f) { matrix[2][3] = f; }
    public void setWX(float f) { matrix[3][0] = f; }
    public void setWY(float f) { matrix[3][1] = f; }
    public void setWZ(float f) { matrix[3][2] = f; }
    public void setWW(float f) { matrix[3][3] = f; }

    public Mat4f multiply(Mat4f other) {
        FloatMatrix re = getIdentityMatrix();

        try {
            re = super.multiply(other);
        } catch (DimensionException e) {
            e.printStackTrace();
        }

        return new Mat4f(re.matrix);
    }
}