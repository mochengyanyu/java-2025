package com.wk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wk.entity.TestEntity;
import com.wk.mapper.second.TestMapper;
import com.wk.service.ITestServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServicesImpl extends ServiceImpl<TestMapper, TestEntity> implements ITestServices {

    @Autowired
    private TestMapper testMapper;

    @Override
    public List<TestEntity> getAll() {
        List<TestEntity> tests = testMapper.selectList(new QueryWrapper<>());
        return tests;
    }
}
