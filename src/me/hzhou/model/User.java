package me.hzhou.model;

import java.util.List;

import me.hzhou.config.Const;
import me.hzhou.ext.jfinal.Model;
import me.hzhou.kit.HtmlTagKit;
import me.hzhou.kit.SecurityKit;
import me.hzhou.kit.SendMailKit;
import me.hzhou.kit.ToolKit;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * Author: Hao Zhou
 * Date: 04-02-2014
 * User model.
 */
@SuppressWarnings("serial")
public class User extends Model<User> {
	
	public static final User dao = new User();
	private static final String USER_CACHE = "user";
	
	public User() {
		super(USER_CACHE);
	}

	/* get */
	
	public String getUserNameById(int userId) {
		User user = dao.findFirst("select id, username, firstname, lastname from user where id=?", userId);
		return user.getStr("firstname")+" "+user.getStr("lastname");
	}
	
	public User getByEmail(String email){
		User user = dao.findFirst("select id, username, email, role_id from user where email=?", email);
		return user;
	}
	
	public User getByHashCode(String hashString){
		User user = dao.findFirst("select id, username, email, role_id from user where hash_password_reset=?", hashString);
		return user;
	}
	
	public User getByEmailAndPassword(String email, String password){
		password =  SecurityKit.encryption(password);
		User user = dao.findFirst("select id, username, email, role_id from user where email=? and password=?", email, password);
		// Update recent login time
		if(user != null) user.set("last_login_time", ToolKit.getCurrentTime()).update();
		return user;
	}
	
	public List<User> getSuperviosrs() {
		return dao.find("select id, username, firstname, lastname, email, role_id from user where role_id = 2");
	}
	
	
	/* other */
	public boolean mySave() {
		if(this.getStr("email") != null && !this.getStr("email").trim().equals("")){
			HtmlTagKit.processHtmlSpecialTag(this, "username");
			String rawPass = SecurityKit.randomString();
			String password = SecurityKit.encryption(rawPass);
			this.set("password", password);
			return this.save();
		} else {
			return false;
		}
	}
	
	public boolean passwordReset(int userID) {
		String password = this.getStr("password");
		password = SecurityKit.encryption(password);
		this.set("password", password);
		this.set("id", userID);
		return this.update();

	}
	
	public void myUpdate() {
		HtmlTagKit.processHtmlSpecialTag(this, "username", "firstname", "lastname");
		this.update();
		removeThisCache(this.getInt("id"));
	}

	public boolean containEmail(String email) {
		return dao.findFirst("select email from user where email=? limit 1", email) != null;
	}

	public boolean containUsername(String username) {
		return dao.findFirst("select username from user where username=? limit 1", username) != null;
	}

	public boolean containEmailExceptThis(int userID, String email) {
		return dao.findFirst("select email from user where email=? and id!=? limit 1", email, userID) != null;
	}
	
	public boolean containUsernameExceptThis(int userID, String username) {
		return dao.findFirst("select username from user where username=? and id!=? limit 1", username, userID) != null;
	}



	/* cache */
	private void removeThisCache(int id){
		CacheKit.remove(USER_CACHE, id);
	}
	


}
