package com.map.pojo;

public class Click {
    private Integer id;

    private Integer userId;

    private Integer type;

    private Integer infoOrRemark;

    public Click(Integer id, Integer userId, Integer type, Integer infoOrRemark) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.infoOrRemark = infoOrRemark;
    }

    public Click() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getInfoOrRemark() {
        return infoOrRemark;
    }

    public void setInfoOrRemark(Integer infoOrRemark) {
        this.infoOrRemark = infoOrRemark;
    }
}