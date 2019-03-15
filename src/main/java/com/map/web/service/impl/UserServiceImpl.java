package com.map.web.service.impl;

import com.map.common.Const;
import com.map.common.ServerResponse;
import com.map.dao.UserMapper;
import com.map.dto.UserInputDTO;
import com.map.pojo.User;
import com.map.utils.TokenUtil;
import com.map.utils.crypto.MD5Util;
import com.map.web.model.ResultBuilder;
import com.map.web.model.ResultModel;
import com.map.web.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    Logger logger = Logger.getLogger(UserServiceImpl.class);


    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public ServerResponse register(UserInputDTO user) {
        // 账号没被注册过
        if (userMapper.selectByAccount(user.getAccount()) == null) {
            logger.info(user);
            User userInDb = assembleUser(user);
            if (userInDb.getSex() == null) {
                userInDb.setSex(Const.SEX.SECRET);
            }
            // 设置用户未被锁定
            userInDb.setIslock(Const.UserStaus.UNLOCK);
            // 进行密码加密
            userInDb.setPassword(MD5Util.MD5EncodeUtf8(userInDb.getPassword()));
            int rawCount = userMapper.insert(userInDb);
            if (rawCount < 1) {
                return ServerResponse.createByErrorMessage("用户注册失败");
            }

            return ServerResponse.createBySuccessMessage("注册成功");
        } else {
            return ServerResponse.createByErrorMessage("该账号已被注册");
        }
    }

    private User assembleUser(UserInputDTO userInputDTO) {
        User user = new User();
        user.setAccount(userInputDTO.getAccount());
        user.setUsername(userInputDTO.getUsername());
        user.setPassword(userInputDTO.getPassword());
        user.setImage(userInputDTO.getImage());
        return user;
    }

    @Override
    public ServerResponse login(String account, String password) {
        User user = userMapper.selectByAccount(account);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户名或密码错误");
        }
        // 判断用户是否被锁定
        if (user.getIslock() == Const.UserStaus.LOCK) {
            return ServerResponse.createByErrorMessage("用户被锁定");
        }
        password = MD5Util.MD5EncodeUtf8(password);
        if (StringUtils.equals(user.getPassword(), password)) {
            String token = null;
            try {
                token = TokenUtil.createToken(user);
            } catch (Exception e) {
                logger.info("创建token错误");
                e.printStackTrace();
                return ServerResponse.createByErrorMessage("token错误");
            }
            return ServerResponse.createBySuccess(token);
        } else {
            return ServerResponse.createByErrorMessage("用户名或密码错误");
        }
    }

    public ResultModel getUserMessageById(int userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            logger.info("用户不存在");
            return ResultBuilder.getFailure(1, "用户不存在");
        } else {
            return ResultBuilder.getSuccess(user);
        }
    }

    public ResultModel saveIcon(int userId, String path) {
        User user = userMapper.selectByPrimaryKey(userId);
        user.setImage(path);
        User userToUpdate = new User();
        userToUpdate.setImage(path);
        return ResultBuilder.getSuccess(user);
    }

    public User findUserById(int userId) {
        return userMapper.selectByPrimaryKey(userId);
    }


//
//    @Override
//    public PageInfo<User> getUsers(int pageNo, int pageSize) {
//        PageHelper.startPage(pageNo, pageSize);
//        List<User> users = userMapper.getUsers();
//        PageInfo<User> pageInfo = new PageInfo<>(users);
//        return pageInfo;
//    }
//
//    @Override
//    public ResultModel updateUser(User user) {
//        User isExistUser = userMapper.findUserById(user.getId());
//        if (isExistUser == null) {
//            return ResultBuilder.getFailure(1, "用户不存在");
//        }
//        User isUpdateUser = userMapper.updateUser(user);
//        if (isUpdateUser == null) {
//            return ResultBuilder.getFailure(2, "用户信息更新失败");
//        }
//        return ResultBuilder.getSuccess("用户信息更新成功");
//    }
//
//    @Override
//    public ResultModel deleteUser(int userId) {
//        User isExistUser = userMapper.findUserById(userId);
//        if (isExistUser == null) {
//            return ResultBuilder.getFailure(1, "用户不存在");
//        }
//        boolean isDelete = userMapper.deleteUser(userId);
//        if (isDelete == false) {
//            return ResultBuilder.getFailure(2, "用户删除失败");
//        }
//
//        return ResultBuilder.getSuccess("用户删除成功");
//    }
//
//    @Override
//    public ResultModel lockedUser(int userId) {
//        if (userMapper.findUserById(userId) == null) {
//            return ResultBuilder.getFailure(1, "用户不存在");
//        }
//        boolean isLock = userMapper.lockUser(userId);
//        if (isLock) {
//            return ResultBuilder.getSuccess("锁定用户成功");
//        }
//        return ResultBuilder.getFailure(2, "锁定用户失败");
//    }
//
//    @Override
//    public ResultModel unLockUser(int userId) {
//        if (userMapper.findUserById(userId) == null) {
//            return ResultBuilder.getFailure(1, "用户不存在");
//        }
//        boolean isUnLock = userMapper.unLockUser(userId);
//        if (isUnLock) {
//            return ResultBuilder.getSuccess("用户解锁成功");
//        }
//        return ResultBuilder.getFailure(2, "用户解锁失败");
//    }

}
