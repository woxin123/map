package com.map.dto;

import javax.validation.constraints.NotBlank;


public class UserInputDTO {

    @NotBlank(message = "姓名不能为空")
    private String username;

    @NotBlank(message = "账号不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String image;

    private Integer sex;

    @NotBlank(message = "email不能为空")
    private String email;

    private String phone;

    public UserInputDTO(String username, String account, String password, String image, Integer sex, String email, String phone) {
        this.username = username;
        this.account = account;
        this.password = password;
        this.image = image;
        this.sex = sex;
        this.email = email;
        this.phone = phone;
    }

    public UserInputDTO() {
        super();
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }


    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    @Override
    public String toString() {
        return "UserInputDTO{" +
                "username='" + username + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}