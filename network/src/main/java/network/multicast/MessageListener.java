package network.multicast;

public interface MessageListener {
	//boolean t(Topic t);
	void messageReceived(byte[] data);
}
