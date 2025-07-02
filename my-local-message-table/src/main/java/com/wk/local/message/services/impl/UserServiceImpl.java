package com.wk.local.message.services.impl;

import com.wk.local.message.rpc.TestRpc;
import com.wk.local.message.services.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private TestRpc testRpc;

    @Override
    @Transactional
    public String saveUser(String message) {
        testRpc.getRpcValue(message);
        log.info("保存成功");
        return "保存成功";
    }

}
