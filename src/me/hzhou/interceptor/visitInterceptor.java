package me.hzhou.interceptor;

import me.hzhou.model.Engage;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

public class visitInterceptor implements Interceptor {

	@Override
    public void intercept(ActionInvocation ai) {
			Engage.dao.incVisit();
            ai.invoke();
    }

}
