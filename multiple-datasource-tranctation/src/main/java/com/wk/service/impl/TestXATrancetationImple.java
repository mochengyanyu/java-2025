package com.wk.service.impl;

import com.wk.entity.TestEntity;
import com.wk.entity.UserEntity;
import com.wk.mapper.primary.UserMapper;
import com.wk.mapper.second.TestMapper;
import com.wk.service.ITestXATrancetation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestXATrancetationImple implements ITestXATrancetation {

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public void updateAndAdd(UserEntity user, TestEntity testEntity) {
        userMapper.insert(user);
        testMapper.updateById(testEntity);
    }
}
