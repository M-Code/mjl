package com.mcode.mjl.andriod.gles20.matrix;

import android.opengl.Matrix;

public class MatrixTransformer {
	
	public static TransformResult translate(float[] matrix, float x, float y, float z) {
		Matrix.translateM(matrix, 0, x, y, z);
		return null;
	}
	
	public static TransformResult rotate(float[] matrix, float x, float y, float z) {
		Matrix.rotateM(matrix, 0, 0, x, y, z);
		return null;
	}
	
	public static TransformResult scale(float[] matrix, float x, float y, float z) {
		return null;
	}
	
	private class TransformResult {
		public TransformResult thenTranslate(float x, float y, float z) {
			return null;
		}
		public TransformResult andTranslate(float x, float y, float z) {
			return null;
		}
		
		public TransformResult thenRotate(float x, float y, float z) {
			return null;
		}
		public TransformResult andRotate(float x, float y, float z) {
			return null;
		}
		public TransformResult thenScale(float x, float y, float z) {
			return null;
		}
		public TransformResult andScale(float x, float y, float z) {
			return null;
		}
		public void done() {
			
		}
	}
}
