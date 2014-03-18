/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mcode.ninja.tools;

import com.mcode.ninja.Ninja;
import com.mcode.ninja.NinjaMaster;
import com.mcode.ninja.listeners.MessageListener;

/**
 *
 * @author mliu
 */
public class NinjaPingPong {
    public static void main(String args[]) throws Exception {
        System.out.println("NINJA PING PONG!");
        String url = args[0];
        NinjaMaster master = new NinjaMaster();
        Ninja sender = master.createSendingNinja(url);
        Ninja receiver = master.createReceivingNinja(url);
        receiver.listenMessages(new byte[]{}, new MessageListener() {
            @Override
            public void messageReceived(byte[] message) {
                System.out.println("Received Message!");
            }
        });
        
        while(true) {
            System.out.println("Sending message...");
            sender.sendMessage(new byte[]{}, new byte[]{ 10, 9, 8});
            Thread.sleep(1000);
        }
        
    }
}
