package com.map.web.service;

import com.github.pagehelper.PageInfo;
import com.map.common.ServerResponse;
import com.map.pojo.Point;
import com.map.vo.PointVO;

import java.util.List;

public interface PointService {
    ServerResponse addPoint(String name, double longitude, double latitude, int id);

    ServerResponse<List<PointVO>> getPoints(double longitude, double latitude, int range);

    ServerResponse<PointVO> getPointById(Integer pointId);

    ServerResponse lockPoint(int pointId);

    ServerResponse unLockPoint(int pointId);

    ServerResponse<PageInfo<Point>> getAllPoints(int pageNum, int pageSize);
}
