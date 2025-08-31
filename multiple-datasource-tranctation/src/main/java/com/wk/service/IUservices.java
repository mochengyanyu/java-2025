package com.wk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wk.entity.UserEntity;

import java.util.List;

public interface IUservices extends IService<UserEntity> {
    public List<UserEntity> getAll();
    public List<UserEntity> getAll2();
}
