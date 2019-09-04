package com.foss.news.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foss.news.dao.AccountDao;
import com.foss.news.dto.AccountModel;
import com.foss.news.entity.Account;

@RestController
@RequestMapping(value = "/api")
public class ApiController {

	@Autowired
	AccountDao accountDao;

	@Autowired
	PasswordEncoder passwordEncoder;

	@GetMapping(value = "/users/all")
	public ResponseEntity<Object> getAllUser() {
		List<Account> accounts = accountDao.findAll();
		return new ResponseEntity<Object>(accounts, HttpStatus.OK);
	}

	@PostMapping(value = "/users")
	public ResponseEntity<Object> addUser(@RequestBody AccountModel acc) {
		Map<String, Object> response = new HashMap<String, Object>();
		String message = "them tai khoan thanh cong";
		int err = 0;
		try {
			Account account = new Account();
			account.setId(acc.getId());
			account.setUsername(acc.getUsername());
			account.setPassword(passwordEncoder.encode(acc.getPassword()));
			account.setIsadmin(acc.isAdmin());
			accountDao.save(account);
		} catch (Exception e) {
			message = "them tai khoan that bai !";
			err = 1;
		}
		response.put("error", err);
		response.put("message", message);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@DeleteMapping(value = "/users")
	public ResponseEntity<Object> deleteUser(@RequestBody AccountModel acc) {
		Map<String, Object> response = new HashMap<String, Object>();
		String message = "xoa tai khoan thanh cong";
		int err = 0;
		try {
			Account account = accountDao.findById(acc.getId()).orElse(null);
			if (account.getIsadmin())
				throw new Exception();
			accountDao.delete(account);
		} catch (Exception e) {
			message = "xoa tai khoan that bai !";
			err = 1;
		}
		response.put("error", err);
		response.put("message", message);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
}
