package com.map.vo;

import com.map.dto.InformationCount;

/**
 * @author mengchen
 * @time 19-3-17 下午4:32
 */
public class PointVO {

    private int id;

    private String name;

    private double longitude;

    private double latitude;

    /**
     * 创建用户
     */
    private String username;

    /**
     * 创建时间
     */
    private String createTime;


    /**
     * message的数量
     */
    private InformationCount informationCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public InformationCount getInformationCount() {
        return informationCount;
    }

    public void setInformationCount(InformationCount informationCount) {
        this.informationCount = informationCount;
    }
}
