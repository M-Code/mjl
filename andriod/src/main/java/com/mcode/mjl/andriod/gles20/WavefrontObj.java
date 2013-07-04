package com.mcode.mjl.andriod.gles20;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import com.mcode.mjl.util.DataConverter;

public class WavefrontObj {
	
	public static final String TAG = "WavefrontObj";
	
	private List<Float> vertexList = new ArrayList<Float>();
	private List<Short> indexList = new ArrayList<Short>();
	private FloatBuffer vertexBuffer;
	private ShortBuffer indexBuffer;
	
	private static final int FLOAT_SIZE_BYTES = 4;
	private static final int SHORT_SIZE_BYTES = 2;
	private static final int TRIANGLE_VERTICES_DATA_STRIDE_BYTES = 3 * FLOAT_SIZE_BYTES;
    private float[] mMVPMatrix = new float[16];
    private float[] mProjMatrix = new float[16];
    private float[] mMMatrix = new float[16];
    private float[] mVMatrix = new float[16];
	
	public WavefrontObj(InputStream obj, InputStream mtl) {
		loadObject(obj);
		loadMaterial(mtl);
	}
	
	private void loadObject(InputStream obj) {
		Log.i(TAG, "Loading wavefront object...");
    	BufferedReader br = new BufferedReader(new InputStreamReader(obj));
    	try {
			String line = br.readLine();
			while(line != null) {
				String[] e = line.split(" ");
				if(e.length > 1) {
					if(e[0].equals("v")) {
						for(int i = 1; i < e.length; i++) {
							vertexList.add(Float.parseFloat(e[i]));
						}
					} else if (e[0].equals("f")) {
						if(e.length >= 5) {
							Log.e(TAG, "ERROR: WavefrontObj currently only support triangle faces! This face has " + (e.length - 1) + " vertices!");
						}
						for(int i = 1; i < e.length; i++) {
							indexList.add((short)(Short.parseShort(e[i]) - 1)); // wavefront format start counting from 1.
						}
					}
				}
				line = br.readLine();
			}
			br.close();
			
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		} 
    	
		vertexBuffer = ByteBuffer.allocateDirect(vertexList.size() * FLOAT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertexBuffer.put(DataConverter.asFloatArray(vertexList)).position(0); 
	
		indexBuffer = ByteBuffer.allocate(indexList.size() * SHORT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asShortBuffer();
		indexBuffer.put(DataConverter.asShortArray(indexList)).position(0);
	}
	
	private void loadMaterial(InputStream mtl) {
		
	}
	
	public void drawFrame(int program, String positionName, String mvpMatrixName) {
		int positionHandle = GLES20.glGetAttribLocation(program, positionName);
		GLES20.glEnableVertexAttribArray(positionHandle);

		GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, TRIANGLE_VERTICES_DATA_STRIDE_BYTES, vertexBuffer);


		int mvpPMatrixHandle = GLES20.glGetUniformLocation(program, mvpMatrixName);

		float mAngleX = 0.0f;
		float mAngleY = 0.0f;
		Matrix.setRotateM(mMMatrix, 0, mAngleX, 0f, 1f, 0f);
		Matrix.rotateM(mMMatrix, 0, mAngleY, 1f, 0f, 0f);
		Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mMMatrix, 0);
		Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);

		GLES20.glUniformMatrix4fv(mvpPMatrixHandle, 1, false, mMVPMatrix, 0);

		GLES20.glDrawElements(GLES20.GL_TRIANGLES, indexList.size(), GLES20.GL_UNSIGNED_SHORT, indexBuffer); 
	}
}
