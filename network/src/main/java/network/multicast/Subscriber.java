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
	
	private MulticastSocket socket;
	public void subscribe(final String group, final int port, final MessageListener listener) throws IOException {
		sub.subscribe(NetUtil.ZERO_BYTE_ARRAY);
		socket = new MulticastSocket(port);
		socket.setInterface(NetUtil.getNonLoopbackLocalHost());
		socket.joinGroup(InetAddress.getByName(group));
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
		
		Thread advertisementListeningThread = new Thread(new Runnable(){
			public void run() {
				byte[] buffer = new byte[1024];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				Set<String> subscribedIPs = new HashSet<String>();
				while(true) {
					try {
						socket.receive(packet);
						if(packet.getLength() == 4) {
							byte[] data = Arrays.copyOfRange(packet.getData(), packet.getOffset(), packet.getOffset() + packet.getLength());
							int offset = packet.getOffset();
							
							String ip = "" + NetUtil.getUnsignedByte(data[offset]) + "." + NetUtil.getUnsignedByte(data[offset + 1]) + "." + NetUtil.getUnsignedByte(data[offset + 2]) + "." + NetUtil.getUnsignedByte(data[offset + 3]);
							if(!subscribedIPs.contains(ip)) {
								sub.connect("tcp://" + ip + ":" + port);
								subscribedIPs.add(ip);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}, "AdvertisementListeningThread:" + group + ":" + port);
		advertisementListeningThread.start();
	}
	
	@Override
	public void finalize() {
		socket.close();
	}
}
