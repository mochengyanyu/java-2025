package com.wk.local.message.rpc;

import com.wk.local.message.annotation.SecureInvoke;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.ServletSecurity;
import javax.servlet.http.PushBuilder;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class TestRpc {

    public final static AtomicInteger index = new AtomicInteger(0);

    @SecureInvoke
    public void getRpcValue(){
        try {
            Thread.sleep(3*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (index.get()<3){
            throw new RuntimeException("error");
        }
        log.info("getRpcValue");
    }

}
