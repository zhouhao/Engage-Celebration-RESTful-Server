package me.hzhou.validator;

import me.hzhou.model.User;

import com.jfinal.core.Controller;
import com.jfinal.kit.StringKit;
import com.jfinal.validate.Validator;

/**
 * Created with IntelliJ IDEA.
 * Author: StevenChow
 * Date: 13-5-3
 */
public class RegistValidator extends Validator {
    @Override
    protected void validate(Controller c) {
        validateEmail("user.email", "emailMsg", "Email format error");
        validateRegex("user.username", "[a-zA-Z0-9_]{2,8}", "usernameMsg", "The length of user name should be 2~8, and it can conly contain numbers, alphabets, and _.");
        //validateString("user.headImg", false, 0, 120, "headImgMsg", "长度不能超过120");
        String email = c.getPara("user.email");
        if(StringKit.notBlank(email) && User.dao.containEmailExceptThis(c.getParaToInt("user.id"), email)){
            addError("emailMsg", "This email has been registered!");
        }
        String username = c.getPara("user.username");
        if(StringKit.notBlank(username) && User.dao.containUsernameExceptThis(c.getParaToInt("user.id"), username)){
            addError("usernameMsg", "This user name has been used by others!");
        }
    }

    @Override
    protected void handleError(Controller c) {
        c.keepModel(User.class);
        c.render("/user/regist.html");
    }
}
