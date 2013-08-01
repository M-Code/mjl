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
