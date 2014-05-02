package me.hzhou.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

import me.hzhou.model.User;

public class LoginInterceptor implements Interceptor {

	@Override
    public void intercept(ActionInvocation ai) {
        Controller controller = ai.getController();
        if(controller.getSessionAttr("user") != null){
        	int userId = (Integer)controller.getSessionAttr("userID");
        	controller.setAttr("userName", User.dao.getUserNameById(userId));
            ai.invoke();
        }else{
            controller.setAttr("msg", "Please Login! :-)");
            controller.redirect("/login");
        }
    }

}
