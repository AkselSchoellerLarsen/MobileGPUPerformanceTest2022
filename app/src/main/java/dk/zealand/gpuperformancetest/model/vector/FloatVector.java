package dk.zealand.gpuperformancetest.model.vector;

import androidx.annotation.NonNull;

import dk.zealand.gpuperformancetest.model.DimensionException;

public class FloatVector {
    protected float[] vector;

    public FloatVector(float[] vector) {
        this.vector = vector;
    }

    public float[] getVector() {
        return vector;
    }
    public int dimensions() {
        return vector.length;
    }
    public float length() {
        float re = 0;

        for(int i=0; i<vector.length; i++) {
            re += vector[i]*vector[i];
        }
        re = (float)Math.sqrt(re);

        return re;
    }

    public FloatVector sum(@NonNull FloatVector other) throws DimensionException {
        if(this.dimensions() == other.dimensions()) {
            float[] re = new float[dimensions()];
            for(int i=0; i<dimensions(); i++) {
                re[i] = this.getVector()[i] + other.getVector()[i];
            }
            return new FloatVector(re);
        }
        throw new DimensionException("Cannot sum two vectors with different dimensions.");
    }

    public FloatVector subtract(@NonNull FloatVector other) throws DimensionException {
        if(this.dimensions() == other.dimensions()) {
            float[] re = new float[dimensions()];
            for(int i=0; i<dimensions(); i++) {
                re[i] = this.getVector()[i] - other.getVector()[i];
            }
            return new FloatVector(re);
        }
        throw new DimensionException("Cannot subtract two vectors with different dimensions.");
    }

    public FloatVector multiply(float scalar) {
        float[] re = new float[dimensions()];
        for(int i=0; i<dimensions(); i++) {
            re[i] = vector[i] * scalar;
        }
        return new FloatVector(re);
    }

    @Override
    public String toString() {
        String re = "";
        for(int i=0; i<dimensions(); i++) {
            re += vector[i] + "";
            if(i < dimensions() - 1) {
                re += ", ";
            }
        }
        re += ";\n";
        return re;
    }
}
