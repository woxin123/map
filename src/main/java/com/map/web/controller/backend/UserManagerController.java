package com.map.web.controller.backend;

import com.github.pagehelper.PageInfo;
import com.map.common.ServerResponse;
import com.map.pojo.User;
import com.map.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mengchen
 * @time 19-3-16 下午3:24
 */
@RestController
@RequestMapping("/manager/user")
public class UserManagerController {

    @Autowired
    private UserService userService;

    /**
     * 获取所有用户信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping
    public ServerResponse<PageInfo<User>> getUsers(@RequestParam(required = false, defaultValue = "1") int pageNum,
                                                   @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return userService.getAllUsers(pageNum, pageSize);
    }
}
