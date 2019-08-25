package com.foss.news.service;

import com.foss.news.configuration.MvcConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadService {

    public String upload(MultipartFile file) throws IOException {
        String type=file.getContentType().split("/")[1];
        String fileName=System.currentTimeMillis()+"."+type;
        File oldFile=new File(MvcConfiguration.folderUploadUrl,fileName);
        file.transferTo(oldFile);
        return fileName;
    }

}
