package network.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Subscriber {
	public void subscribe(String group, int port, MessageListener listener) throws IOException {
		MulticastSocket socket = new MulticastSocket(port);
		socket.joinGroup(InetAddress.getByName(group));
		
		byte[] buffer = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		
		while(true) {
			socket.receive(packet);
			System.out.println("gotit");
		}
		
	}
}
