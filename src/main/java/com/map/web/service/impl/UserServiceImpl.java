package com.map.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.map.common.Const;
import com.map.common.ServerResponse;
import com.map.dao.UserMapper;
import com.map.dto.UserInputDTO;
import com.map.vo.UserOutputVO;
import com.map.pojo.User;
import com.map.utils.TokenUtil;
import com.map.utils.crypto.MD5Util;
import com.map.web.model.ResultBuilder;
import com.map.web.model.ResultModel;
import com.map.web.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public ServerResponse<UserOutputVO> getUserMessageById(int userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            logger.info("用户不存在");
            return ServerResponse.createByErrorMessage("获取用户信息出错");
        } else {
            UserOutputVO userOutputVO = assembleUserOutputVO(user);
            return ServerResponse.createBySuccess(userOutputVO);
        }
    }

    private UserOutputVO assembleUserOutputVO(User user) {
        UserOutputVO userOutputDTO = new UserOutputVO();
        userOutputDTO.setUsername(user.getUsername());
        userOutputDTO.setAccount(user.getAccount());
        userOutputDTO.setPhone(user.getPhone());
        userOutputDTO.setEmail(user.getEmail());
        if (user.getSex() == Const.SEX.WOMAN) {
            userOutputDTO.setSex(Const.SEX.WOMAN_STR);
        } else if (user.getSex() == Const.SEX.MAN) {
            userOutputDTO.setSex(Const.SEX.MAN_STR);
        } else {
            userOutputDTO.setSex(Const.SEX.SECRET_STR);
        }

        return userOutputDTO;
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



    @Override
    public ServerResponse<PageInfo<User>> getAllUsers(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.selectALl();
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return ServerResponse.createBySuccess(pageInfo);
    }
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
    @Override
    public ServerResponse lockedUser(int userId) {
        if (userMapper.selectByPrimaryKey(userId) == null) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        User user = new User();
        user.setId(userId);
        user.setIslock(Const.UserStaus.LOCK);
        int rowCount = userMapper.updateByPrimaryKeySelective(user);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("锁定用户成功");
        }
        return ServerResponse.createByErrorMessage("锁定用户失败");
    }

    @Override
    public ServerResponse unLockUser(int userId) {
        if (userMapper.selectByPrimaryKey(userId) == null) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        User user = new User();
        user.setId(userId);
        user.setIslock(Const.UserStaus.UNLOCK);
        int rowCount = userMapper.updateByPrimaryKeySelective(user);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("用户解锁成功");
        }
        return ServerResponse.createByErrorMessage("用户解锁失败");
    }

}
