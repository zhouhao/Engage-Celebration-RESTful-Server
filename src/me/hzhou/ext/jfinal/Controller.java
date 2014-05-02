package me.hzhou.ext.jfinal;

import me.hzhou.kit.ToolKit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: iver
 * Date: 13-10-11
 */
public class Controller extends com.jfinal.core.Controller{
	
    public Map<String, Object> getParaMap(String... paras) {
        Map<String, Object> parasMap = new HashMap<String, Object>();
        for(String para : paras){
            parasMap.put(para, super.getPara(para));
        }
        return parasMap;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public <M extends Model> M getModel(Class<M> modelClass, String... paras){
        Model<M> model = super.getModel(modelClass);
        String[] paraArray = new String[paras.length];
        for (int i = 0; i < paras.length; i++) {
            paraArray[i] = paras[i].substring(paras[i].indexOf(".") + 1, paras[i].length());
        }
        List<String> diffList = ToolKit.getDifference(paraArray, model.getAttrNames());
        for (String diff : diffList) {
            model.set(diff, null);
        }
        return (M)model;
    }
}
