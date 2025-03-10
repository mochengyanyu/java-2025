package com.zz.wk.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.concurrent.locks.LockSupport;

public class Handler implements Runnable{

    private SocketChannel socketChannel;

    public Handler(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        //处理业务
        ByteBuffer byteBuf = ByteBuffer.allocate(1024);
        try {
            System.out.println(Thread.currentThread().getName()+":业务处理");
            int read = socketChannel.read(byteBuf);
            Charset charset = Charset.forName("UTF-8");
            if (read>0){
                //切换模式
                byteBuf.flip();
                byte[] bytes = new byte[byteBuf.limit()];
                //业务可以用多线程处理
                byteBuf.get(bytes);
                System.out.println("收到客户端的数据:"+new String(bytes,0,bytes.length,charset).trim());
            }else {
                socketChannel.close();
            }
            //模拟阻塞状态下会不会影响其他客户端
            LockSupport.parkNanos(10*1000*1000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
