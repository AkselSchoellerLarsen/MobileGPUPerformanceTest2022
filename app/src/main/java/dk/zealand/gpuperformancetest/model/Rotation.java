package dk.zealand.gpuperformancetest.model;

import android.opengl.Matrix;
import android.util.Log;

import dk.zealand.gpuperformancetest.model.matrix.Mat4f;
import dk.zealand.gpuperformancetest.model.vector.Vec3f;

public class Rotation {
	private Vec3f yawPitchRoll;

	public Rotation() {
		this(0, 0, 0);
	}
	
	public Rotation(float yaw, float pitch, float roll) {
		this.yawPitchRoll = new Vec3f(yaw, pitch, roll);
	}

	public void rotate(float yaw, float pitch, float roll) {
		Vec3f by = new Vec3f(yaw, pitch, roll);
		this.yawPitchRoll = yawPitchRoll.sum(by);
	}

	public Vec3f getYawPitchRoll() {
		return yawPitchRoll;
	}

	public float yaw() {
		return yawPitchRoll.x();
	}

	public float pitch() {
		return yawPitchRoll.y();
	}

	public float roll() {
		return yawPitchRoll.z();
	}

	public Mat4f getRotationMatrix() {
		float[] tmp = new float[16];

		Matrix.setRotateEulerM(tmp, 0, yaw(), pitch(), roll());

		return Mat4f.fromRowMajorVector(tmp);
	}
}