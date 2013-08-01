package network.multicast;

public interface MessageListener {
	void messageReceived(byte[] data);
}
