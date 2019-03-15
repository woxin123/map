package com.map.pojo;

import java.util.Date;

public class Information {
    private Integer id;

    private Integer pointId;

    private Integer userId;

    private Integer type;

    private String content;

    private Integer remarkCount;

    private Integer clickCount;

    private Date createAt;

    private Integer islock;

    public Information(Integer id, Integer pointId, Integer userId, Integer type, String content, Integer remarkCount, Integer clickCount, Date createAt, Integer islock) {
        this.id = id;
        this.pointId = pointId;
        this.userId = userId;
        this.type = type;
        this.content = content;
        this.remarkCount = remarkCount;
        this.clickCount = clickCount;
        this.createAt = createAt;
        this.islock = islock;
    }

    public Information() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPointId() {
        return pointId;
    }

    public void setPointId(Integer pointId) {
        this.pointId = pointId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getRemarkCount() {
        return remarkCount;
    }

    public void setRemarkCount(Integer remarkCount) {
        this.remarkCount = remarkCount;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Integer getIslock() {
        return islock;
    }

    public void setIslock(Integer islock) {
        this.islock = islock;
    }
}