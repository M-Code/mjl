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
    	short[] shortArray = new short[shortList.size()];
    	for(int i = 0; i < shortList.size(); i++) {
    		shortArray[i] = shortList.get(i);
    	}
    	return shortArray;
	}
}
