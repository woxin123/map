package com.map.web.controller;

import com.map.domain.Replay;
import com.map.web.model.ReplayModel;
import com.map.web.model.ResultBuilder;
import com.map.web.model.ResultModel;
import com.map.web.service.ReplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
public class ReplayController {

    @Autowired
    private ReplayService replayService;

    @PostMapping("/replay/{commId}")
    public ResultModel addReplay(@PathVariable Integer commId, Replay replay,
                                 HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("id");
        replay.setFromId(userId);
        replay.setCommId(commId);
        replay.setCreateAt(new Date());
        replay.setClick(0);
        if (replayService.save(replay)) {
            return ResultBuilder.getSuccess("回复成功");
        } else {
            return ResultBuilder.getFailure(1,"回复失败");
        }
    }

    @GetMapping("/none/replay")
    public ResultModel getReplays(int commId, int pageNo, int pageSize,HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("id");
        List<ReplayModel> replayModels = replayService.getByCommId(commId, pageNo, pageSize, userId);
        if (replayModels.size() == 0) {
            return ResultBuilder.getFailure(1, "无回复");
        } else {
            return ResultBuilder.getSuccess(replayModels);
        }
    }

}
