/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mcode.ninja.senders;

import com.mcode.ninja.Ninja;
import com.mcode.ninja.listeners.MessageListener;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

/**
 *
 * @author mliu
 */
public class SendingNinja implements Ninja {
    private final Socket pubsock;
    public SendingNinja(ZMQ.Context zmqContext, String url) {
        pubsock = zmqContext.socket(ZMQ.PUB);
        pubsock.bind(url);
    }

    @Override
    public void sendMessage(byte[] topic, byte[] message) {
        pubsock.send(topic, ZMQ.SNDMORE);
        pubsock.send(message, 0);
    }

    @Override
    public void die() {
        pubsock.close();
    }

    @Override
    public void listenMessages(byte[] topic, MessageListener listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
