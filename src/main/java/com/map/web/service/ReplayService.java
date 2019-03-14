package com.map.web.service;

import com.map.domain.Replay;
import com.map.web.model.ReplayModel;

import java.util.List;

public interface ReplayService {
    boolean save(Replay replay);
    List<ReplayModel> getByCommId(Integer commId, Integer pageNo, Integer pageSize, Integer userId);
}
