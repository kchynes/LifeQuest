package ca.chynes_b51_final;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_ViewTasks extends Fragment {
	public static final String MY_PREFS = "UserPrefs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewtasks, container, false);
        
        // Get SQLite and then get User and their Tasks
        final DBHelper db = new DBHelper(getActivity().getApplicationContext());
        User user = db.getUser(0);
        TextView tvUsername = (TextView) view.findViewById(R.id.tvViewQuestTitle);
        
        // Get Tasks and Fill into Customer Adapter
        ArrayList<Task> taskList =  db.getTasks();
        ListView lvTasks = (ListView) view.findViewById(R.id.lvViewTasks);
        QuestViewAdapter qapt = new QuestViewAdapter(getActivity(), taskList);
        lvTasks.setAdapter(qapt);
        tvUsername.setText(user.getName()+"'s Quests");
        lvTasks.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LinearLayout llTask = (LinearLayout) view;
				TextView tvTask = (TextView) llTask.findViewById(R.id.tvQuestName);
				
				// Store Prefs
				SharedPreferences options = getActivity().getSharedPreferences(MY_PREFS, 0);
				SharedPreferences.Editor optionsEditor = options.edit();
				optionsEditor.putString("TaskName", tvTask.getText().toString());
				optionsEditor.commit();

				// Bring up the Task Fragment
	    		Fragment_Task task = new Fragment_Task();
	            FragmentManager fragmentManager = getFragmentManager();
	            fragmentManager.beginTransaction()
	                    .replace(R.id.container, task)
	                    .commit();
			}
		});
        return view;
    }
}
