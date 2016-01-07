package ca.chynes_b51_final;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/*
 * Name: Casey Hynes
 * Date: Dec. 15, 2014
 * 
 * QuestViewAdapter is a ListAdapter that allows the View Quests
 * listview to show the duration, quest name, and the XP given per each task.
 */
public class QuestViewAdapter extends BaseAdapter{

	public ArrayList<Task> tasks;
    Activity act;
 
    public QuestViewAdapter(Activity a, ArrayList<Task> t) {
        super();
        this.act = a;
        this.tasks = t;
    }
    
	@Override
	public int getCount() {
		return tasks.size();
	}

	@Override
	public Object getItem(int position) {
		return tasks.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	// Creates a Class replicating the XML Views
	private class QuestView{
		TextView tvQuestDuration;
		TextView tvQuestName;
		TextView tvQuestExperience;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		QuestView views;
        LayoutInflater inflater =  act.getLayoutInflater();
        
        if (convertView == null)
        {
        	// Inflate the View with my QuestView XML File
            convertView = inflater.inflate(R.layout.questview_layout, null);
            views = new QuestView();
            
            // Find the Views inside the XML
            views.tvQuestDuration = (TextView) convertView.findViewById(R.id.tvQuestDuration);
            views.tvQuestName = (TextView) convertView.findViewById(R.id.tvQuestName);
            views.tvQuestExperience = (TextView) convertView.findViewById(R.id.tvQuestExperience);
            convertView.setTag(views);
        }
        else
        {
        	views = (QuestView) convertView.getTag();
        }

        // Populate the Views with the current Task information
        Task task = tasks.get(position);
        views.tvQuestDuration.setText(task.getDuration()+" hour(s)");
        views.tvQuestName.setText(task.getName());
        views.tvQuestExperience.setText(task.getExperience()+" XP");

        // If the Position is Odd turn the background Blue and color White
        // Else keep it normal
        if(position % 2 == 1){
        	convertView.setBackgroundResource(R.color.blue);
        	views.tvQuestDuration.setTextColor(Color.WHITE);
        	views.tvQuestName.setTextColor(Color.WHITE);
        	views.tvQuestExperience.setTextColor(Color.WHITE);
        }else{
        	convertView.setBackgroundResource(android.R.color.transparent);
        	views.tvQuestDuration.setTextColor(Color.BLACK);
        	views.tvQuestName.setTextColor(Color.BLACK);
        	views.tvQuestExperience.setTextColor(Color.BLACK);
        }
        
        return convertView;
	}

}
