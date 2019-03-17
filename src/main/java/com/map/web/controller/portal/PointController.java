package com.map.web.controller.portal;

import com.map.common.ServerResponse;
import com.map.web.service.PointService;
import com.map.utils.DoubleUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@RestController
@RequestMapping("/point")
public class PointController {
    @Autowired
    PointService pointService;

    /**
     * @param name      点的名字
     * @param longitude 经度
     * @param latitude  纬度
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    @PostMapping
    public ServerResponse addPoint(String name, double longitude, double latitude,
                                   HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (StringUtils.isBlank(name)) {
            return ServerResponse.createByErrorMessage("点的名字不能为空");
        }
        int userId = (int) request.getAttribute("id");
        longitude = DoubleUtil.accuracy(longitude, 5);
        latitude = DoubleUtil.accuracy(latitude, 5);
        return pointService.addPoint(name, longitude, latitude, userId);
    }

//    @RequestMapping("/none/getPoints")
//    public ResultModel getPoints(double longitude, double latitude, int range) {
//        return pointService.getPoints(longitude, latitude, range);
//    }
//
//    @RequestMapping("/none/getItems/{pointId}")
//    public ResultModel getItems(@PathVariable int pointId) {
//        return pointService.getItems(pointId);
//    }
//
//    @RequestMapping("/admin/lockpoint/{pointId:\\d+}")
//    public ResultModel lockPoint(@PathVariable int pointId) {
//        return pointService.lockPoint(pointId);
//    }
//
//    @RequestMapping("/admin/unlockpoint/{pointId:\\d+}")
//    public ResultModel unLockPoint(@PathVariable int pointId) {
//        return pointService.unLockPoint(pointId);
//    }
//
//
}
