package com.mcode.mjl.util;

import java.util.List;

public class DataConverter {
	public static float[] asFloatArray(List<Float> floatList) {
    	float[] floatArray = new float[floatList.size()];
    	for(int i = 0; i < floatList.size(); i++) {
    		floatArray[i] = floatList.get(i);
    	}
    	return floatArray;
	}
	public static short[] asShortArray(List<Short> shortList) {
    	short[] floatArray = new short[shortList.size()];
    	for(int i = 0; i < shortList.size(); i++) {
    		floatArray[i] = shortList.get(i);
    	}
    	return floatArray;
	}
}
