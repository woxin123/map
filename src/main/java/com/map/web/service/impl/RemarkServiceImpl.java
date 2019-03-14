package com.map.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.map.domain.Remark;
import com.map.mapper.ClickMapper;
import com.map.mapper.InformationMapper;
import com.map.mapper.RemarkMapper;
import com.map.web.model.InformationModel;
import com.map.web.model.RemarkModel;
import com.map.web.model.ResultBuilder;
import com.map.web.model.ResultModel;
import com.map.web.service.RemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RemarkServiceImpl implements RemarkService {
    @Autowired
    RemarkMapper remarkMapper;
    @Autowired
    ClickMapper clickMapper;
    @Autowired
    InformationMapper informationMapper;
    public ResultModel remarked(Remark remark) {
        if (saveRemark(remark)) {
            return ResultBuilder.getSuccess("评论成功");
        } else {
            return ResultBuilder.getFailure(1, "评论失败");
        }
    }

    public ResultModel getRemarks(int infoId, Integer userId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<RemarkModel> remarkModels = remarkMapper.getRemarks(infoId);
        if (remarkModels.size() != 0) {
            if (userId != null) {
                for (RemarkModel remarkModel : remarkModels) {
                    boolean isClick = clickMapper.isExist(userId, 2, remarkModel.getId());
                    remarkModel.setClick(isClick);
                }
            }
            return ResultBuilder.getSuccess(remarkModels);
        } else {
            return ResultBuilder.getFailure(1, "无消息");
        }
    }

    @Transactional
    @Override
    public boolean save(Remark remark) {
        int saveStatus = remarkMapper.saveRemark(remark);
        if (saveStatus == 1) {
            return true;
        }
        return false;
    }

    @Transactional
    public boolean saveRemark(com.map.domain.Remark remark) {
        InformationModel informationModel = informationMapper.findById(remark.getInfoId());
        informationModel.setRemarkCount(informationModel.getRemarkCount() + 1);
        if (informationMapper.updaateClickOrRemark(informationModel) == 0) {
            return false;
        } else {
            if(remarkMapper.saveRemark(remark) == 0) {
                return false;
            }
        }
        return true;
    }
}
