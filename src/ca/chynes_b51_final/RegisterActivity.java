package ca.chynes_b51_final;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/*
 * Author: Casey Hynes
 * Date: Dec. 15, 2014
 * 
 * Desc: Register Activity is the first Activity the User gets to when they
 * first sign into the app. They will have a mandatory check to make sure they
 * sign into the system. To Prevent the User from pressing the back button to navigate 
 * passed the Register Activity, the back button is disabled.
 */
public class RegisterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		// Create SQLite Object
		final DBHelper db = new DBHelper(this);
		
		// Remove Action Bar
		ActionBar ab = getActionBar();
		ab.hide();

		Button btnRegister = (Button) findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Get Controls
				EditText etName = (EditText) findViewById(R.id.etUsername);
				Spinner spnYearBorn = (Spinner) findViewById(R.id.spnYearBorn);
				TextView tvYear = (TextView) spnYearBorn.getSelectedView();
				
				// If the Controls have been filled out then proceed
				if(spnYearBorn.getSelectedItemPosition() > 0 && etName.getText().length() > 0){
					
					// Create a new User with their first Task and add to the Database
					User u = new User(0, etName.getText().toString(), Integer.parseInt(tvYear.getText().toString()), 0);
		            Task firstTask = new Task(0, 0, "Welcome to LifeQuest", null, "LifeQuest", 1, 50);
					db.addUser(u);
		            db.addTask(firstTask);
		            
		            // Create all Achievements for the User
		            LifeQuest.setupAchievements(getApplicationContext(), u.getIDUser());
		            
		            // Return the result to the calling Activity and close this Activity
					Intent register = new Intent();
					register.putExtra("registerName", u.getName());
					setResult(RESULT_OK, register);
					finish();
				}
			}
		});
		
		// Give the Spinner a default value and a hundred years from the current year.
		Spinner spnYears = (Spinner) findViewById(R.id.spnYearBorn);
		String[] years = get100Years();
		ArrayAdapter<String> adpt = new ArrayAdapter<String>(this, R.layout.spinner_years_layout, years);
		spnYears.setAdapter(adpt);
	}
	
	/**
	 * Gets the passed 100 years from the current year along with a default select
	 * year value at the beginning of the array.
	 * @return years
	 */
	private String[] get100Years(){
		String[] years = new String[100];
		int curYear = Calendar.getInstance().get(Calendar.YEAR);
		years[0] = "-- Select Year --";
		for(int i = 1; i < 100; ++i){
			years[i] = String.valueOf(curYear--);
		}
		return years;
	}
	
	/**
	 * Overrides the back button to do nothing
	 */
	@Override
	public void onBackPressed() {
	}
	
	
}
