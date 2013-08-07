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
	
	public Publisher() throws IOException {
		final InetAddress host = NetUtil.getNonLoopbackLocalHost();
		
		pub.bind("tcp://" + host.getHostAddress() + ":" + Config.PUBLISHING_PORT);

		final InetAddress advertisementGroup = InetAddress.getByName(Config.ADVERTISEMENT_GROUP);
		final MulticastSocket advertisementSocket = new MulticastSocket();
		advertisementSocket.setInterface(host);
		advertisementSocket.setTimeToLive(Config.ADVERTISEMENT_TTL);
		
		Thread advertisingThread = new Thread(new Runnable() {
			public void run() {
				byte[] data = host.getAddress();
				while(!Thread.currentThread().isInterrupted()) {
					try {
						advertisementSocket.send(new DatagramPacket(data, data.length, advertisementGroup, Config.ADVERTISEMENT_PORT));
						Thread.sleep(ADVERTISING_INTERVAL);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				advertisementSocket.close();
			}
		}, "Publisher Advertising Thread");
		advertisingThread.start();
	}
	
	public void publish(byte subject, byte[] data) throws IOException {
		pub.send(new byte[] { subject }, ZMQ.SNDMORE);
		pub.send(data, 0);
	}
}
