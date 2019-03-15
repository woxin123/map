package com.map.pojo;

import java.util.Date;

public class Remark {
    private Integer id;

    private Integer infoId;

    private Integer topicId;

    private String content;

    private Date createAt;

    private Integer fromId;

    private Integer toId;

    private Integer clickCount;

    public Remark(Integer id, Integer infoId, Integer topicId, String content, Date createAt, Integer fromId, Integer toId, Integer clickCount) {
        this.id = id;
        this.infoId = infoId;
        this.topicId = topicId;
        this.content = content;
        this.createAt = createAt;
        this.fromId = fromId;
        this.toId = toId;
        this.clickCount = clickCount;
    }

    public Remark() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }
}