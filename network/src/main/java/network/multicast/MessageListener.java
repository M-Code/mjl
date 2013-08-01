package network.multicast;

public interface MessageListener {
	void messageReceived(String group, int port, byte[] data);
}
