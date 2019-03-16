package com.map.web.service;

import com.github.pagehelper.PageInfo;
import com.map.common.ServerResponse;
import com.map.dto.UserInputDTO;
import com.map.pojo.User;
import com.map.vo.UserOutputVO;
import com.map.web.model.ResultModel;


public interface UserService {

    ServerResponse register(UserInputDTO user);

    ServerResponse login(String account, String password) throws Exception;

    ServerResponse<UserOutputVO> getUserMessageById(int userId);

    ResultModel saveIcon(int userId, String path);

    User findUserById(int userId);

    ServerResponse<PageInfo<User>> getAllUsers(int pageNum, int pageSize);
//
//    ResultModel updateUser(User user);
//
//    ResultModel deleteUser(int userId);

    ServerResponse lockedUser(int userId);

    ServerResponse unLockUser(int userId);
}
