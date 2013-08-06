package network.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetUtil {
	public static final byte[] ZERO_BYTE_ARRAY = new byte[] { 0 };
	
	public static int getUnsignedByte(byte b) {
		if(b >= 0) {
			return b;
		} else {
			return b + 256;
		}
	}
	
	public static String bytesToIP(byte[] byteArray) {
		if(byteArray.length != 4) {
			throw new IllegalArgumentException("Byte array must be of size 4");
		}
		return "" + getUnsignedByte(byteArray[0]) + "." + getUnsignedByte(byteArray[1]) + "." + getUnsignedByte(byteArray[2]) + "." + getUnsignedByte(byteArray[3]);
	}
	
	public static InetAddress getNonLoopbackLocalHost() throws SocketException {
		Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces();
		while(nics.hasMoreElements()) {
			Enumeration<InetAddress> addresses = nics.nextElement().getInetAddresses();
			while(addresses.hasMoreElements()) {
				InetAddress address = addresses.nextElement();
				if(!address.isLoopbackAddress() && address.isSiteLocalAddress()) {
					return address;
				}
			}
		}
		return null;
	}
}
