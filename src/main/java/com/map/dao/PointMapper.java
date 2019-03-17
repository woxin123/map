package com.map.dao;

import com.map.pojo.Point;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<Point> selectPointsByRange(@Param(value = "x1") double x1,
                                    @Param(value = "x2") double x2,
                                    @Param(value = "y1") double y1,
                                    @Param(value = "y2") double y2);

    List<Point> selectAll();
}