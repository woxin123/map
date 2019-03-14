package com.map.web.service.impl;

import com.map.mapper.ClickMapper;
import com.map.mapper.InformationMapper;
import com.map.mapper.RemarkMapper;
import com.map.mapper.ReplayMapper;
import com.map.web.model.*;
import com.map.web.service.ClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClickServiceImpl implements ClickService {
    @Autowired
    ClickMapper clickMapper;

    @Autowired
    InformationMapper informationMapper;

    @Autowired
    RemarkMapper remarkMapper;

    @Autowired
    ReplayMapper replayMapper;

    @Autowired
    ClickService clickService;

    public ResultModel click(int userId, int type, int infoOrRemark) {
        ClickModel clickModel = new ClickModel(userId, type, infoOrRemark);
        if (clickService.isClick(clickModel)) {
            return ResultBuilder.getSuccess("点赞成功");
        } else {
            return ResultBuilder.getFailure(1, "点赞失败");
        }
    }

    @Override
    public ResultModel unclick(int userId, int type, int infoOrRemark) {
        ClickModel clickModel = new ClickModel(userId, type, infoOrRemark);
        if (clickService.deleteClick(clickModel)) {
            return ResultBuilder.getSuccess("取消点赞成功");
        } else {
            return ResultBuilder.getFailure(1, "取消点赞失败");
        }
    }

    @Transactional
    public boolean isClick(ClickModel clickModel) {
        if (clickMapper.saveClick(clickModel) == 0)
            return false;   // 存储点赞
        // 更新information那张表 或者更新remark那张表
        if (clickModel.getType() == 1) {
            InformationModel informationModel =
                    informationMapper.findById(clickModel.getInfoOrRemark());
            informationModel.setClickCount(informationModel.getRemarkCount() + 1);
            if (informationMapper.updaateClickOrRemark(informationModel) == 0) {
                return false;
            }

        } else if (clickModel.getType() == 2) {
            int clickCount = remarkMapper.getClickCount(clickModel.getInfoOrRemark());
            if (clickCount != 0)
                remarkMapper.updateClickClick(clickCount - 1);
            else
                return false;
        } else if (clickModel.getType() == 3) {
            Integer clickCount = replayMapper.selectReplayCountByCommId(clickModel.getInfoOrRemark());
            replayMapper.addClick(clickModel.getInfoOrRemark() ,++clickCount);
        }
        return true;
    }

    @Transactional
    public boolean deleteClick(ClickModel clickModel) {
        if (clickMapper.deleteClick(clickModel) == 0)
            return false;
        // 更新information那张表 或者更新remark那张表
        if (clickModel.getType() == 1) {
            InformationModel informationModel =
                    informationMapper.findById(clickModel.getInfoOrRemark());
            if (informationModel.getClickCount() != 0)
                informationModel.setClickCount(informationModel.getRemarkCount() - 1);
            else
                return false;
            if (informationMapper.updaateClickOrRemark(informationModel) == 0) {
                return false;
            }

        } else if (clickModel.getType() == 2) {
            int clickCount = remarkMapper.getClickCount(clickModel.getInfoOrRemark());

        }
        return true;
    }
}
