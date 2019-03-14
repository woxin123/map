package com.map.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.map.web.model.VideoMessage;

import java.io.IOException;

public class FileMessageTest {

    public static void main(String[] args) throws IOException {
        String fileMessage = "{\"title\":\"好听的歌\",\"url\":\"/video/2018051386662.mp3\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        VideoMessage message = objectMapper.readValue(fileMessage, VideoMessage.class);
        System.out.println(message.getTitle() + ":" + message.getUrl());
    }
}
