package com.map.web.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.map.converter.ImageConverter;
import com.map.web.View.SimpleView;
import com.map.web.model.ImageMessage;
import com.map.web.model.ResultBuilder;
import com.map.web.model.ResultModel;
import com.map.web.service.InformationService;
import com.map.web.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.map.web.model.VideoMessage;

import static com.map.utils.FileUtil.getMultiPartSuffix;
import static com.map.utils.FileUtil.getParentPath;
import static com.map.utils.FileUtil.getRandomFileName;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class FileUploadController {

    Logger logger = Logger.getLogger(FileUploadController.class);


    @Autowired
    private InformationService informationService;

    @Autowired
    private UserService userService;

    // 视频音频的上传
    @RequestMapping(value = "/upload/{pointId}", method = POST)
    public ResultModel fileUpload(@PathVariable int pointId, @RequestParam("type") int type,
                                  @RequestParam("file") CommonsMultipartFile file,
                                  @RequestParam(required = false) String title,
                                  HttpServletRequest request) throws JsonProcessingException {

        if (!(type == 2 || type == 3)) {
            return ResultBuilder.getFailure(4, "类型不合法");
        }
        if (type == 3 && title == null) {
            return ResultBuilder.getFailure(5, "无效的标题");
        }

        // 得到userId;
        Integer userId = (Integer) request.getAttribute("id");
        ResultModel resultModel = isFileNull(file);
        if (resultModel != null) {
            return resultModel;
        }

        // 获取文件上传的路径
        String parentPath = getParentPath();
        // 得到文件名
        String fileRandomName = getRandomFileName();

        String suffix = getMultiPartSuffix(file);

        String path = null;
        switch (type) {
            case 2:
                path = "/audio/" + fileRandomName + suffix;
                break;
            case 3:
                path = "/video/" + fileRandomName + suffix;
                break;
        }
        try {
            File realFile = new File(parentPath + path);

            File dir = realFile.getParentFile();
            if (!dir.exists()) {
                dir.mkdir();
            }
            file.transferTo(realFile);
        } catch (IOException e) {
            logger.info("文件上传出错");
            e.printStackTrace();
            return ResultBuilder.getFailure(2, "文件上传出错");
        }
        VideoMessage fileMessage = new VideoMessage();
        if (type == 3)
            fileMessage.setTitle(title);
        else
            fileMessage.setTitle(null);
        fileMessage.setUrl(path);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = null;
//        if (type == 3)
            content = objectMapper.writeValueAsString(fileMessage);
        return informationService.addInformation(pointId, type, userId, content);
    }


    @RequestMapping("/uploadIcon")
    @JsonView(SimpleView.class)
    public ResultModel uploadIcon(MultipartFile file,
                                  HttpServletRequest request) throws IOException {
        ResultModel resultModel = isFileNull(file);
        if (resultModel != null) {
            return resultModel;
        }
        Integer userId = (Integer) request.getAttribute("id");
        String fileName = file.getOriginalFilename();
        String suffix = new String(fileName.substring(fileName.lastIndexOf(".")));

        // 获取文件上传的路径
        String parentPath = getParentPath();

        long localTime = new Date().getTime();
        String path = "/icon/" + localTime + userId + suffix;
        File localFile = new File(parentPath, path);
        if (!localFile.getParentFile().exists()) {
            localFile.getParentFile().mkdir();
        }
        System.out.println(localFile.getAbsolutePath());
        file.transferTo(localFile);

        return userService.saveIcon(userId, path);
    }

    @PostMapping("/uploadMangPhotos/{pointId:\\d+}")
    public ResultModel uploadMangPicture(@PathVariable int pointId, String title,
                                         MultipartHttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("id");
        List<MultipartFile> photos = request.getFiles("file");
        if (photos.isEmpty()) {
            return ResultBuilder.getFailure(1, "文件内容为空");
        }

        for (MultipartFile file : photos) {
            ResultModel resultModel = isFileNull(file);
            if (resultModel != null) {
                return resultModel;
            }
        }
        String paths[] = new String[photos.size()];
        String parentPath = getParentPath();

        File parentFile = new File(parentPath, "/photo");
        if (!parentFile.exists()) {
            parentFile.mkdir();
        }
        int count = 0;

        for (MultipartFile file : photos) {

            String fileRandomName = getRandomFileName();
            paths[count] = "/photo/" + fileRandomName + ".jpg";
            try {
                ImageConverter.imageConverter(parentPath, fileRandomName, file.getInputStream());
            } catch (IOException e) {
                logger.info("文件上传出错");
                e.printStackTrace();
                return ResultBuilder.getFailure(2, "文件上传出错");
            }
            count++;
        }
        ImageMessage imageMessage = new ImageMessage();

        imageMessage.setTitle(title);
        imageMessage.setUrls(paths);
        return informationService.addMangPhotosMessage(userId, pointId, imageMessage);
    }

//    @PostMapping("/uploadMangPhotos/{pointId:\\d+}")
//    public ResultModel uploadMangPicture(@PathVariable int pointId,
//                                         HttpServletRequest request, HttpServletResponse response) {
//        Integer userId = (Integer) request.getAttribute("id");
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//
//        List<String> paths = new ArrayList<>();
//        String parentPath = getParentPath();
//
//        if (multipartResolver.isMultipart(request)) {
//            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
//            Iterator<String> iter = multiRequest.getFileNames();
//            System.out.println(multiRequest.toString());
//            while (iter.hasNext()) {
//
//                // 获得上传文件
//                MultipartFile file = multiRequest.getFile(iter.next());
//                if (file != null) {
//                    // 获出去当前上传文件的文件名
//                    String fileName = file.getOriginalFilename();
//                    System.out.println(fileName);
//                    // 如果名称不为""，说明该文件存在
//                    if (fileName.trim() != "") {
//                        String fileRandomName = getRandomFileName();
//                        paths.add("/photo/" + fileRandomName + ".jpg");
//                        try {
//                            ImageConverter.imageConverter(parentPath, fileRandomName, file.getInputStream());
//                        } catch (IOException e) {
//                            logger.error("上传文件出错");
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        } else {
//            ResultBuilder.getFailure(1, "文件内容为空");
//        }
//        if (paths.size() == 0) {
//            ResultBuilder.getFailure(1, "文件内容为空");
//        }
//        String[] pathArray = new String[paths.size()];
//        for (int i = 0; i < paths.size(); i++) {
//            pathArray[i] = paths.get(i);
//        }
//        return informationService.addMangPhotosMessage(userId, pointId, pathArray);
//    }


    private ResultModel isFileNull(MultipartFile file) {
        if (file == null) {
            return ResultBuilder.getFailure(1, "文件内容为空");
        }
        if (file.isEmpty()) {
            return ResultBuilder.getFailure(1, "文件内容为空");
        }

        return null;
    }


}