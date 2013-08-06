package util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mcode.mjl.util.BitFlags;

public class BitFlagsTest {

	@Test
	public void test() {
		BitFlags bf = new BitFlags(1);
		assertFalse(bf.get(3));
		bf.set(3);
		assertTrue(bf.get(3));
		bf.unset(3);
		assertFalse(bf.get(3));
		
		assertFalse(bf.get(0));
		assertFalse(bf.get(7));
		bf.set(0);
		bf.set(7);
		assertTrue(bf.get(0));
		assertTrue(bf.get(7));
	}
	
	@Test
	public void testFalse() {
		BitFlags bf = new BitFlags(1);
		BitFlags bf2 = new BitFlags(1);
		assertEquals(bf, bf2);
		bf.set(4);
		assertFalse(bf.equals(bf2));
	}

}
