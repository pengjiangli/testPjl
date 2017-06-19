package com.gt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gt.entity.User;
import com.gt.service.IUserService;


@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private IUserService userService;
	
	@RequestMapping("/showUser")
	public String toIndex(HttpServletRequest request,Model model){
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = userService.getUserById(userId);
		request.getSession().setAttribute("user", user);
		model.addAttribute("user", user);
		return "showUser";
	}
	@RequestMapping("/index")
	public String index(HttpServletRequest request,Model model){
		User user = (User) request.getSession().getAttribute("user");
		model.addAttribute("user",user);
		return "showUser";
	}
}
