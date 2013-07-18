package com.mcode.mjl.andriod.gles20.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.mcode.mjl.andriod.gles20.matrix.MVPMatrix;
import com.mcode.mjl.andriod.gles20.shaders.ShaderProgram;
import com.mcode.mjl.util.DataConverter;

public class WavefrontObj implements Model {
	
	public static final String TAG = "WavefrontObj";
	
	private List<Float> vertexList = new ArrayList<Float>();
	private List<Short> indexList = new ArrayList<Short>();
	private FloatBuffer vertexBuffer;
	private ShortBuffer indexBuffer;
	
	private static final int FLOAT_SIZE_BYTES = 4;
	private static final int SHORT_SIZE_BYTES = 2;
	private static final int TRIANGLE_VERTICES_DATA_STRIDE_BYTES = 3 * FLOAT_SIZE_BYTES;
	
	private MVPMatrix matrix;
	private ShaderProgram shaderProgram;
	private Context ctx;
	
	public WavefrontObj(ShaderProgram program, MVPMatrix m, InputStream obj, Context ctx) {
		if(ctx == null) {
			throw new IllegalArgumentException("Context cannot be null");
		}
		this.ctx = ctx;
		this.shaderProgram = program;
		
		matrix = m;
		loadObject(obj);
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
							String eSub[] = e[i].split("/");
							indexList.add((short)(Short.parseShort(eSub[0]) - 1)); // wavefront format start counting from 1.
						}
					} else if (e[0].equals("mtllib")) {
						loadMaterial(ctx.getAssets().open(e[1]));
					} else if(e[0].equals("usemtl")) {
						Log.w(TAG, "No support for usemtl yet");
					} else if(e[0].equals("o")) {
						Log.w(TAG, "No support for o yet");
					} else if(e[0].equals("s")) {
						Log.w(TAG, "No support for s yet");
					} else if (e[0].startsWith("#")) { 
						// Comment. Ignore.
					} else {
						Log.e(TAG, "Cannot parse [" + e[0] +"]" );
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

		indexBuffer = ByteBuffer.allocateDirect(indexList.size() * SHORT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asShortBuffer();
		indexBuffer.put(DataConverter.asShortArray(indexList)).position(0);
	}
	
	private void loadMaterial(InputStream mtl) {
		Log.i(TAG, "Loading material...");
	}
	
	public void setMVPMatrix(MVPMatrix matrix) {
		this.matrix = matrix;
	}
	
	public void drawFrame() {
		int program = shaderProgram.getProgram();
		String positionAttribute = shaderProgram.getPositionAttribute();
		String mvpMatrixUniform = shaderProgram.getMVPMatrixUniform();
		GLES20.glUseProgram(program);
		
		int positionHandle = GLES20.glGetAttribLocation(program, positionAttribute);
		GLES20.glEnableVertexAttribArray(positionHandle);
		GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, TRIANGLE_VERTICES_DATA_STRIDE_BYTES, vertexBuffer);
		
		int mvpPMatrixHandle = GLES20.glGetUniformLocation(program, mvpMatrixUniform);
		GLES20.glUniformMatrix4fv(mvpPMatrixHandle, 1, false, matrix.getMVPMatrix(), 0);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, indexList.size(), GLES20.GL_UNSIGNED_SHORT, indexBuffer); 
	}
}
