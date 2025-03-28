package com.wk.local.message.control;

import com.wk.local.message.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserControl {

    @Autowired
    private IUserService userService;

    @RequestMapping("/hello")
    public String hello(){
        userService.saveUser("zuozhu");
        return "hello";
    }

}
