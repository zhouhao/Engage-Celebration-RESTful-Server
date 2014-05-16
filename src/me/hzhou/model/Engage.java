package me.hzhou.model;

import java.util.List;

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
		return Db.find("select * from wish");
	}
	
	public List<Record> getStat() {
		return Db.find("select * from keyValue");
	}
	
	private boolean incKeyValue(String whereField){
		return Db.update("UPDATE keyValue SET itemValue=itemValue+1 WHERE itemKey=?", whereField) > 0 ? true: false;
	}
	
	
	
}
