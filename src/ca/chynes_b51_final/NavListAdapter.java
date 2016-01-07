package ca.chynes_b51_final;

import java.util.ArrayList;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/*
 * Author: Casey Hynes
 * Date: Dec. 15, 2014
 * 
 * The NavListAdapter is a ListView adapter that overrides the Navigation Drawer
 * to include headings, images and the item names.
 */
public class NavListAdapter extends BaseAdapter {

	public ArrayList<NavItem> navItems;
    Activity act;
 
    public NavListAdapter(Activity a, ArrayList<NavItem> n) {
        super();
        this.act = a;
        this.navItems = n;
    }
    
	@Override
	public int getCount() {
		return navItems.size();
	}

	@Override
	public Object getItem(int position) {
		return navItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	// Replicates Each Navigation Item XML Views
	private class NavItemView{
		TextView tvNavItem;
		ImageView ivNavImage;
	}
	
	// Replicates Each Nagivation Header XML Views
	private class NavHeaderView{
		TextView tvHeaderItem;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NavItemView navItemViews = null;
		NavHeaderView navHeaderViews;
        LayoutInflater inflater =  act.getLayoutInflater();
		
        // Get the Current Navigation Object
		NavItem nav = navItems.get(position);
		
		if(convertView == null){
			// If the Navigation Obj is set to a Header then populate the Header XML
			if(nav.isHeader()){
	
				// Inflate and Find views within XML
	            convertView = inflater.inflate(R.layout.navheader_layout, null);
	            navHeaderViews = new NavHeaderView();
	            navHeaderViews.tvHeaderItem = (TextView) convertView.findViewById(R.id.tvHeader);
				navHeaderViews.tvHeaderItem.setText(nav.getTitle());

	            convertView.setTag(navHeaderViews);
	            convertView.setEnabled(false);
	            convertView.setOnClickListener(null);
			}else{
				// Else populate the Nav Item XML
				
				//Inflate and Find views within XML
	            convertView = inflater.inflate(R.layout.navlist_layout, null);
	            navItemViews = new NavItemView();
	            navItemViews.tvNavItem = (TextView) convertView.findViewById(R.id.tvNavItem);
	            navItemViews.tvNavItem.setText(nav.getTitle());
	            navItemViews.ivNavImage = (ImageView) convertView.findViewById(R.id.ivNavImage);
	            navItemViews.ivNavImage.setImageResource(nav.getImageRes());

	            convertView.setTag(navItemViews);
			}
		}else{
			if(nav.isHeader())
				navHeaderViews = (NavHeaderView) convertView.getTag();
			else
				navItemViews = (NavItemView) convertView.getTag();
				
		}
		
		return convertView;
		
		
	}

}
