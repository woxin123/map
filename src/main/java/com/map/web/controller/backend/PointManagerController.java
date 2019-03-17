package com.map.web.controller.backend;

import com.github.pagehelper.PageInfo;
import com.map.common.ServerResponse;
import com.map.pojo.Point;
import com.map.web.service.PointService;
import org.springframework.web.bind.annotation.*;


/**
 *
 * @author mengchen
 * @time 19-3-17 下午7:15
 */
@RestController
@RequestMapping("/manager/point")
public class PointManagerController {

    private PointService pointService;

    public PointManagerController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping
    public ServerResponse<PageInfo<Point>> getPoints(@RequestParam(required = false, defaultValue = "1") int pageNum,
                                                      @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return pointService.getAllPoints(pageNum, pageSize);
    }

    @PostMapping("{pointId:\\d+}/lock")
    public ServerResponse lockPointById(@PathVariable Integer pointId) {
        return pointService.lockPoint(pointId);
    }

    @PostMapping("{pointId:\\d+}/unlock")
    public ServerResponse unLockPointById(@PathVariable Integer pointId) {
        return pointService.unLockPoint(pointId);
    }

}
