package ca.chynes_b51_final;

import java.util.ArrayList;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
    	
    	if(position == 2){
    		Fragment_ViewTasks vt = new Fragment_ViewTasks();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, vt)
                    .commit();
            mTitle = getString(R.string.title_section2);
    	}else if(position == 3){
    		Fragment_CreateTask ct = new Fragment_CreateTask();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, ct, "CreateTask")
                    .commit();
            mTitle = getString(R.string.title_section3);
    	}else if(position == 4){
    		Fragment_Achievements achieve = new Fragment_Achievements();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, achieve)
                    .commit();
            mTitle = getString(R.string.title_section4);
    	}else if(position == 6){
    		Fragment_About about = new Fragment_About();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, about)
                    .commit();
            mTitle = getString(R.string.title_section5);
    	}else if(position == 7){
    		Fragment_Help help = new Fragment_Help();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, help)
                    .commit();
            mTitle = getString(R.string.title_section6);
    	}else{
	        // update the main content by replacing fragments
	        FragmentManager fragmentManager = getFragmentManager();
	        fragmentManager.beginTransaction()
	                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
	                .commit();
    	}
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
            case 6:
                mTitle = getString(R.string.title_section6);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_create) {
    		Fragment_CreateTask ct = new Fragment_CreateTask();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, ct)
                    .commit();
            ActionBar actionBar = getActionBar();
            actionBar.setTitle(getString(R.string.title_section3));
        }else if(id == R.id.action_achievements){
    		Fragment_Achievements a = new Fragment_Achievements();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, a)
                    .commit();
            ActionBar actionBar = getActionBar();
            actionBar.setTitle(getString(R.string.title_section4));
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

    	public static final String MY_PREFS = "UserPrefs";
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            
            // Immediately close Navigation Drawer on Open
            DrawerLayout dl = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
            dl.closeDrawer(Gravity.LEFT);
            
            // Check Database for Users
            // If there are no users then display the Register Activity
            DBHelper db = new DBHelper(getActivity().getApplicationContext());
            if(db.getUserCount() == 0){
            	Intent registerUser = new Intent(getActivity().getApplicationContext(), RegisterActivity.class);
            	startActivityForResult(registerUser, 1);
            }
            
            // Display the User Data
            displayUserData(rootView);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
        
        /**
         * Gathers the User Name, Level, and Most Recent Tasks for the User
         * from the database and displays them on the main fragment.
         * Will also only get the top 3 most recent tasks.
         * @param v : The Fragments Inflated View
         */
        private void displayUserData(View v){
        	DBHelper db = new DBHelper(getActivity().getApplicationContext());
        	User user = db.getUser(0);
        	
        	// Set Progress Based on level
        	ProgressBar pbCurLevel = (ProgressBar) v.findViewById(R.id.pbCurLevel);
        	pbCurLevel.setProgress((user.getExperience()%250));
        	
            TextView tvUsername = (TextView) v.findViewById(R.id.tvLevelLbl);
            TextView tvLevel = (TextView) v.findViewById(R.id.tvLevelVal);
            ArrayList<Task> taskList =  db.getMostRecentTasks();
            ListView lvMostRecentTasks = (ListView) v.findViewById(R.id.lvMostRecentTasks);
             
            // We only want the top 3 Most recent tasks.
            int size = 3;
            if(taskList.size() < 3)
            	size = taskList.size();
            ArrayList<Task> mostRecentTasks = new ArrayList<Task>();
            
            for(int i = 0; i < size; ++i){
            	mostRecentTasks.add(taskList.get(i));
            }
            
            // Fill the Most Recent Tasks into a the QuestViewAdapter
            QuestViewAdapter adt = new QuestViewAdapter(getActivity(), mostRecentTasks);
            lvMostRecentTasks.setAdapter(adt);
            lvMostRecentTasks.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					LinearLayout llTask = (LinearLayout) view;
					TextView tvTask = (TextView) llTask.findViewById(R.id.tvQuestName);
					
					// Store Task Prefs
					SharedPreferences options = getActivity().getSharedPreferences(MY_PREFS, 0);
					SharedPreferences.Editor optionsEditor = options.edit();
					optionsEditor.putString("TaskName", tvTask.getText().toString());
					optionsEditor.commit();

					// Display The Task that was Click on.
		    		Fragment_Task task = new Fragment_Task();
		            FragmentManager fragmentManager = getFragmentManager();
		            fragmentManager.beginTransaction()
		                    .replace(R.id.container, task)
		                    .commit();
					
				}
			});
            
            // Display Values in Controls
            tvLevel.setText(String.valueOf(String.valueOf(user.calculateLevel())));
            tvUsername.setText(user.getName()+"'s Level");
        }
        
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
        	// If the Register Request Code was fulfilled and the Result is OK
        	// then proceed
            if (requestCode == 1) {
                if(resultCode == RESULT_OK){
                	// Save the Name from the Returned Bundle, and display Data
                    String name =data.getStringExtra("registerName");
                    displayUserData(getView());
                    
                    // Check Achievement Progress for Registering
					LifeQuest.checkAchievementProgress(getActivity().getApplicationContext(), 1);
                }// if
            }// if
        }
    }

}
