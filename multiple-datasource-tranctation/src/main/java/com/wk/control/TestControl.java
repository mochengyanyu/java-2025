package com.wk.control;

import com.wk.entity.TestEntity;
import com.wk.entity.UserEntity;
import com.wk.service.ITestServices;
import com.wk.service.ITestXATrancetation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestControl {

    @Resource
    private ITestServices testServices;

    @Autowired
    private ITestXATrancetation testXATrancetation;

    @GetMapping("/all")
    public List<TestEntity> getAll(){
        List<TestEntity> all = testServices.getAll();
        return all;
    }

    @GetMapping("/addXa")
    public String updateAndAdd(){
        UserEntity user = new UserEntity("佐良娜147","147","4234","5345","6456","7657","53");
        TestEntity testEntity = new TestEntity();
        testEntity.setId(2l);
        testEntity.setUserName("佐良娜98745");
        testEntity.setPwd("98745");
        testXATrancetation.updateAndAdd(user,testEntity);
        return "sucess";
    }

}
