package com.foss.news.controller;

import com.foss.news.dao.AccountDao;
import com.foss.news.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    AccountDao accountDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping(value = "/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping(value = "/signup")
    public String signUpPage(){
        return "signup";
    }

    @PostMapping(value = "/signup")
    public String signUp(@ModelAttribute Account acc, Model model, HttpSession session){
        try{
            acc.setPassword(passwordEncoder.encode(acc.getPassword()));
            acc.setIsadmin(false);
            accountDao.save(acc);
            session.setAttribute("login_error","Dang ky thanh cong, hay dang nhap");
            return "redirect:/login";
        }catch (Exception ex){
            model.addAttribute("message_status","dang ky that bai: "+ex.getMessage());
            return "signup";
        }
    }
}
