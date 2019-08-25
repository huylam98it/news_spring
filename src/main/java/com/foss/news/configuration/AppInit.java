package com.foss.news.configuration;

import com.foss.news.dao.AccountDao;
import com.foss.news.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${admin.username}")
    public String adminUsername;
    @Value("${admin.password}")
    public String adminPassword;

    @Autowired
    AccountDao accountDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try{
            Account admin=new Account();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setIsadmin(true);
            accountDao.save(admin);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
