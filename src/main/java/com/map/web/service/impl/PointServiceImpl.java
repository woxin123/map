package com.map.web.service.impl;

import com.map.common.Const;
import com.map.common.ServerResponse;
import com.map.dao.InformationMapper;
import com.map.dao.PointMapper;
import com.map.dao.UserMapper;
import com.map.dto.InformationCount;
import com.map.pojo.User;
import com.map.utils.DateTimeUtil;
import com.map.vo.PointVO;
import com.map.pojo.Point;
import com.map.web.service.PointService;
import com.map.utils.MapUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class PointServiceImpl implements PointService {

    Logger logger = Logger.getLogger(PointServiceImpl.class);

    private final PointMapper pointMapper;

    private final UserMapper userMapper;

    private final InformationMapper informationMapper;

    public PointServiceImpl(PointMapper pointMapper, UserMapper userMapper, InformationMapper informationMapper) {
        this.pointMapper = pointMapper;
        this.userMapper = userMapper;
        this.informationMapper = informationMapper;
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

    public ServerResponse<List<PointVO>> getPoints(double longitude, double latitude, int range) {
        double dlon = MapUtil.dlon(range, latitude);
        double dlat = MapUtil.dlat(range, longitude);
        System.out.println(dlat + " " + dlon);
        double x1 = longitude + dlon;
        double x2 = longitude - dlon;
        double y1 = latitude + dlat;
        double y2 = latitude - dlat;
        List<Point> points = pointMapper.selectPointsByRange(x1, x2, y1, y2);
        List<PointVO> pointVOList = new ArrayList<>();
        for(Point point : points) {
            pointVOList.add(assemblePointToPointVO(point));
        }
        return ServerResponse.createBySuccess(pointVOList);
    }

    public PointVO assemblePointToPointVO(Point point) {
        PointVO pointVO = new PointVO();
        pointVO.setId(point.getId());
        pointVO.setName(point.getName());
        pointVO.setLongitude(point.getLongitude());
        pointVO.setLatitude(point.getLatitude());
        User user = userMapper.selectByPrimaryKey(point.getCreateBy());
        pointVO.setUsername(user.getUsername());
        pointVO.setCreateTime(DateTimeUtil.dateToString(point.getCreateAt()));
        InformationCount informationCount = new InformationCount();
        informationCount.setTxtCount(informationMapper.selectByMessageTypeCount(point.getId(), Const.InformationType.TXT_INFORMATION));
        informationCount.setImgCount(informationMapper.selectByMessageTypeCount(point.getId(), Const.InformationType.IMG_INFORMATION));
        informationCount.setAudCount(informationMapper.selectByMessageTypeCount(point.getId(), Const.InformationType.AUD_INFORMATION));
        informationCount.setVidCount(informationMapper.selectByMessageTypeCount(point.getId(), Const.InformationType.VID_INFORMATION));
        pointVO.setInformationCount(informationCount);
        return pointVO;
    }
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
