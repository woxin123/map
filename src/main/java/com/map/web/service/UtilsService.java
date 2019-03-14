package com.map.web.service;

import com.map.web.model.ResultModel;

public interface UtilsService {

    ResultModel notLoginIn();

    ResultModel logonExpires();
    ResultModel loginException();

    ResultModel fileNotAllow();

    ResultModel adminNotLoginIn();

    ResultModel noJurisdiction();
}
