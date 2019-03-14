package com.map.mapper;

import com.map.web.model.ItemsModel;
import org.apache.ibatis.annotations.*;

public interface ItemsMapper {

    @Insert("insert into items values(#{pointId}, #{mesCount}, #{phoCount}, " +
            "#{audCount}, #{vidCount})")
    int saveItems(ItemsModel itemsModel);

    @Select("select * from items where id = #{pointId}")
    @Results({
            @Result(column = "id", property = "pointId"),
            @Result(column = "mes_count", property = "mesCount"),
            @Result(column = "pho_count", property = "phoCount"),
            @Result(column = "aud_count", property = "audCount"),
            @Result(column = "vid_count", property = "vidCount")
    })
    ItemsModel findItemsByPointId(int pointId);
    @Update("update items set mes_count = #{mesCount}, pho_count = #{phoCount}, " +
            "aud_count = #{audCount}, vid_count = #{vidCount} where id = #{pointId}")
    int updateItems(ItemsModel itemsModel);
}
