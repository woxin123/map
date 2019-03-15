package com.map.web.service;

import com.github.pagehelper.PageInfo;
import com.map.common.ServerResponse;
import com.map.dto.UserInputDTO;
import com.map.pojo.User;
import com.map.web.model.ResultModel;

import java.util.List;


public interface UserService {
    ServerResponse register(UserInputDTO user);

    ServerResponse login(String account, String password) throws Exception;

    ResultModel getUserMessageById(int userId);

    ResultModel saveIcon(int userId, String path);

    User findUserById(int userId);
//
//    User findUserByUsername(String username);
//
//    PageInfo<User> getUsers(int pageNo, int pageSize);
//
//    ResultModel updateUser(User user);
//
//    ResultModel deleteUser(int userId);
//
//    ResultModel lockedUser(int userId);
//
//    ResultModel unLockUser(int userId);
}
