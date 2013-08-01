package network.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.jeromq.ZMQ;

import network.util.NetUtil;

public class Publisher {
	private static final int ADVERTISING_INTERVAL = 1000;
	
	private ZMQ.Context ctx = ZMQ.context();
	private ZMQ.Socket pub = ctx.socket(ZMQ.PUB);
	
	public Publisher(String group, final int port, int ttl) throws IOException {
		final InetAddress host = NetUtil.getNonLoopbackLocalHost();
		
		pub.bind("tcp://" + host.getHostAddress() + ":" + port);

		final InetAddress multicastGroup = InetAddress.getByName(group);
		final MulticastSocket socket = new MulticastSocket();
		socket.setInterface(host);
		socket.setTimeToLive(ttl);
		
		Thread advertisingThread = new Thread(new Runnable() {
			public void run() {
				byte[] data = host.getAddress();
				while(!Thread.currentThread().isInterrupted()) {
					try {
						socket.send(new DatagramPacket(data, data.length, multicastGroup, port));
						Thread.sleep(ADVERTISING_INTERVAL);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				socket.close();
			}
		}, "Publisher Advertising Thread");
		advertisingThread.start();
	}
	
	public void publish(byte[] data) throws IOException {
		pub.send(NetUtil.ZERO_BYTE_ARRAY, ZMQ.SNDMORE);
		pub.send(data, 0);
	}
}
