package com.wk.service;

import com.wk.entity.TestEntity;
import com.wk.entity.UserEntity;

public interface ITestXATrancetation {

    public void updateAndAdd(UserEntity user, TestEntity testEntity);

}
