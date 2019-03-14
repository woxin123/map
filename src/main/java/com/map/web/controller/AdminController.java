package com.map.web.controller;

import com.map.domain.Admin;
import com.map.utils.ErroUtils;
import com.map.web.model.ResultModel;
import com.map.web.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author: mengchen
 * Create by 18-4-11
 */
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/admin/register", method = POST)
    public ResultModel registerAdmin(@Valid Admin admin, BindingResult result) {
        if (result.hasErrors()) {
            return ErroUtils.handlerReutrnError(result);
        }

        return adminService.register(admin);
    }

    @RequestMapping(value = "/admin/login", method = POST)
    public ResultModel loginAdmin(@Valid Admin admin, BindingResult result) {
        if (result.hasErrors()) {
            return ErroUtils.handlerReutrnError(result);
        }

        return adminService.login(admin);
    }



}
