package com.foss.news.dao;

import com.foss.news.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao extends JpaRepository<Account,Long> {

    Account findAccountByUsername(String username);
}
