package ca.chynes_b51_final;
/*
 * Author: Casey Hynes
 * Date: Dec. 15, 2014
 * 
 * NavItem class is used to make each navigation item within the
 * navigation drawer an object with a title, an image, and the boolean value
 * of header to tell the ListView Adapter to make the Object a Header in the
 * Navigation Drawer.
 */
public class NavItem {
	
	private String Title;
	private boolean Header;
	private int ImageRes;
	
	public NavItem(String t, boolean h){
		this.Title = t;
		this.Header = h;
	}
	
	public NavItem(String t, boolean h, int i){
		this.Title = t;
		this.Header = h;
		this.ImageRes = i;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public boolean isHeader() {
		return Header;
	}

	public void setHeader(boolean header) {
		Header = header;
	}

	public int getImageRes() {
		return ImageRes;
	}

	public void setImageRes(int imageRes) {
		ImageRes = imageRes;
	}
	
	
}
