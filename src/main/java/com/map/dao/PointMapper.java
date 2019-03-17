package com.map.dao;

import com.map.pojo.Point;
import org.apache.ibatis.annotations.Param;

/**
 * 坐标点的管理
 */
public interface PointMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Point record);

    int insertSelective(Point record);

    Point selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Point record);

    int updateByPrimaryKey(Point record);

    Point selectLongitudeAndLatitude(@Param(value = "longitude") double longitude,
                                   @Param(value = "latitude") double latitude);

}