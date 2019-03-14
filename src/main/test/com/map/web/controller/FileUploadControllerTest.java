package com.map.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(locations = {"classpath:application*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class FileUploadControllerTest {


    private MockMvc mockMvc;

    @Before
    public void setUp() {
        FileUploadController fileUploadController = new FileUploadController();
        mockMvc =  MockMvcBuilders.standaloneSetup(fileUploadController).build();
    }
    @Test
    public void whenFileUpload() throws Exception {
        File file = new File("F:\\图片\\icon1.png");
        FileInputStream inputStream = new FileInputStream(file);
        mockMvc.perform(fileUpload("/iconUpload/11}")
                .file(new MockMultipartFile("icon", inputStream)))
                .andExpect(status().isOk());
    }
}
