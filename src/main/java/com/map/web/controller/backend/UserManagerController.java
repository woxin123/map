package com.map.web.controller.backend;

import com.github.pagehelper.PageInfo;
import com.map.common.ServerResponse;
import com.map.pojo.User;
import com.map.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping
    public ServerResponse<PageInfo<User>> getUsers(@RequestParam(required = false, defaultValue = "1") int pageNum,
                                                   @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return userService.getAllUsers(pageNum, pageSize);
    }

    @RequestMapping(value = "/{userId:\\d+}/lock")
    public ServerResponse lockUser(@PathVariable int userId) {
        return userService.lockedUser(userId);
    }

    @RequestMapping(value = "/{userId:\\d+}/unlock")
    public ServerResponse unLockUser(@PathVariable int userId) {
        return userService.unLockUser(userId);
    }
}
