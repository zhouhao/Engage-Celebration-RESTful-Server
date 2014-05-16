package me.hzhou.controller;

import me.hzhou.model.User;

import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;

import me.hzhou.ext.jfinal.Controller;
import me.hzhou.interceptor.LoginInterceptor;

/**
 * UserController
 */
//@Before(LoginInterceptor.class)
public class UserController extends Controller {
	
	
	/**
	 * index page
	 * Display profile page for different roles of users
	 */
	public void index() {
		renderHtml("This is Index Page");
	}
/*	
	@ActionKey("/logout")
    public void logout(){
        removeSessionAttr("user");
        removeSessionAttr("userID");
        removeSessionAttr("roleID");
        redirect("/");
    }
	
	@ActionKey("/about")
	public void about(){
		render("/common/about.html");
	}
	
	@ActionKey("/contact")
	public void contact() {
		render("/common/contact.html");
	}
	
	@ActionKey("/profile")
    public void profile(){
        redirect("/user/edit");
    }
*/
}


