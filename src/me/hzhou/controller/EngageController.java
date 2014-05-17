package me.hzhou.controller;

import com.jfinal.aop.Before;

import me.hzhou.ext.jfinal.Controller;
import me.hzhou.interceptor.visitInterceptor;
import me.hzhou.model.Engage;

@Before(visitInterceptor.class)
public class EngageController extends Controller {
	
	public void index() {
		renderHtml("Hello I am engaged");
	}
	
	public void haoZan() {
		renderJson(Engage.dao.incZan("haoZan"));
	}
	
	public void shanZan() {
		renderJson(Engage.dao.incZan("shanZan"));
	}
	
	public void addWish() {
		String name = getPara("name");
		String wish = getPara("wish");
		renderJson(Engage.dao.addWish(name, wish));
	}
	
	public void wishList() {
		renderJson(Engage.dao.getWishList());
	}
}
