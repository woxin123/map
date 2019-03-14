package com.map.web.service;

import com.map.domain.Information;
import com.map.web.model.ImageMessage;
import com.map.web.model.InformationModel;
import com.map.web.model.ResultModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InformationService {

    ResultModel addMessage(int pointId, String content, int uer_id);
    ResultModel addInformation(int pointId, int type, int userId, String content);
    ResultModel getMessages(Integer userId, int pointId, int type);
    void checkClick(List<InformationModel> informationModels,
                           int userId, int type);
    @Transactional
    boolean insertInformation(Information information);

    ResultModel addMangPhotosMessage(Integer userId, int pointId, ImageMessage imageMessage);

    ResultModel deleteInformation(int id);

    ResultModel lockInformation(int infoId);

    ResultModel unLockInformation(int infoId);
}
