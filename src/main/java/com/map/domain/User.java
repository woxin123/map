package com.map.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.map.web.View.DetialView;
import com.map.web.View.SimpleView;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户
 */
public class User implements Serializable {


    private int id;

    @NotBlank(message = "用户名不能为空")
    @Length(min = 2, max = 8, message = "用户名必须在2到8为之间")
    private String username;

    @NotBlank(message = "账号不能为空")
    @Length(min = 6, max = 12, message = "账号必须在6到12位之间")
    private String account;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 16, message = "密码长度必须在6到16个之间")
    private String password;

    private String image;

    private String type;

    private int isLock = 0;

    public User() {
    }

    public User(String username, String account, String password, String image, String type) {
        this.username = username;
        this.account = account;
        this.password = password;
        this.image = image;
        this.type = type;
    }

    @JsonView(SimpleView.class)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonView(SimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonView(SimpleView.class)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonView(SimpleView.class)
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    @JsonView(DetialView.class)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonView(DetialView.class)
    public int getIsLock() {
        return isLock;
    }

    public void setIsLock(int isLock) {
        this.isLock = isLock;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
