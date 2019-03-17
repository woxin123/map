package com.map.web.service.impl;

import com.map.common.ServerResponse;
import com.map.dao.PointMapper;
import com.map.pojo.Point;
import com.map.web.model.ItemsModel;
import com.map.web.model.PointAndItems;
import com.map.web.model.ResultBuilder;
import com.map.web.model.ResultModel;
import com.map.web.service.PointService;
import com.map.utils.MapUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class PointServiceImpl implements PointService {

    Logger logger = Logger.getLogger(PointServiceImpl.class);

    private final PointMapper pointMapper;

    public PointServiceImpl(PointMapper pointMapper) {
        this.pointMapper = pointMapper;
    }


    public ServerResponse addPoint(String name, double longitude, double latitude, int id) {
        if (pointMapper.selectLongitudeAndLatitude(longitude, latitude) == null) {
            Point point = new Point();
            point.setName(name);
            point.setLongitude(longitude);
            point.setLatitude(latitude);
            point.setCreateAt(new Date());
            point.setCreateBy(id);
            int rawCount = pointMapper.insert(point);
            if (rawCount > 0) {
                logger.info("坐标：" + longitude + ", " + latitude + " 添加成功");
                return ServerResponse.createBySuccessMessage("添加成功");
            } else {
                logger.info("坐标：" + longitude + ", " + latitude + " 添加失败");
                return ServerResponse.createByErrorMessage("添加失败");
            }
        } else {
            logger.info("该点已存在");
            return ServerResponse.createByErrorMessage("该点已存在");
        }
    }
//
//    public ResultModel getPoints(double longitude, double latitude, int range) {
//        double dlon = MapUtil.dlon(range, latitude);
//        double dlat = MapUtil.dlat(range, longitude);
//        System.out.println(dlat + " " + dlon);
//        double x1 = longitude + dlon;
//        double x2 = longitude - dlon;
//        double y1 = latitude + dlat;
//        double y2 = latitude - dlat;
//        List<Point> points = pointMapper.findPoints(x1, x2, y1, y2);
//        List<PointAndItems> pointAndItems = new ArrayList<PointAndItems>();
//        for (Point point : points) {
//            ItemsModel itemsModel = itemsMapper.findItemsByPointId(point.getId());
//            pointAndItems.add(new PointAndItems(point, itemsModel));
//        }
//        if (points.size() == 0) {
//            return ResultBuilder.getFailure(1, "无点");
//        } else {
//            return ResultBuilder.getSuccess(pointAndItems);
//        }
//    }
//
//    public ResultModel getItems(int pointId) {
//        ItemsModel itemsModel = itemsMapper.findItemsByPointId(pointId);
//        if (itemsModel != null) {
//            return ResultBuilder.getSuccess(itemsModel);
//        } else {
//            return ResultBuilder.getFailure(1, "该点不存在");
//        }
//    }
//
//    @Override
//    public ResultModel lockPoint(int pointId) {
//        boolean isExist = pointMapper.isExistPoint(pointId);
//        if (!isExist) {
//            return ResultBuilder.getFailure(1, "该点不存在");
//        }
//        if (pointMapper.isLockedPoint(pointId) == 1) {
//            return ResultBuilder.getFailure(2, "该点已经被锁定");
//        }
//        boolean lockPoint = pointMapper.lockPoint(pointId);
//        if (lockPoint) {
//            return ResultBuilder.getSuccess("该点锁定成功");
//        }
//        return ResultBuilder.getFailure(3, "该点锁定失败");
//    }
//
//
//
//    @Override
//    public ResultModel unLockPoint(int pointId) {
//        boolean isExist = pointMapper.isExistPoint(pointId);
//        if (!isExist) {
//            return ResultBuilder.getFailure(1, "该点不存在");
//        }
//        if (pointMapper.isLockedPoint(pointId) == 0) {
//            return ResultBuilder.getFailure(2, "该点没有被锁定");
//        }
//        boolean unlockPoint = pointMapper.unlockPoint(pointId);
//        if (unlockPoint) {
//            return ResultBuilder.getSuccess("该点解锁成功");
//        }
//        return ResultBuilder.getFailure(3, "该点解锁失败");
//    }
}
