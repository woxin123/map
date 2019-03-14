package com.map.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.map.domain.Replay;
import com.map.mapper.ClickMapper;
import com.map.mapper.ReplayMapper;
import com.map.web.model.RemarkModel;
import com.map.web.model.ReplayModel;
import com.map.web.service.ReplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReplayServiceImpl implements ReplayService {

    @Autowired
    private ReplayMapper replayMapper;

    @Autowired
    private ClickMapper clickMapper;

    @Transactional
    @Override
    public boolean save(Replay replay) {
        int status  = replayMapper.save(replay);
        if (status == 1) {
            return true;
        }
        return false;
    }

    @Override
    public List<ReplayModel> getByCommId(Integer commId, Integer pageNo, Integer pageSize, Integer userId) {
        PageHelper.startPage(pageNo, pageSize);
        List<ReplayModel> replayModels = replayMapper.selectAllByCommId(commId);
        if (userId != null) {
            for (ReplayModel replayModel : replayModels) {
                if (clickMapper.isExist(userId, 3, replayModel.getId())) {
                    replayModel.setIsClick(true);
                } else
                    replayModel.setIsClick(false);
            }
        }
        return replayModels;
    }
}
