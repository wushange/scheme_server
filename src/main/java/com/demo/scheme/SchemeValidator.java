package com.demo.scheme;

import com.demo.common.model.Blog;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * BlogValidator.
 */
public class SchemeValidator extends Validator {
	
	protected void validate(Controller controller) {
		validateRequiredString("scheme.title", "titleMsg", "请输入Scheme标题!");
		validateRequiredString("scheme.scheme", "contentMsg", "请输入Scheme内容!");
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(Blog.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/scheme/save"))
			controller.render("add.html");
		else if (actionKey.equals("/scheme/update"))
			controller.render("edit.html");
	}
}
