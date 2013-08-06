package com.mcode.mjl.util;

import java.util.Arrays;

public final class BitFlags {
	private final int BIT_COUNT;
	private final byte[] data;
	
	public BitFlags(byte[] byteArray) {
		BIT_COUNT = byteArray.length * 8;
		data = byteArray;
	}
	
	public BitFlags(int numOfBytes) {
		BIT_COUNT = numOfBytes * 8;
		data = new byte[BIT_COUNT];
	}
	
	public int getBitCount() {
		return BIT_COUNT;
	}
	
	public boolean get(int index) {
		if(index < 0 || index >= BIT_COUNT) {
			throw new IndexOutOfBoundsException();
		}
		int byteNumber = index / 8;
		int bitNumber = index % 8;
		
		byte b = (byte)(Math.pow(2, bitNumber));
		return (data[byteNumber] & b) == b;
	}
	
	public void set(int index, boolean flag) {
		if(index < 0 || index >= BIT_COUNT) {
			throw new IndexOutOfBoundsException();
		}
		
		int byteNumber = index / 8;
		int bitNumber = index % 8;
		
		if(flag == true) {
			byte b = (byte)(Math.pow(2, bitNumber));
			data[byteNumber] |= b;
		} else {
			byte b = (byte)(0xFF - (int)Math.pow(2, bitNumber));
			data[byteNumber] &= b;
		}
	}
	
	public void set(int index) {
		set(index, true);
	}
	
	public void unset(int index) {
		set(index, false);
	}
	
	public byte[] getBytes() {
		return data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BitFlags)) {
			return false;
		}
		BitFlags other = (BitFlags) obj;
		if (!Arrays.equals(data, other.data)) {
			return false;
		}
		return true;
	}
}
