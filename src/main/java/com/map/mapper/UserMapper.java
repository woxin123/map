package com.map.mapper;


import com.map.domain.User;
import com.map.web.model.UserModel;
import org.apache.ibatis.annotations.*;

import java.net.URL;
import java.util.List;

public interface UserMapper {

    @Select("select password from map_user where account = #{account}")
    String findPasswordByAccount(String account);

    @Insert("insert into map_user(username, account, password, type) values(#{username}, #{account}," +
            "#{password}, #{type})")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    int saveUser(User user);
    @Select("select * from map_user where account = #{account}")
    User findUserByAccount(String account);

    @Select("SELECT username FROM map_user WHERE id = #{id}")
    String findUsernameById(int id);

    @Select("SELECT * from map_user where id = #{userId}")
    User findUserById(int userId);

    @Select("SELECT * from map_user where id = #{userId}")
    UserModel findUserModelById(int userId);

    @Select("UPDATE map_user SET image = #{image} where id = #{id}")
    void saveIcon(User user);

    @Select("SELECT * FROM map_user")
    List<User> getUsers();

    @Update("UPDATE FROM map_user SET username = #{username}, " +
            "password = #{password} WHERE id = #{id}")
    User updateUser(User user);

    @Delete("DELETE FROM map_user WHERE id = #{id}")
    boolean deleteUser(int id);

    @Update("UPDATE map_user set isLock = 1 WHERE id = #{userId}")
    boolean lockUser(int userId);

    @Update("UPDATE map_user set isLock = 0 WHERE id = #{userId}")
    boolean unLockUser(int userId);

    @Select("SELECT * FROM map_user WHERE username = #{username}")
    User findUserByUsername(String username);
}
