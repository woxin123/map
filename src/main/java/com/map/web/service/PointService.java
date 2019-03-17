package com.map.web.service;

import com.map.common.ServerResponse;
import com.map.web.model.ResultModel;

public interface PointService {
    ServerResponse addPoint(String name, double longitude, double latitude, int id);

//    ResultModel getPoints(double longitude, double latitude, int range);
//
//    ResultModel getItems(int pointId);
//
//    ResultModel lockPoint(int pointId);
//
//    ResultModel unLockPoint(int pointId);
}
