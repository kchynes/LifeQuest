package ca.chynes_b51_final;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ca.chynes_b51_final.MainActivity.PlaceholderFragment;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_CreateTask extends Fragment {

	// Static Variable to Help Store State in Fragments
	private static String mTaskName = "";
	private static int mTaskDuration = 0;
	private static String mTaskXP = "...";
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_createtask, container, false);

        // Get Button Views
        Button btnFinish = (Button) view.findViewById(R.id.btnCreateFinishTask);
        Button btnCancel = (Button) view.findViewById(R.id.btnCreateCancelTask);
        
        btnFinish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Get all Controls
				EditText etName = (EditText) view.findViewById(R.id.etTaskName);
				TextView tvLocation = (TextView) view.findViewById(R.id.tvTaskLocation);
				Spinner spnDuration = (Spinner) view.findViewById(R.id.spnTaskDuration);
				TextView tvDuration = (TextView) spnDuration.getSelectedView();
				TextView tvXP = (TextView) view.findViewById(R.id.tvCalcExperience);
				ImageView ivQuestImage = (ImageView) getActivity().findViewById(R.id.ivCreateTaskImage);
        	    
				// If Valid
				if(spnDuration.getSelectedItemPosition() > 0 && !etName.getText().toString().equals("")){
					// Create new Task from Form
					Task t = new Task(0,0,etName.getText().toString(),
										String.valueOf(ivQuestImage.getTag()), tvLocation.getText().toString(), 
										Integer.parseInt(tvDuration.getText().toString()),
										Integer.parseInt(tvXP.getText().toString()));
					
					// Get Database and Add Task if Task if Unique
					DBHelper db = new DBHelper(getActivity().getApplicationContext());
					boolean isUnique = db.addTask(t);
					
					// Unique Check Achievements
	        		if(isUnique){
						// Check Created Quest Achievements
						LifeQuest.checkAchievementProgress(getActivity().getApplicationContext(), 2);
						LifeQuest.checkAchievementProgress(getActivity().getApplicationContext(), 3);
						LifeQuest.checkAchievementProgress(getActivity().getApplicationContext(), 4);
						
						displayHomeFragment();
	        		}else{
	        			// Notify User the task already exists
	        			Toast.makeText(getActivity().getApplicationContext(),
	        					"Quest already exist!", Toast.LENGTH_LONG).show();
	        			etName.setText("");
	        		}// else
				}else{
					// Display Notification of missing information
					String errMsg = "Whoops! We\'re missing the Quest ";
					if(spnDuration.getSelectedItemPosition() == 0)
						errMsg += "Duration!";
					else
						errMsg += "Name!";
        			Toast.makeText(getActivity().getApplicationContext(), errMsg, Toast.LENGTH_LONG).show();
				}
			}
		});
        btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				displayHomeFragment();
			}
		});
        
        // Get new image
        ImageView ivQuestImage = (ImageView) view.findViewById(R.id.ivCreateTaskImage);
        ivQuestImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Call Device Gallery Intent for Result
				Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1337);
				
			}
		});
        
        // Get GEO Location
        displayLocation(view);
        // Display Task Controls
        setUpCreateTaskControls(view);
        // Restore State if Available
        restoreQuestState(view);
        
        return view;
    }
    
    /**
     * Gets the GeoLocation and Displays it in the TextView once it has gathered
     * the closest city to the users location
     * @param view - Fragment View
     */
    private void displayLocation(final View view){
    	// Display Default Searching Value before Location is Received
		final TextView tvLocation = (TextView) view.findViewById(R.id.tvTaskLocation);
		tvLocation.setText("Searching...");

    	final LocationListener listener = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				try {
					// Get Users Lat and Long and get the closest city via the Android Address Class
					Geocoder gcd = new Geocoder(getActivity().getBaseContext(), Locale.getDefault());
					List<Address> addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
					tvLocation.setText(addresses.get(0).getLocality());
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {}

			@Override
			public void onProviderEnabled(String provider) {}

			@Override
			public void onProviderDisabled(String provider) {}
        };
        
        // Implement Location manager via Network or GPS depending on Device
        // Then use listener applied above
        LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
        }
    }
    
    /**
     * Display and Initialize all Form Controls
     * @param view - Fragment View
     */
    private void setUpCreateTaskControls(View view){
    	// Get User
        DBHelper db = new DBHelper(getActivity().getApplicationContext());
        final User user = db.getUser(0);
        
        // Get Controls
        final Spinner spnDuration = (Spinner) view.findViewById(R.id.spnTaskDuration);
        final TextView tvCalcExperience = (TextView) view.findViewById(R.id.tvCalcExperience);
        final EditText etTaskName = (EditText) view.findViewById(R.id.etTaskName);
        
        // Fill Duration with available Hours
        String[] hours = new String[25];
        hours[0] = "-- Select Hours --";
        for(int i = 1; i < 25; ++i){
        	hours[i] = String.valueOf(i);
        }
        ArrayAdapter<String> adt = new ArrayAdapter<String>(getActivity().getBaseContext(),
        													R.layout.spinner_years_layout,
        													hours);
        spnDuration.setAdapter(adt);
        spnDuration.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(position > 0){
					// Calculate Experience
					int year = Calendar.getInstance().get(Calendar.YEAR);
					int age = year-user.getYearBorn();
					TextView tvDuration = (TextView) spnDuration.getSelectedView();
					double duration = Double.parseDouble(tvDuration.getText().toString())*10;
					double experience = duration+(((double)age/100)*duration);
					int roundedExperience = (int) experience;
					tvCalcExperience.setText(String.valueOf(roundedExperience));
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
    }
    
    /**
     * Display Home Fragment
     */
    private void displayHomeFragment(){

    	// Clear Quest Save State from Static Vars
    	clearQuestState();
    	
    	// Reset Action Bar
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Home");
        
        // Replace Fragment with Home Fragment
		FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(1))
                .commit();
    }
    
    @Override
    public void onSaveInstanceState(Bundle data){
        super.onSaveInstanceState(data);
        // Get form Views
        Spinner spnDuration = (Spinner) getActivity().findViewById(R.id.spnTaskDuration);
        TextView tvCalcExperience = (TextView) getActivity().findViewById(R.id.tvCalcExperience);
        EditText etTaskName = (EditText) getActivity().findViewById(R.id.etTaskName);
        
        // Save View Values into Static Modular Variables
        mTaskName = etTaskName.getText().toString();
    	mTaskDuration = spnDuration.getSelectedItemPosition();
    	mTaskXP = tvCalcExperience.getText().toString();
    }
    
    /**
     * Restores the state back to the appropriate controls via the static variables.
     * @param v - Fragment View
     */
    private void restoreQuestState(View v){

    	// Get Controls
        Spinner spnDuration = (Spinner) v.findViewById(R.id.spnTaskDuration);
        TextView tvCalcExperience = (TextView) v.findViewById(R.id.tvCalcExperience);
        EditText etTaskName = (EditText) v.findViewById(R.id.etTaskName);
        
        // Populate Controls
        spnDuration.setSelection(mTaskDuration);
        etTaskName.setText(mTaskName);
        tvCalcExperience.setText(mTaskXP);
    }
    
    /**
     * Clears the Static Variables to restart state
     */
    private void clearQuestState(){
        mTaskName = "";
    	mTaskDuration = 0;
    	mTaskXP = "...";
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	// If Gallery Request is Good and Result is OK then proceed
        if (requestCode == 1337) {
            if(resultCode == Activity.RESULT_OK){
            	// Get Image URI from returned data
                Uri questImage = data.getData();
                // Get image view from Fragment
        	    ImageView ivQuestImage = (ImageView) getActivity().findViewById(R.id.ivCreateTaskImage);
        	    
        	    // Set URI and Tag to the REAL parse URI
        	    ivQuestImage.setImageURI(Uri.parse(getRealPathFromURI(questImage)));
        	    ivQuestImage.setTag(Uri.parse(getRealPathFromURI(questImage)));
            	
            }
        }
    }
    
    /**
     * This function gets the true path from an image uri saved on your device.
     * Could not figure this out for myself so the credit for this function goes to
     * the Stack OverFlow user: bluebrain
     * URL: http://stackoverflow.com/a/20470572/2203488
     * 
     * All this function does is essentially goes through a query of my image information via my URI
     * and then gets the first row in the cursor to give me my Image Path Name. It has to do some substringing
     * first because of an issue with KitKat 4.4
     * @param questImageURI
     * @return path
     */
    public String getRealPathFromURI(Uri questImageURI) {
    	
    	// Get the Quest Cursor from the URI and Substring the value to get the KitKat Image ID
    	Cursor questCurs = getActivity().getContentResolver().query(questImageURI, null, null, null, null);
    	questCurs.moveToFirst();
    	String img_id = questCurs.getString(0);
    	img_id = img_id.substring(img_id.lastIndexOf(":")+1);
    	questCurs.close();

    	// Get the new Cursor from the new Image ID to work with KITKAT
    	questCurs = getActivity().getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
    														null, MediaStore.Images.Media._ID + " = ? ", new String[]{img_id}, null);
    	questCurs.moveToFirst();
    	
    	// Get Image Path from Cursor
	    String path = questCurs.getString(questCurs.getColumnIndex(MediaStore.Images.Media.DATA));
	    questCurs.close();

	   return path;
	}
}