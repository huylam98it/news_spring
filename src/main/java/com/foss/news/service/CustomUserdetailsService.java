package com.foss.news.service;

import com.foss.news.dao.AccountDao;
import com.foss.news.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserdetailsService implements UserDetailsService {

    @Autowired
    public AccountDao accountDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account=accountDao.findAccountByUsername(username);
        if(account==null){
            throw new UsernameNotFoundException("Thong tin dang nhap khong chinh xac");
        }
        List<GrantedAuthority> authorities=new ArrayList<>();
        if(account.getIsadmin()==true){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else{
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        User user=new User(username,account.getPassword(),authorities);
        return user;
    }
}
