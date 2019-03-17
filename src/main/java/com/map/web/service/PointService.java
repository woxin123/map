package com.map.web.service;

import com.map.common.ServerResponse;
import com.map.vo.PointVO;

import java.util.List;

public interface PointService {
    ServerResponse addPoint(String name, double longitude, double latitude, int id);

    ServerResponse<List<PointVO>> getPoints(double longitude, double latitude, int range);
//
//    ResultModel getItems(int pointId);
//
//    ResultModel lockPoint(int pointId);
//
//    ResultModel unLockPoint(int pointId);
}
