package com.mcode.mjl.util;

import java.util.List;

public class DataConverter {
	public static float[] asArray(List<Float> floatList) {
    	float[] floatArray = new float[floatList.size()];
    	for(int i = 0; i < floatList.size(); i++) {
    		floatArray[i] = floatList.get(i);
    	}
    	return floatArray;
	}
}
