
package ca.chynes_b51_final;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
/*
 * Author: Casey Hynes
 * Date: Dec. 15, 2014
 * 
 * AchievementViewAdapter is a Customer ListViewAdapter that allows the ability
 * to display all of the most important parts of the achievement in one list item.
 */
public class AchievementViewAdapter extends BaseAdapter{

	public ArrayList<Achievement> achieves;
    Activity act;
 
    public AchievementViewAdapter(Activity a, ArrayList<Achievement> ach) {
        super();
        this.act = a;
        this.achieves = ach;
    }
    
	@Override
	public int getCount() {
		return achieves.size();
	}

	@Override
	public Object getItem(int position) {
		return achieves.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	// Replicated the Achievement XML
	private class AchievementView{
		TextView tvAchievementName;
		TextView tvAchievementDesc;
		ImageView ivAchievementMedal;
		ProgressBar pbAchievementProg;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		AchievementView views;
        LayoutInflater inflater =  act.getLayoutInflater();
        if (convertView == null)
        {
        	// Inflate XML
            convertView = inflater.inflate(R.layout.achievements_layout, null);
            views = new AchievementView();
            
            // Get XML Views
            LinearLayout llAchievements = (LinearLayout) convertView.findViewById(R.id.llAchievements);
            LinearLayout llAchievementData = (LinearLayout) llAchievements.findViewById(R.id.llAchievementData);
            LinearLayout llAchievementInfo = (LinearLayout) llAchievementData.findViewById(R.id.llAchievementInfo);
            views.tvAchievementName = (TextView) llAchievementInfo.findViewById(R.id.tvAchievementName);
            views.tvAchievementDesc = (TextView) llAchievementInfo.findViewById(R.id.tvAchievementDescription);
            views.ivAchievementMedal = (ImageView) llAchievementData.findViewById(R.id.ivMedal);
            views.pbAchievementProg = (ProgressBar) llAchievements.findViewById(R.id.pbAchievementProg);
            convertView.setTag(views);
        }
        else
        {
        	views = (AchievementView) convertView.getTag();
        }

        // Get Current Achievement and Populate Views
        Achievement achievement = achieves.get(position);
        views.tvAchievementName.setText(achievement.getName());
        views.tvAchievementDesc.setText(achievement.getDescription());
        if(achievement.getProgress() >= achievement.getTotal())
        	views.ivAchievementMedal.setImageResource(getMedalResource(achievement.getType()));
        else
        	views.ivAchievementMedal.setImageResource(R.drawable.ic_medal_default);
        views.pbAchievementProg.setMax(achievement.getTotal());
        views.pbAchievementProg.setProgress(achievement.getProgress());

        // If Odd Postion make background blue and text white
        // Else leave it the same
        if(position % 2 == 1){
        	convertView.setBackgroundResource(R.color.blue);
        	views.tvAchievementName.setTextColor(Color.WHITE);
        	views.tvAchievementDesc.setTextColor(Color.WHITE);
        }else{
        	convertView.setBackgroundResource(android.R.color.transparent);
        	views.tvAchievementName.setTextColor(Color.BLACK);
        	views.tvAchievementDesc.setTextColor(Color.BLACK);
        }
        
        return convertView;
	}
	
	/**
	 * Gets the drawable resource depending on the medal type of the
	 * achievement.
	 * @param medal
	 * @return
	 */
	private int getMedalResource(String medal){
		if(medal.equals("Bronze"))
			return R.drawable.ic_medal_bronze;
		else if(medal.equals("Silver"))
			return R.drawable.ic_medal_silver;
		else
			return R.drawable.ic_medal_gold;
	}

}

