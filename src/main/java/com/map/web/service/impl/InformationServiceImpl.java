package com.map.web.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.map.domain.Information;
import com.map.mapper.*;
import com.map.web.model.*;
import com.map.web.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class InformationServiceImpl implements InformationService {

    @Autowired
    InformationMapper informationMapper;
    @Autowired
    ItemsMapper itemsMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ClickMapper clickMapper;
    @Autowired
    InformationService informationService;
    @Autowired
    PointMapper pointMapper;

    // 需要添加判断点是否存在
    public ResultModel addMessage(int pointId, String content, int user_id) {
        // 判断需要添加的点是否存在
        if (!pointMapper.isExistPoint(pointId)) {
            ResultBuilder.getFailure(2, "点不存在");
        }
        Information information = new Information(pointId, user_id, 0, content, 0, 0, new Date());

        if (informationService.insertInformation(information)) {
            return ResultBuilder.getSuccess("添加成功");
        } else {
            return ResultBuilder.getFailure(1, "添加失败");
        }
    }

    public ResultModel addInformation(int pointId, int type, int userId, String content) {
        Information information = new Information(pointId, userId, type, content, 0, 0, new Date());
        if (informationService.insertInformation(information)) {
            return ResultBuilder.getSuccess("文件上传成功");
        } else {
            return ResultBuilder.getFailure(3, "文件上传失败");
        }
    }

//    public ResultModel getMessages(int pointId, int type) {
//        List<InformationModel> informations = informationMapper.findInformation(pointId, type);
//        if (type == 2 || type == 3) {
//            for (InformationModel informationModel : informations) {
//                informationModel = handlerInformation(informationModel);
//            }
//        }
//        if (informations.size() != 0) {
//
//            return ResultBuilder.getSuccess(informations);
//        } else {
//            return ResultBuilder.getFailure(1, "无消息");
//        }
//    }

    public ResultModel getMessages(Integer userId, int pointId, int type) {
        List<InformationModel> informations = informationMapper.findInformation(pointId, type);
        if (type == 1 || type == 3 || type == 2) {
            for (InformationModel informationModel : informations) {
                handlerInformation(informationModel, type);
            }
        }
        if (informations.size() != 0) {
            if (userId != null)
                informationService.checkClick(informations, userId, 1);
            return ResultBuilder.getSuccess(informations);
        } else {
            return ResultBuilder.getFailure(1, "无消息");
        }
    }


    public ResultModel addMangPhotosMessage(Integer userId, int pointId, ImageMessage imageMessage) {
        Information information = new Information();
        information.setPointId(pointId);
        information.setUserId(userId);
        information.setType(1);
        information.setCreateAt(new Date());
        information.setClickCount(0);
        information.setRemarkCount(0);
//        StringBuilder content = new StringBuilder(paths[0]);
//        for (int i = 1; i < paths.length; i++) {
//            content.append("&" + paths[i]);
//        }
//        information.setContent(content.toString());
        String content = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            content = objectMapper.writeValueAsString(imageMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        information.setContent(content);
        if (insertInformation(information)) {
            return ResultBuilder.getSuccess("文件上传成功");
        } else {
            return ResultBuilder.getFailure(3, "文件上传失败");
        }
    }

    public ResultModel deleteInformation(int id) {
        InformationModel information = informationMapper.findById(id);
        if (information == null) {
            ResultBuilder.getFailure(1, "信息不存在");
        }
        ItemsModel itemsModel = itemsMapper.findItemsByPointId(information.getPointId());
        switch (information.getType()) {
            case 0:
                itemsModel.setMesCount(itemsModel.getMesCount() - 1);
                break;
            case 1:
                itemsModel.setPhoCount(itemsModel.getPhoCount() - 1);
                break;
            case 2:
                itemsModel.setAudCount(itemsModel.getAudCount() - 1);
                break;
            case 3:
                itemsModel.setVidCount(itemsModel.getVidCount() - 1);
        }
        itemsMapper.updateItems(itemsModel);
        informationMapper.delete(id);
        return ResultBuilder.getSuccess("删除成功");
    }

    @Override
    public ResultModel lockInformation(int infoId) {
        boolean isExist = informationMapper.isExist(infoId);
        if (!isExist) {
            return ResultBuilder.getFailure(1, "锁定的信息不存在");
        }
        if (informationMapper.isLockInformation(infoId) == 1) {
            return ResultBuilder.getFailure(2, "该信息已经被锁定");
        }
        boolean lockInfo = informationMapper.lockInformation(infoId);
        if (lockInfo) {
            return ResultBuilder.getSuccess("信息锁定成功");
        }
        return ResultBuilder.getFailure(3, "信息锁定失败");
    }

    @Override
    public ResultModel unLockInformation(int infoId) {
        boolean isExist = informationMapper.isExist(infoId);
        if (!isExist) {
            return ResultBuilder.getFailure(1, "锁定的信息不存在");
        }
        if (informationMapper.isLockInformation(infoId) == 0) {
            return ResultBuilder.getFailure(2, "该信息没有被锁定");
        }
        boolean lockInfo = informationMapper.unLockInformation(infoId);
        if (lockInfo) {
            return ResultBuilder.getSuccess("信息解锁成功");
        }
        return ResultBuilder.getFailure(3, "信息解锁失败");
    }

    @Transactional
    public boolean insertInformation(Information information) {
        ItemsModel itemsModel = itemsMapper.findItemsByPointId(information.getPointId());
        switch (information.getType()) {
            case 0:
                itemsModel.setMesCount(itemsModel.getMesCount() + 1);
                break;
            case 1:
                itemsModel.setPhoCount(itemsModel.getPhoCount() + 1);
                break;
            case 2:
                itemsModel.setAudCount(itemsModel.getAudCount() + 1);
                break;
            case 3:
                itemsModel.setVidCount(itemsModel.getVidCount() + 1);
                break;
        }
        if (itemsMapper.updateItems(itemsModel) == 0) {
            return false;
        }
        if (informationMapper.saveMessage(information) == 0) {
            return false;
        }
        return true;
    }


    public void checkClick(List<InformationModel> informationModels,
                           int userId, int type) {
        for (InformationModel informationModel : informationModels) {
            if (clickMapper.isExist(userId, type, informationModel.getId())) {
                informationModel.setIsClick(true);
            } else {
                informationModel.setIsClick(false);
            }
        }

    }

    private void handlerInformation(InformationModel informationModel, Integer type) {
        String content = (String) informationModel.getContent();
        ObjectMapper objectMapper = new ObjectMapper();
        FileMessage fileMessage = null;
        try {
            if (type == 3 || type == 2)
                fileMessage = objectMapper.readValue(content, VideoMessage.class);
            else
                fileMessage = objectMapper.readValue(content, ImageMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        informationModel.setContent(fileMessage);
    }
}
