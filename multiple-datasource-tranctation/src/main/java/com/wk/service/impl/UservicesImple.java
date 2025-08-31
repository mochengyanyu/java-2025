package com.wk.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wk.entity.UserEntity;
import com.wk.mapper.primary.UserMapper;
import com.wk.service.IUservices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UservicesImple extends ServiceImpl<UserMapper, UserEntity> implements IUservices {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserEntity> getAll() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        List<UserEntity> users = userMapper.selectList(queryWrapper);
        return users;
    }

    @Override
    @Transactional(value = "primaryTransactionManager")
    public List<UserEntity> getAll2() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        List<UserEntity> users = userMapper.selectList(queryWrapper);
        return users;
    }
}
