package com.yfr.upload.controller;

import com.yfr.upload.service.UploadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/upload")
@Controller
public class UploadController {

    @Autowired
    private UploadService service;

    /**
     * 上传图片
     * @return
     */
    @PostMapping("/image")
    public ResponseEntity<String> uploadIma(@RequestParam(name = "file",required = true) MultipartFile file){

        String url=service.uploadImage(file);
        if(StringUtils.isBlank(url)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(url, HttpStatus.OK);
    }
}
