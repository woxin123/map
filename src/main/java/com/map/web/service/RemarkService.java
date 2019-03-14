package com.map.web.service;

import com.map.domain.Remark;
import com.map.web.model.ResultModel;

public interface RemarkService {
    ResultModel remarked(Remark remark);
    ResultModel getRemarks(int infoId, Integer userId, int pageNo, int pageSize);
    boolean save(Remark remark);
}
