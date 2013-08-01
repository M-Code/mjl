package network.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

public class Subscriber {
	private MulticastSocket socket;
	public void subscribe(final String group, final int port, final MessageListener listener) throws IOException {
		socket = new MulticastSocket(port);
		socket.joinGroup(InetAddress.getByName(group));
		
		Thread messageListeningThread = new Thread(new Runnable(){

			public void run() {
				byte[] buffer = new byte[1024];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				while(true) {
					try {
						socket.receive(packet);
						byte[] data = Arrays.copyOfRange(packet.getData(), packet.getOffset(), packet.getOffset() + packet.getLength());
						packet.getOffset();
						packet.getLength();
						listener.messageReceived(group, port, data);
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
			
		}, "MessageListeningThread:" + group + ":" + port);
		messageListeningThread.start();
		

	}
	
	@Override
	public void finalize() {
		socket.close();
	}
}
