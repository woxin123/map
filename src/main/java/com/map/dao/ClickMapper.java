package com.map.dao;

import com.map.pojo.Click;

public interface ClickMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Click record);

    int insertSelective(Click record);

    Click selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Click record);

    int updateByPrimaryKey(Click record);
}