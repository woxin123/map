package com.map.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.map.common.Const;
import com.map.common.ResponseCodeEnum;
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
import org.springframework.transaction.annotation.Transactional;

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
        for (Point point : points) {
            pointVOList.add(assemblePointToPointVO(point));
        }
        return ServerResponse.createBySuccess(pointVOList);
    }

    @Override
    public ServerResponse<PointVO> getPointById(Integer pointId) {
        Point point = pointMapper.selectByPrimaryKey(pointId);
        if (point == null) {
            return ServerResponse.createErrorByCodeMessage(ResponseCodeEnum.NOT_FOUNT.getCode(),
                    ResponseCodeEnum.NOT_FOUNT.getDesc());
        }
        PointVO pointVO = assemblePointToPointVO(point);
        return ServerResponse.createBySuccess(pointVO);
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


    @Override
    @Transactional
    public ServerResponse lockPoint(int pointId) {
        Point point = pointMapper.selectByPrimaryKey(pointId);
        if (point == null) {
            return ServerResponse.createErrorByCodeMessage(ResponseCodeEnum.NOT_FOUNT.getCode(),
                    ResponseCodeEnum.NOT_FOUNT.getDesc());
        }

        Point updatePoint = new Point();
        updatePoint.setId(pointId);
        updatePoint.setIslock(Const.PointStatus.LOCK);
        int rawCount = pointMapper.updateByPrimaryKeySelective(updatePoint);
        if (rawCount > 0) {
            return ServerResponse.createBySuccessMessage("锁定坐标点成功");
        }
        return ServerResponse.createByErrorMessage("锁定坐标点出错");
    }



    @Override
    @Transactional
    public ServerResponse unLockPoint(int pointId) {
        Point point = pointMapper.selectByPrimaryKey(pointId);
        if (point == null) {
            return ServerResponse.createErrorByCodeMessage(ResponseCodeEnum.NOT_FOUNT.getCode(),
                    ResponseCodeEnum.NOT_FOUNT.getDesc());
        }

        Point updatePoint = new Point();
        updatePoint.setId(pointId);
        updatePoint.setIslock(Const.PointStatus.UNLOCK);
        int rawCount = pointMapper.updateByPrimaryKeySelective(updatePoint);
        if (rawCount > 0) {
            return ServerResponse.createBySuccessMessage("解锁坐标点成功");
        }
        return ServerResponse.createByErrorMessage("解锁坐标点出错");
    }

    @Override
    public ServerResponse<PageInfo<Point>> getAllPoints(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Point> pointList = pointMapper.selectAll();
        PageInfo<Point> pageInfo = new PageInfo<>(pointList);
        return ServerResponse.createBySuccess(pageInfo);
    }

}
