package com.map.web.service.impl;

import com.map.web.model.ResultBuilder;
import com.map.web.model.ResultModel;
import com.map.web.service.UtilsService;
import org.springframework.stereotype.Service;

@Service
public class UtilsServiceImpl implements UtilsService {
    public ResultModel notLoginIn() {
        return ResultBuilder.getFailure(-1, "未登录");
    }

    public ResultModel logonExpires() {
        return ResultBuilder.getFailure(-2, "登录过期");
    }

    public ResultModel loginException() {
        return ResultBuilder.getFailure(-3, "登录异常");
    }

    @Override
    public ResultModel fileNotAllow() {
        return ResultBuilder.getFailure(-1, "不支持的文件类型");
    }

    @Override
    public ResultModel adminNotLoginIn() {
        return ResultBuilder.getFailure(-1, "管理员未登录");
    }

    @Override
    public ResultModel noJurisdiction() {
        return ResultBuilder.getFailure(-4, "权限不足");
    }


}
