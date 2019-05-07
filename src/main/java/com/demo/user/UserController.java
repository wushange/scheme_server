package com.demo.user;

import com.demo.blog.BlogInterceptor;
import com.demo.blog.BlogValidator;
import com.demo.common.model.Blog;
import com.demo.common.model.User;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * 所有 sql 与业务逻辑写在 Service 中，不要放在 Model 中，更不
 * 要放在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
public class UserController extends Controller {
	
	@Inject
	UserService service;

	public void login(){
		String name = getPara("name");
		String pwd = getPara("pwd");
		User user = service.validate(name,pwd);
		if(user!=null){
			renderText("登陆成功");
		}else{
			renderText("登陆失敗");
		}
	}

	public void delete() {
		service.deleteById(getParaToInt());
		redirect("/blog");
	}
}


