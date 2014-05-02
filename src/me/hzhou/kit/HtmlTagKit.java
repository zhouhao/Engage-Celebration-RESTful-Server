package me.hzhou.kit;

import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Model;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * Created with IntelliJ IDEA.
 * Author: StevenChow
 * Date: 13-4-4
 */
public class HtmlTagKit {
    @SuppressWarnings("rawtypes")
	public static void processHtmlSpecialTag(Model model, String... attrNames){
        for (String attrName : attrNames) {
            String content = model.getStr(attrName);
            model.set(attrName, processHtmlSpecialTag(content));
        }
    }

    public static String processHtmlSpecialTag(String content){
        if(StringKit.notBlank(content)){
            return content.trim().replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
	public static void processHtmlXSSTag(Model model, String... attrNames){
        for (String attrName : attrNames) {
            String content = model.getStr(attrName);
            if(StringKit.notBlank(content)){
                String temp = Jsoup.clean(content, Whitelist.basicWithImages().addTags("embed"));
                model.set(attrName, temp);
            }
        }
    }
}
