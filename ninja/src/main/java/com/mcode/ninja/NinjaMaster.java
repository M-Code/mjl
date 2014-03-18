/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mcode.ninja;

import com.mcode.ninja.receivers.ReceivingNinja;
import com.mcode.ninja.senders.SendingNinja;
import org.zeromq.ZMQ;

/**
 *
 * @author mliu
 */
public class NinjaMaster {
    private final ZMQ.Context ctx;
    public NinjaMaster() {
        ctx = ZMQ.context(1);
    }
    public Ninja createSendingNinja(String url) {
        return new SendingNinja(ctx, url);
    }
    public Ninja createReceivingNinja(String url) {
        return new ReceivingNinja(ctx, url);
    }
    public Ninja createOmniNinja(String url) {
        return null;
    }
    
    public void done() {
        ctx.term();
    }
}
