package com.foss.news.controller;

import com.foss.news.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/upload")
public class UploadController {

    @Autowired
    UploadService  uploadService;

    @PostMapping
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile file){
        try{
            String filename=uploadService.upload(file);
            Map<String,String> response=new HashMap<>();
            response.put("link","/public_resource/"+filename);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
