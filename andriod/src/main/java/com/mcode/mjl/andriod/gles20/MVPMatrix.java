package com.mcode.mjl.andriod.gles20;

import android.opengl.Matrix;

public class MVPMatrix {
    private float[] pMatrix = new float[16];
    private float[] mMatrix = new float[16];
    private float[] vMatrix = new float[16];
    private float[] mvpMatrix = new float[16];
    public MVPMatrix() {
    	Matrix.setIdentityM(mMatrix, 0);
    	Matrix.setIdentityM(vMatrix, 0);
    	Matrix.setIdentityM(pMatrix, 0);
    	Matrix.setIdentityM(mvpMatrix, 0);
    }
    public float[] getMMatrix() {
    	return mMatrix;
    }
    public float[] getVMatrix() {
    	return vMatrix;
    }
    public float[] getPMatrix() {
    	return pMatrix;
    }
    public float[] getMVPMatrix() {
		Matrix.multiplyMM(mvpMatrix, 0, vMatrix, 0, mMatrix, 0);
		Matrix.multiplyMM(mvpMatrix, 0, pMatrix, 0, mvpMatrix, 0);
		return mvpMatrix;
    }
}
