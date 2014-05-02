package me.hzhou.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * Created with IntelliJ IDEA.
 * Author: StevenChow
 * Date: 13-5-9
 */
public class ReportValidator extends Validator {
    @Override
    protected void validate(Controller c) {
        validateString("report.content", 1, 8000, "msg", "不能为空且长度不超过8000");
    }

    @Override
    protected void handleError(Controller c) {
        c.renderError(500);
    }
}
