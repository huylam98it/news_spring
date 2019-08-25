package com.foss.news.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    public static String folderUploadUrl;

    public MvcConfiguration(){
        String homeFolder=System.getProperty("user.home");
        folderUploadUrl=homeFolder.replace("\\","/")+"/images/";
        File uploadFolder=new File(folderUploadUrl);
        if(uploadFolder.exists()==false){
            uploadFolder.mkdirs();
        }
        System.out.println("folder upload: "+folderUploadUrl);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public_resource/**").addResourceLocations("file:"+folderUploadUrl);
    }
}
