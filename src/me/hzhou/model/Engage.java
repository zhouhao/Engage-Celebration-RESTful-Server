package me.hzhou.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class Engage extends Model<Engage> {
	public static final Engage dao = new Engage();
	
	public boolean incZan(String key) {
		return incKeyValue(key);
	}
	
	public boolean incVisit() {
		return incKeyValue("visit");
	}
	
	private boolean incKeyValue(String whereField){
		return Db.update("UPDATE keyValue SET itemValue=itemValue+1 WHERE itemKey=?", whereField) > 0 ? true: false;
	}
}
