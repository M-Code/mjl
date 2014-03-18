/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mcode.ninja;

import com.mcode.ninja.listeners.MessageListener;

/**
 *
 * @author mliu
 */
public interface Ninja {
    void sendMessage(byte[] topic, byte[] message);
    void listenMessages(byte[] topic, MessageListener listener);
    /**
     * Kills this ninja!
     */
    void die();
}
