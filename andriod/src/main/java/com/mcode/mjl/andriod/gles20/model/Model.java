package com.mcode.mjl.andriod.gles20.model;

import com.mcode.mjl.andriod.gles20.exception.MVPMatrixNotSetException;
import com.mcode.mjl.andriod.gles20.matrix.MVPMatrix;

public interface Model {
	void setMVPMatrix(MVPMatrix matrix);
	void drawFrame() throws MVPMatrixNotSetException;
}
