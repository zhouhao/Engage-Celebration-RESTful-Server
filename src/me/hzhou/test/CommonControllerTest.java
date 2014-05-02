package me.hzhou.test;

import org.junit.Test;

import me.hzhou.config.MyConfig;
import me.hzhou.ext.jfinal.test.ControllerTestCase;

public class CommonControllerTest extends ControllerTestCase<MyConfig> {

	@Test
	public void testLogin() {
		String url = "/login";
	    use(url).invoke();
	}

}
