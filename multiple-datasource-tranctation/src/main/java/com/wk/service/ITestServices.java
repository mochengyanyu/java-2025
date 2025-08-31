package com.wk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wk.entity.TestEntity;

import java.util.List;

public interface ITestServices extends IService<TestEntity> {
    public List<TestEntity> getAll();
}
