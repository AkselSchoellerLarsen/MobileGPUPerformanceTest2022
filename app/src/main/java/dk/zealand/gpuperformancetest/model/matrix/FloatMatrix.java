package dk.zealand.gpuperformancetest.model.matrix;

import androidx.annotation.NonNull;

import dk.zealand.gpuperformancetest.model.DimensionException;

public class FloatMatrix {
	protected float[][] matrix;
	
	public FloatMatrix(int x, int y, float f) {
		matrix = new float[x][y];
		
		for(int j=0; j<x; j++) {
			for(int k=0; k<y; k++) {
				matrix[j][k] = f;
			}
		}
	}
	public FloatMatrix(float[][] fltmtrx) {
		matrix = fltmtrx;
	}
	public FloatMatrix(int x, int y) {
		this(x, y, 0.0f);
	}
	
	public float[][] getMatrix() {
		return matrix;
	}

	public FloatMatrix sum(@NonNull FloatMatrix other) throws DimensionException {
		if(this.matrix.length == other.matrix.length
		&& this.matrix[0].length == other.matrix[0].length) {
			float[][] re =  new float[matrix.length][matrix[0].length];
			for(int x=0; x<matrix.length; x++) {
				for(int y=0; y<matrix[0].length; y++) {
						re[x][y] = matrix[x][y] + other.matrix[x][y];
					}
				}
			return new FloatMatrix(re);
		} else {
			throw new DimensionException("Cannot sum two matrixes with different dimensions.");
		}
	}

	public FloatMatrix subtract(FloatMatrix other) throws DimensionException {
		if(this.matrix.length == other.matrix.length
		&& this.matrix[0].length == other.matrix[0].length) {
			float[][] re =  new float[matrix.length][matrix[0].length];
			for(int x=0; x<matrix.length; x++) {
				for(int y=0; y<matrix[0].length; y++) {
					re[x][y] = matrix[x][y] - other.matrix[x][y];
				}
			}
			return new FloatMatrix(re);
		} else {
			throw new DimensionException("Cannot sum two matrixes with different dimensions.");
		}
	}

	public FloatMatrix multiply(float f) {
		float[][] re =  new float[matrix.length][matrix[0].length];
		for(int x=0; x<matrix.length; x++) {
			for(int y=0; y<matrix[x].length; y++) {
				re[x][y] = matrix[x][y] * f;
			}
		}
		return new FloatMatrix(re);
	}
	
	/*
	 * Divides this matrix by the given float.
	 */
	public FloatMatrix divide(float f) {
		float[][] re =  new float[matrix.length][matrix[0].length];
		for(int x=0; x<matrix.length; x++) {
			for(int y=0; y<matrix[x].length; y++) {
				re[x][y] = matrix[x][y] / f;
			}
		}
		return new FloatMatrix(re);
	}

	public FloatMatrix multiply(FloatMatrix other) throws DimensionException {
		if(this.matrix[0].length == other.matrix.length) {
			float[][] rowMatrix = other.matrix;
			float[][] columnMatrix = this.matrix;

			float[][] newMatrix = new float[columnMatrix.length][];

			for (int x = 0; x < newMatrix.length; x++) {
				newMatrix[x] = new float[rowMatrix[0].length];

				for (int y = 0; y < newMatrix[x].length; y++) {
					float total = 0;

					for (int i = 0; i < rowMatrix.length; i++) {
						total += columnMatrix[x][i] * rowMatrix[i][y];
					}
					newMatrix[x][y] = total;
				}
			}
			return new FloatMatrix(newMatrix);
		} else {
			throw new DimensionException("Cannot multiply two matrixes that doesn't have complimentory dimensions.");
		}
	}

	public float[] asRowMajorVector() {
		float[] re = new float[matrix.length*matrix[0].length];
		for(int x=0; x<matrix.length; x++) {
			for(int y=0; y<matrix[x].length; y++) {
				re[x*matrix.length+y] = matrix[x][y];
			}
		}
		return re;
	}

	public float[] asColumnMajorVector() {
		float[] re = new float[matrix.length*matrix[0].length];
		for(int x=0; x<matrix.length; x++) {
			for(int y=0; y<matrix[x].length; y++) {
				re[y*matrix.length+x] = matrix[y][x];
			}
		}
		return re;
	}

	@NonNull
	@Override
	public String toString() {
		String re = "";
		for(int row=0; row<matrix.length; row++) {
			re += "[";
			for(int column=0; column<matrix[row].length; column++) {
				re += matrix[row][column] + " ";
			}
			re += "]\n";
		}
		return re;
	}
}
