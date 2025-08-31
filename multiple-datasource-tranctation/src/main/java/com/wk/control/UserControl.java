package com.wk.control;

import com.wk.entity.UserEntity;
import com.wk.service.IUservices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserControl {
    @Autowired
    private IUservices uservices;


    @GetMapping("/all")
    public List<UserEntity> getAlluser(){
        return uservices.getAll();
    }

    @GetMapping("/all2")
    public List<UserEntity> getAll2user(){
        return uservices.getAll2();
    }
}
