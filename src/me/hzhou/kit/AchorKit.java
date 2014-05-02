package me.hzhou.kit;

public class AchorKit {
	private String menuName = "";
	private String menuUrl = "";
	
	public AchorKit(String _menuName, String _menuUrl) {
		this.menuName = _menuName;
		this.menuUrl = _menuUrl;
	}
	
	public AchorKit(String _menuName, String _menuUrl, String _baseUrl) {
		this.menuName = _menuName;
		this.menuUrl = _baseUrl + _menuUrl;
	}
	
	public String getName() {
		return this.menuName;
	}
	
	public String getUrl() {
		return this.menuUrl;
	}
	
	public String toString(){
		String str = menuName.toLowerCase().replace(" ", "-");
		return "<a href='"+ this.menuUrl+"' class='list-group-item "+str+"'>" +this.menuName+"</a>";
	}
	
	public String toHref(){
		return this.toString();
	}
}
