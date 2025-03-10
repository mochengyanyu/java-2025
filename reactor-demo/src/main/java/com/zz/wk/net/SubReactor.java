package com.zz.wk.net;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class SubReactor implements Runnable{

    private Selector selector;

    public SubReactor(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()){
            try {
                System.out.println("sub reactor:===>"+Thread.currentThread().getName()+":阻塞");
                //阻塞等待就绪的channel
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if (key!= null){
                        //事件分发
                        dispatch(key);
                        iterator.remove();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void dispatch(SelectionKey key) {
        Runnable runnable = (Runnable) key.attachment();
        if (runnable!=null){
            runnable.run();
        }
    }

    public Selector getSelector() {
        return selector;
    }
}
