package com.foss.news.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.foss.news.dao.AccountDao;
import com.foss.news.dao.CategoryDao;
import com.foss.news.dao.PostDao;
import com.foss.news.dto.PostDTO;
import com.foss.news.dto.PostModel;
import com.foss.news.dto.ResponseModel;
import com.foss.news.entity.Account;
import com.foss.news.entity.Category;
import com.foss.news.entity.Post;
import com.foss.news.service.UploadService;

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
	public String adminPage() {
		return "admin";
	}

	@GetMapping(value = "/qlaccount")
	public String qlAccountPage(Model model) {
		return "qlaccount";
	}

	@GetMapping(value = "/qlcategory")
	public String qlCategoryPage(Model model, @RequestParam(required = false) String message) {
		if (message != null) {
			model.addAttribute("message_status", message);
		}
		model.addAttribute("categories", categoryDao.findAll());
		return "qlcategory";
	}

	@GetMapping(value = "/qlpost")
	public String qlPostPage(Model model) {
		model.addAttribute("categories", categoryDao.findAll());
		return "qlpost";
	}

	@PostMapping(value = "/addcategory")
	public String addCategory(@ModelAttribute Category cat, Model model) {
		String message = "them danh muc thanh cong";
		try {
			cat.setCreated(new Date(System.currentTimeMillis()));
			categoryDao.save(cat);
		} catch (Exception ex) {
			message = "them danh muc that bai";
		}
		model.addAttribute("message_status", message);
		return "redirect:/admin/qlcategory";
	}

//    add post
	@GetMapping(value = "/addpost")
	public String addPostPage(Model model, @ModelAttribute PostDTO dto) {
		model.addAttribute("categories", categoryDao.findAll());
		return "addpost";
	}

	@PostMapping(value = "/addpost")
	public String addPost(Model model, @ModelAttribute PostDTO dto) {
		String message_status = "Them bai viet thanh cong";
		try {
			Post post = new Post();
			post.setTitle(dto.getTitle());
			post.setContent(dto.getContent());
			post.setCreated(new Date(System.currentTimeMillis()));

			Category cat = new Category();
			cat.setId(dto.getCategory());

			String image = uploadService.upload(dto.getImage());

			post.setCategory(cat);
			post.setImage(image);
			postDao.save(post);
		} catch (Exception ex) {
			message_status = "Them bai viet that bai: " + ex.getMessage();
			System.out.println(ex.getMessage());
		}

		model.addAttribute("categories", categoryDao.findAll());
		model.addAttribute("message_status", message_status);
		return "addpost";
	}

	@GetMapping(value = "/api/listpost/{id}")
	public ResponseEntity<Object> postApi(@PathVariable("id") long id) {
		return new ResponseEntity<>(postDao.getPostByCatId(id), HttpStatus.OK);
	}

	@GetMapping(value = "/removeuser/{id}")
	public String removeUser(@PathVariable("id") long id, Model model) {
		String message_status = "Tai khoan admin khong the xoa";
		Account acc = accountDao.findById(id).orElse(null);
		if (acc.getIsadmin() == false) {
			try {
				accountDao.delete(acc);
				message_status = "Xoa tai khoan thanh cong";
			} catch (Exception ex) {
				message_status = "Xoa tai khoan that bai: " + ex.getMessage();
			}
		}
		model.addAttribute("message_status", message_status);
		model.addAttribute("accounts", accountDao.findAll());
		return "qlaccount";
	}

	@GetMapping(value = "/removecategory/{id}")
	public String removeCategory(@PathVariable("id") long id, RedirectAttributes re) {
		try {
			re.addAttribute("message", "xoa danh muc thanh cong");
			categoryDao.deleteById(id);
		} catch (Exception ex) {
			re.addAttribute("message", "xoa danh muc that bai: " + ex.getMessage());
		}
		return "redirect:/admin/qlcategory";
	}

	@PostMapping(value = "/removepost")
	public ResponseEntity<Object> removePost(@RequestBody PostModel postModel) {
		String message = "xoa bai viet thanh cong";
		System.out.println("removing post: " + postModel.getId());
		try {
			postDao.deleteById(postModel.getId());
		} catch (Exception ex) {
			message = "xoa bai viet that bai: ";
		}

		return new ResponseEntity<>(new ResponseModel(message), HttpStatus.OK);
	}
}
