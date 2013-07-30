package network.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Publisher {
	private MulticastSocket socket;
	private InetAddress multicastGroup;
	private int port;
	public Publisher(String group, int port, int ttl) throws IOException {
		this.port = port;
		multicastGroup = InetAddress.getByName(group);
		socket = new MulticastSocket();
		socket.setTimeToLive(ttl);
	}
	public void publish(byte[] data) throws IOException {
		socket.send(new DatagramPacket(data, data.length, multicastGroup, port));
	}
}
