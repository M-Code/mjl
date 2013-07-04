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
import android.util.Log;

public class WavefrontObj {
	
	public static final String TAG = "WavefrontObj";
	
	private List<Float> vertexList = new ArrayList<Float>();
	private List<Short> indexList = new ArrayList<Short>();
	private FloatBuffer vertexBuffer;
	private ShortBuffer indexBuffer;
	
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
						if(e.length >= 4) {
							Log.e(TAG, "ERROR: WavefrontObj currently only support triangle faces! This face has " + e.length + " vertices!");
						}
						for(int i = 1; i < e.length; i++) {
							indexList.add((short)(Short.parseShort(e[i]) - 1)); // wavefront format start counting from 1.
							Log.w(TAG, e[i]);
						}
					}
				}
				line = br.readLine();
			}
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		} 
    	
		vertexBuffer = ByteBuffer.allocateDirect(vertexList.size()).order(ByteOrder.nativeOrder()).asFloatBuffer();
		//vertexBuffer.put(null);
	}
	
	private void loadMaterial(InputStream mtl) {
		
	}
	
	public void drawFrame(int program, String positionName) {
        int positionHandle = GLES20.glGetAttribLocation(program, positionName);
        GLES20.glEnableVertexAttribArray(positionHandle);
	}
}
