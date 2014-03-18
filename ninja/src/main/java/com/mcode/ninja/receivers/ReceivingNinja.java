package com.mcode.ninja.receivers;

import com.mcode.ninja.Ninja;
import com.mcode.ninja.listeners.MessageListener;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;
import org.zeromq.ZMsg;

public class ReceivingNinja implements Ninja {

    private final Socket subsock;
    public ReceivingNinja(ZMQ.Context ctx, String url) {
        subsock = ctx.socket(ZMQ.SUB);
        subsock.connect(url);
    }
    
    @Override
    public void die() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void sendMessage(byte[] topic, byte[] message) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void listenMessages(byte[] topic, final MessageListener listener) {
        subsock.subscribe(topic);
        new Thread() {
            @Override
            public void run() {
                while(true) {
                    ZMsg msg = ZMsg.recvMsg(subsock);
                    listener.messageReceived(msg.getFirst().getData());
                }
            }
        }.start();
    }
    
}
