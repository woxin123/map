package com.map.dao;

import com.map.pojo.Information;
import org.apache.ibatis.annotations.Param;

public interface InformationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Information record);

    int insertSelective(Information record);

    Information selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Information record);

    int updateByPrimaryKey(Information record);

    int selectByMessageTypeCount(@Param("pointId") int pointId,
                                 @Param("type") int type);
}