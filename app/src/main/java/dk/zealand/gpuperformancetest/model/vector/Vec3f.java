package dk.zealand.gpuperformancetest.model.vector;

import androidx.annotation.NonNull;

public class Vec3f extends FloatVector {
    public Vec3f(float x, float y, float z) {
        super(new float[]{x, y, z});
    }

    public float x() {
        return vector[0];
    }
    public float y() {
        return vector[1];
    }
    public float z() {
        return vector[2];
    }

    public Vec3f sum(@NonNull Vec3f other) {
        float x = this.x() + other.x();
        float y = this.y() + other.y();
        float z = this.z() + other.z();
        return new Vec3f(x, y, z);
    }

    public Vec3f subtract(@NonNull Vec3f other) {
        float x = this.x() - other.x();
        float y = this.y() - other.y();
        float z = this.z() - other.z();
        return new Vec3f(x, y, z);
    }

    public Vec3f multiply(float scalar) {
        float x = this.x() * scalar;
        float y = this.y() * scalar;
        float z = this.z() * scalar;
        return new Vec3f(x, y, z);
    }
}
