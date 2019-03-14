package com.map.web.controller;

import com.auth0.jwt.interfaces.Claim;
import com.map.web.model.ResultModel;
import com.map.web.service.InformationService;
import com.map.utils.JWTUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class InformationController {

    @Autowired
    private UserController userController;

    @Autowired
    InformationService informationService;

    @RequestMapping(value = "/addMessage/{pointId}", method = POST)
    public ResultModel addMessage(@PathVariable int pointId, String content, HttpServletRequest request) {
        int id = (Integer) request.getAttribute("id");
        System.out.println(id);
        return informationService.addMessage(pointId, content, id);
    }

    @RequestMapping("/none/getMessage/{pointId}")
    public ResultModel getMessage(@PathVariable int pointId, int type,
                                  HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = userController.getUserIdFromToken(request, response);
        return informationService.getMessages(userId, pointId, type);
    }

    @DeleteMapping("/information/{id:\\d+}")
    public ResultModel deleteInformation(@PathVariable int id) {
        return informationService.deleteInformation(id);
    }

    @RequestMapping("/admin/lockinfo/{infoId:\\d+}")
    public ResultModel lockInfo(@PathVariable int infoId) {
        return informationService.lockInformation(infoId);
    }

    @RequestMapping("/admin/unlockinfo/{infoId:\\d+}")
    public ResultModel unlockInfo(@PathVariable int infoId) {
        return informationService.unLockInformation(infoId);
    }
}
