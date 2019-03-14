package com.map.web.controller;

import com.map.domain.User;
import com.map.mapper.UserMapper;
import com.map.web.model.ResultBuilder;
import com.map.web.model.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    UserMapper userMapper;

    @RequestMapping("/none/hello")
    public ResultModel hello() {
        User user = userMapper.findUserByAccount("13572011907");
        System.out.println(user);
        return ResultBuilder.getSuccess(user);
    }
}
