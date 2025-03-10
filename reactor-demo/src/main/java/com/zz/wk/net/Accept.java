package com.zz.wk.net;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Accept implements Runnable{

    private final int SUB_REACTOR_COUNT = 16;

    private ServerSocketChannel serverSocketChannel;

    private SubReactor[] subReactors;

    public final AtomicInteger index = new AtomicInteger(0);

    ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            SUB_REACTOR_COUNT,
            SUB_REACTOR_COUNT,
            60l,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue(4)
    );

    public Accept(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
        this.subReactors = new SubReactor[SUB_REACTOR_COUNT];
        for (int i = 0;i<SUB_REACTOR_COUNT;i++){
            try {
                this.subReactors[i] = new SubReactor(Selector.open());
                threadPool.execute(this.subReactors[i]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int nextIndex(){
        int andIncrement = index.getAndIncrement();
        if (andIncrement > SUB_REACTOR_COUNT){
            index.set(0);
            return 0;
        }
        int nextIndex = andIncrement % (subReactors.length);
        return nextIndex;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName()+":处理连接");
            SocketChannel clinetChannel = this.serverSocketChannel.accept();
            clinetChannel.configureBlocking(false);
            SubReactor subReactor = this.subReactors[nextIndex()];
            if (subReactor != null){
                Selector selector = subReactor.getSelector();
                //注册OP_READ事件
                clinetChannel.register(selector, SelectionKey.OP_READ,new Handler(clinetChannel));
                //唤醒
                selector.wakeup();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
