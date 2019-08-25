package com.foss.news.controller;

import com.foss.news.dao.AccountDao;
import com.foss.news.dao.CategoryDao;
import com.foss.news.dao.PostDao;
import com.foss.news.dto.PostDTO;
import com.foss.news.entity.Category;
import com.foss.news.entity.Post;
import com.foss.news.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    UploadService uploadService;

    @Autowired
    AccountDao accountDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    PostDao postDao;

    @GetMapping
    public String adminPage(){
        return "admin";
    }

    @GetMapping(value = "/qlaccount")
    public String qlAccountPage(Model model){
        model.addAttribute("accounts",accountDao.findAll());
        return "qlaccount";
    }

    @GetMapping(value = "/qlcategory")
    public String qlCategoryPage(Model model){
        model.addAttribute("categories",categoryDao.findAll());
        return "qlcategory";
    }

    @GetMapping(value = "/qlpost")
    public String qlPostPage(Model model){
        model.addAttribute("categories",categoryDao.findAll());
        return "qlpost";
    }

    @PostMapping(value = "/addcategory")
    public String addCategory(@ModelAttribute Category cat,Model model){
        String message="them danh muc thanh cong";
       try{
           cat.setCreated(new Date(System.currentTimeMillis()));
           categoryDao.save(cat);
       }catch (Exception ex){
           message="them danh muc that bai";
       }
       model.addAttribute("message_status",message);
        return "redirect:/admin/qlcategory";
    }

//    add post
    @GetMapping(value = "/addpost")
    public String addPostPage(Model model, @ModelAttribute PostDTO dto){
        model.addAttribute("categories",categoryDao.findAll());
        return "addpost";
    }

    @PostMapping(value = "/addpost")
    public String addPost(Model model, @ModelAttribute PostDTO dto){
        String message_status="Them bai viet thanh cong";
        try{
            Post post=new Post();
            post.setTitle(dto.getTitle());
            post.setContent(dto.getContent());
            post.setCreated(new Date(System.currentTimeMillis()));

            Category cat=new Category();
            cat.setId(dto.getCategory());

            String image=uploadService.upload(dto.getImage());

            post.setCategory(cat);
            post.setImage(image);
            postDao.save(post);
        }catch (Exception ex){
            message_status="Them bai viet that bai: "+ex.getMessage();
            System.out.println(ex.getMessage());
        }

        model.addAttribute("categories",categoryDao.findAll());
        model.addAttribute("message_status",message_status);
        return "addpost";
    }

    @GetMapping(value = "/api/listpost/{id}")
    public ResponseEntity<Object> postApi(@PathVariable("id") long id){
        return new ResponseEntity<>(postDao.getPostByCatId(id), HttpStatus.OK);
    }
}
