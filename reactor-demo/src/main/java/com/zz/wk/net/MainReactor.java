package com.zz.wk.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class MainReactor implements Runnable{

    private final ServerSocketChannel serverSocketChannel;

    private final Selector selector;

    public MainReactor(int port){
        try {
            this.serverSocketChannel = ServerSocketChannel.open();
            this.selector = Selector.open();
            this.serverSocketChannel.configureBlocking(false);
            this.serverSocketChannel.bind(new InetSocketAddress(port));
            //注册一个连接事件
            this.serverSocketChannel.register(
                    this.selector,
                    SelectionKey.OP_ACCEPT,
                    new Accept(serverSocketChannel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void run() {
        //死循环处理
        while (!Thread.interrupted()){
            //阻塞
            try {
                System.out.println("main reactor:===>"+Thread.currentThread().getName()+":阻塞");
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if (key!= null && key.isAcceptable()){
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
        Runnable attachment = (Runnable)key.attachment();
        if (attachment != null){
            attachment.run();
        }
    }
}
