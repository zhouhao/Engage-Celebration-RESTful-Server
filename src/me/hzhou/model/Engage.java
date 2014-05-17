package me.hzhou.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.hzhou.kit.ToolKit;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

@SuppressWarnings("serial")
public class Engage extends Model<Engage> {
	public static final Engage dao = new Engage();
	
	public boolean incZan(String key) {
		return incKeyValue(key);
	}
	
	public boolean incVisit() {
		return incKeyValue("visit");
	}
	
	public boolean addWish(String name, String wishContent) {
		Record wish = new Record().set("name",name).set("wish", wishContent).set("time",ToolKit.getCurrentTime());
		return Db.save("wish", wish);
	}
	
	public List<Record> getWishList() {
		return Db.find("SELECT * FROM wish order by id asc");
	}
	
	public Map<String, Integer> getStat() {
		List<Record> list = Db.find("select * from keyValue");
		Map<String, Integer> hm = new HashMap<String, Integer>();
		for(Record r : list){
			hm.put(r.getStr("itemKey"), r.getInt("itemValue"));
		}
		return hm;
	}
	
	private boolean incKeyValue(String whereField){
		return Db.update("UPDATE keyValue SET itemValue=itemValue+1 WHERE itemKey=?", whereField) > 0 ? true: false;
	}
	
	
	
}
