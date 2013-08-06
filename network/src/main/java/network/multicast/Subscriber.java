package network.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import network.util.NetUtil;

import org.jeromq.ZMQ;

public class Subscriber {
	private ZMQ.Context ctx = ZMQ.context();
	private ZMQ.Socket sub = ctx.socket(ZMQ.SUB);
	
	private MulticastSocket advertisementSocket;
	
	public void subscribe(final MessageListener listener) throws IOException {
		sub.subscribe(NetUtil.ZERO_BYTE_ARRAY);
		Thread messageListeningThread = new Thread(new Runnable() {
			public void run() {
				while(!Thread.currentThread().isInterrupted()) {
					byte[] subject = sub.recv();
					byte[] data = sub.recv();
					if(Arrays.equals(subject, NetUtil.ZERO_BYTE_ARRAY)) {
						listener.messageReceived(data);
					}
				}
			}
		}, "MessageListeningThread");
		messageListeningThread.start();
		
		
		advertisementSocket = new MulticastSocket(Config.ADVERTISEMENT_PORT);
		advertisementSocket.setInterface(NetUtil.getNonLoopbackLocalHost());
		advertisementSocket.joinGroup(InetAddress.getByName(Config.ADVERTISEMENT_GROUP));
		Thread advertisementListeningThread = new Thread(new Runnable(){
			public void run() {
				byte[] buffer = new byte[1024];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				Set<String> subscribedIPs = new HashSet<String>();
				while(!Thread.currentThread().isInterrupted()) {
					try {
						advertisementSocket.receive(packet);
						if(packet.getLength() == 4) {
							byte[] data = Arrays.copyOfRange(packet.getData(), packet.getOffset(), packet.getOffset() + packet.getLength());
							int offset = packet.getOffset();
							
							String ip = NetUtil.bytesToIP(Arrays.copyOfRange(data, offset, offset + 4));
							if(!subscribedIPs.contains(ip)) {
								sub.connect("tcp://" + ip + ":" + Config.PUBLISHING_PORT);
								subscribedIPs.add(ip);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}, "AdvertisementListeningThread");
		advertisementListeningThread.start();
	}
	
	@Override
	public void finalize() {
		advertisementSocket.close();
	}
}
