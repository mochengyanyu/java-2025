package com.wk.local.message.rpc;

import com.wk.local.message.annotation.LocalMessageTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class TestRpc {

    public final static AtomicInteger index = new AtomicInteger(1);

    @LocalMessageTable
    public void getRpcValue(String name){
        try {
            Thread.sleep(3*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int andIncrement = index.getAndIncrement();
        index.getAndIncrement();
        log.info("andIncrement = {}",andIncrement);
        if (andIncrement<100){
            throw new RuntimeException("error");
        }
        log.info("getRpcValue");
    }

}
