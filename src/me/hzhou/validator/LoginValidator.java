package me.hzhou.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
/**
 * Author: Hao Zhou
 * Date: 04-02-2014
 */
public class LoginValidator extends Validator{
    @Override
    protected void validate(Controller c) {
        validateEmail("email", "msg", "Email format Error");
        validateRequired("password", "msg", "Password cannot be null");
    }

    @Override
    protected void handleError(Controller c) {
        c.keepPara("email");
        c.render("/user/login.html");
    }
}
