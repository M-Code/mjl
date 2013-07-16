package com.mcode.mjl.andriod.gles20.shaders;

public interface ShaderProgram {
	/**
	 * 
	 * @return program handle.
	 */
	int getProgram();
	String getPositionAttribute();
	String getMVPMatrixUniform();
}
