package ca.chynes_b51_final;

import java.io.File;

import ca.chynes_b51_final.MainActivity.PlaceholderFragment;
import ca.chynes_b51_final.NavigationDrawerFragment.NavigationDrawerCallbacks;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_Task extends Fragment {
	
	public static final String MY_PREFS = "UserPrefs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_task, container, false);
        
        // Get Shared Prefs
        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS, 0);
        String taskName = prefs.getString("TaskName", "");
        
        // Get SQLite DB and Task
        final DBHelper db = new DBHelper(getActivity().getApplicationContext());
        final Task t = db.getTask(taskName);
        
        // Display Task Information
        displayTaskInformation(t, view);

        Button btnFinish = (Button) view.findViewById(R.id.btnFinishTask);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancelTask);
        
        btnFinish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Check to see if the User has Leveled
				LifeQuest.checkLevelProgress(getActivity().getApplicationContext(), t);
				
				// Check for Hour Achievement
		        TextView tvDuration= (TextView) view.findViewById(R.id.tvDuration);
		        String duration = tvDuration.getText().toString().substring(0, tvDuration.getText().toString().indexOf(" hours"));
		        LifeQuest.checkAchievementProgressWithAddition(getActivity().getApplicationContext(), 8, Integer.parseInt(duration));
		        
		        // Check for Finished Quest Achievements
				LifeQuest.checkAchievementProgress(getActivity().getApplicationContext(), 5);
				LifeQuest.checkAchievementProgress(getActivity().getApplicationContext(), 6);
				LifeQuest.checkAchievementProgress(getActivity().getApplicationContext(), 7);
		        
				// Return to Home Screen
				displayHomeFragment(t);
			}
		});
        btnCancel.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {

				// Check for Quit Achievement
				LifeQuest.checkAchievementProgress(getActivity().getApplicationContext(), 13);
				
				// Redisplay Home Screen
				displayHomeFragment(t);
			}
		});
        
        return view;
    }
    
    private void displayTaskInformation(Task t, View view){
    	// Get Fragment Controls
        TextView tvTaskTitle = (TextView) view.findViewById(R.id.tvViewQuestTitle);
        TextView tvLocation = (TextView) view.findViewById(R.id.tvLocation);
        TextView tvDuration= (TextView) view.findViewById(R.id.tvDuration);
        TextView tvExperience = (TextView) view.findViewById(R.id.tvCalcExperience);
        ImageView ivQuestImg = (ImageView) view.findViewById(R.id.ivTaskImage);
        
        // If Task Name is too big then Hide Characters with ...
        String taskName = t.getName();
        int wordLimit = 18;
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        	wordLimit = 25;
        
        if(taskName.length() > wordLimit)
    		taskName =  taskName.substring(0, wordLimit-1)+"...";
        
        tvTaskTitle.setText(taskName);
        tvLocation.setText(t.getLocation());
        tvDuration.setText(String.valueOf(t.getDuration())+" hours");
        tvExperience.setText(String.valueOf(t.getExperience()));
        
        // Display Image if Available
        if(t.getImage_Ref() != null && !t.getImage_Ref().equals("null")){ 
        	ivQuestImg.setImageURI(Uri.parse(t.getImage_Ref()));
        }
    }
    
    /**
     * Deletes the task viewed, sets the ActionBar back to Home default
     * and returns to the Home Fragment
     * @param t - Task
     */
    private void displayHomeFragment(Task t){
    	
    	// Delete Current Task from DB
    	DBHelper db = new DBHelper(getActivity().getApplicationContext());
		db.deleteTask(t.getIDTask());

		// Reset ActionBar
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Home");
        
        // Return Home
		FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(1))
                .commit();
    }

}
