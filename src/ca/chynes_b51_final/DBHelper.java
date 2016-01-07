package ca.chynes_b51_final;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {

	// Create Database Name
	public static final String DATABASE_NAME = "lifequest.db";

	// Create Database Table Names
	public static final String TABLE_USER = "user";
	public static final String TABLE_ACHIEVEMENT = "achievement";
	public static final String TABLE_TASK = "task";

	// Create User Table Column Names
	public static final String USER_ID = "IDUser";
	public static final String USER_NAME = "Name";
	public static final String USER_YEARBORN = "Yearborn";
	public static final String USER_EXPERIENCE = "Experience";

	// Create Achievement Table Column Names
	public static final String ACHIEVEMENT_ID = "IDAchievement";
	public static final String ACHIEVEMENT_USER = USER_ID;
	public static final String ACHIEVEMENT_NAME = "Achievement_Name";
	public static final String ACHIEVEMENT_DESC = "Achievement_Desc";
	public static final String ACHIEVEMENT_PROGRESS = "Progress";
	public static final String ACHIEVEMENT_TOTAL = "Total";
	public static final String ACHIEVEMENT_TYPE = "Type";
	
	// Create Task Table Column Names
	public static final String TASK_ID = "IDTask";
	public static final String TASK_USER = USER_ID;
	public static final String TASK_NAME = "Task_Name";
	public static final String TASK_IMAGE_REF = "Image_Ref";
	public static final String TASK_LOCATION = "Location";
	public static final String TASK_DURATION = "Duration";
	public static final String TASK_EXPERIENCE = "Task_Experience";
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		// Create User Table
		db.execSQL("CREATE TABLE "+TABLE_USER+" ("+
				USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
				USER_NAME+" TEXT, "+USER_YEARBORN+" INTEGER, "+
				USER_EXPERIENCE+" INTEGER);");
		
		// Create Achievement Table
		db.execSQL("CREATE TABLE "+TABLE_ACHIEVEMENT+" ("+
				ACHIEVEMENT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
				ACHIEVEMENT_USER+" INTEGER, "+ACHIEVEMENT_NAME+" TEXT, "+ACHIEVEMENT_DESC+" TEXT, "+
				ACHIEVEMENT_PROGRESS+" INTEGER, "+ACHIEVEMENT_TOTAL+" INTEGER, "+
				ACHIEVEMENT_TYPE+" TEXT, "+
				" FOREIGN KEY ("+ACHIEVEMENT_USER+") REFERENCES "+TABLE_USER+" ("+USER_ID+"));");
		
		// Create Task Table
		db.execSQL("CREATE TABLE "+TABLE_TASK+" ("+
				TASK_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
				TASK_USER+" INTEGER, "+TASK_NAME+" TEXT, "+
				TASK_IMAGE_REF+" TEXT, "+TASK_LOCATION+" TEXT, "+
				TASK_DURATION+" INTEGER, "+TASK_EXPERIENCE+" INTEGER, "+
				" FOREIGN KEY ("+TASK_USER+") REFERENCES "+TABLE_USER+" ("+USER_ID+"));");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop tables and recreate them.
		db.execSQL("DROP IF TABLE EXISTS "+TABLE_USER);
		db.execSQL("DROP IF TABLE EXISTS "+TABLE_ACHIEVEMENT);
		db.execSQL("DROP IF TABLE EXISTS "+TABLE_TASK);
		
		// Recreate the Database
		onCreate(db);
	}
	
/*
 *  USER FUNCTIONS
 */
	
	/**
	 * Gets the count of Users in the database.
	 * @return User Count.
	 */
	public int getUserCount(){
		SQLiteDatabase db = getReadableDatabase();	
		Cursor curs = db.rawQuery("SELECT "+USER_ID+" "+
								"FROM "+TABLE_USER, null);
		return curs.getCount();
	}
	
	/**
	 * Updates the User via a User object in the Database.
	 * @param u : User Object
	 */
	public void updateUser(User u){
        SQLiteDatabase db = this.getWritableDatabase();
 
        // Put User Object Values into the Database
        ContentValues values = new ContentValues();
        values.put(USER_ID, u.getIDUser());
        values.put(USER_NAME, u.getName());
        values.put(USER_YEARBORN, u.getYearBorn());
        values.put(USER_EXPERIENCE, u.getExperience());
 
        // Update the User Table and Close the Database Connection.
		db.update(TABLE_USER, values, USER_ID+" = ?", new String[]{String.valueOf(u.getIDUser())});
        db.close(); 
	}
	
	/**
	 * Inserts a User via a User object into the Database.
	 * @param u : User Object
	 */
	public void addUser(User u) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        // Put User Object Values into the Database
        ContentValues values = new ContentValues();
        values.put(USER_ID, u.getIDUser());
        values.put(USER_NAME, u.getName());
        values.put(USER_YEARBORN, u.getYearBorn());
        values.put(USER_EXPERIENCE, u.getExperience());
 
        // Inserting User and Close Database Connection
        db.insert(TABLE_USER, null, values);
        db.close();     
    }
	
	/**
	 * Gets the User from the database matching the User ID.
	 * @param id : User ID.
	 * @return newUser: The User Object Matching the ID.
	 */
	public User getUser(int id){
		User newUser = new User();
		SQLiteDatabase db = getReadableDatabase();
		
		// Create Cursor from User Query
		Cursor curs = db.query(TABLE_USER, new String[]{USER_ID, USER_NAME, USER_YEARBORN, USER_EXPERIENCE},
							USER_ID+" = ?", new String[]{String.valueOf(id)}, null, null, null);
		
		// If the Cursor has a first row, then get the User Object Values
		if(curs != null && curs.moveToFirst()){
			newUser.setIDUser(Integer.parseInt(curs.getString(0)));
			newUser.setName(curs.getString(1));
			newUser.setYearBorn(Integer.parseInt(curs.getString(2)));
			newUser.setExperience(Integer.parseInt(curs.getString(3)));
		}
							
							
		return newUser;
	}
	
/*
 *  ACHIEVEMENT FUNCTIONS
 */
	
	/**
	 * Inserts an array of Achievements into the Database at Once.
	 * @param a: Array of Achievement Objects
	 * 
	 */
	public void addAchievements(ArrayList<Achievement> a) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        // Add each Achievement to the Database
        for(int i = 0; i < a.size(); ++i){
	        ContentValues values = new ContentValues();
	        values.put(ACHIEVEMENT_USER, a.get(i).getIDUser());
	        values.put(ACHIEVEMENT_NAME, a.get(i).getName());
	        values.put(ACHIEVEMENT_DESC, a.get(i).getDescription());
	        values.put(ACHIEVEMENT_PROGRESS, a.get(i).getProgress());
	        values.put(ACHIEVEMENT_TOTAL, a.get(i).getTotal());
	        values.put(ACHIEVEMENT_TYPE, a.get(i).getType());
	 
	        // Inserting Task
	        db.insert(TABLE_ACHIEVEMENT, null, values);
        }
        // Close Connection
        db.close();     
    }
	
	/**
	 * Updates an Achievement Object form the Database
	 * @param a
	 */
	public void updateAchievement(Achievement a){
        SQLiteDatabase db = this.getWritableDatabase();
 
        // Store Achievement Values
        ContentValues values = new ContentValues();
        values.put(ACHIEVEMENT_USER, a.getIDUser());
        values.put(ACHIEVEMENT_NAME, a.getName());
        values.put(ACHIEVEMENT_DESC, a.getDescription());
        values.put(ACHIEVEMENT_PROGRESS, a.getProgress());
        values.put(ACHIEVEMENT_TOTAL, a.getTotal());
        values.put(ACHIEVEMENT_TYPE, a.getType());
 
        // Update the Achievement Table and Close Connection
		db.update(TABLE_ACHIEVEMENT, values, ACHIEVEMENT_ID+" = ?", new String[]{String.valueOf(a.getIDAchievement())});
        db.close(); 
	}

	/**
	 * Get the Achievement Object matching the Database ID.
	 * @param id
	 * @return newAchievement : The Achievement Object matching the ID.
	 */
	public Achievement getAchievement(int id){
		Achievement newAchievement = new Achievement();
		SQLiteDatabase db = getReadableDatabase();
		
		// Create Cursor from Achievement Query
		Cursor curs = db.query(TABLE_ACHIEVEMENT, new String[]{ACHIEVEMENT_ID, ACHIEVEMENT_USER, ACHIEVEMENT_NAME, ACHIEVEMENT_DESC, ACHIEVEMENT_PROGRESS, ACHIEVEMENT_TOTAL, ACHIEVEMENT_TYPE},
							ACHIEVEMENT_ID+" = ?", new String[]{String.valueOf(id)}, null, null, null);
		
		// If a row is returned then fill in Achievement Information
		if(curs != null && curs.moveToFirst()){
			newAchievement.setIDAchievement(Integer.parseInt(curs.getString(0)));
			newAchievement.setIDUser(Integer.parseInt(curs.getString(1)));
			newAchievement.setName(curs.getString(2));
			newAchievement.setDescription(curs.getString(3));
			newAchievement.setProgress(Integer.parseInt(curs.getString(4)));
			newAchievement.setTotal(Integer.parseInt(curs.getString(5)));
			newAchievement.setType(curs.getString(6));
		}
							
							
		return newAchievement;
	}
	
	/**
	 * Gets an ArrayList of Achievement Objects in the Database
	 * @return achievements: The List of Achievements
	 */
	public ArrayList<Achievement> getAchievements(){
		ArrayList<Achievement> achievements =  new ArrayList<Achievement>();
		SQLiteDatabase db = getReadableDatabase();
		
		// Create Cursor from Achievement Query
		Cursor curs = db.rawQuery("SELECT "+ACHIEVEMENT_ID+", "+ACHIEVEMENT_USER+", "+
				ACHIEVEMENT_NAME+", "+ACHIEVEMENT_DESC+", "+ACHIEVEMENT_PROGRESS+", "+ACHIEVEMENT_TOTAL+", "+
				ACHIEVEMENT_TYPE+" FROM "+TABLE_ACHIEVEMENT+" ORDER BY "+ACHIEVEMENT_ID, null);
		
		// If Cursor has rows returned then create achievement and add to list
		if(curs.moveToFirst()){
			do{
				Achievement newAch = new Achievement();
				newAch.setIDAchievement(Integer.parseInt(curs.getString(0)));
				newAch.setIDUser(Integer.parseInt(curs.getString(1)));
				newAch.setName(curs.getString(2));
				newAch.setDescription(curs.getString(3));
				newAch.setProgress(Integer.parseInt(curs.getString(4)));
				newAch.setTotal(Integer.parseInt(curs.getString(5)));
				newAch.setType(curs.getString(6));
				achievements.add(newAch);
			}while(curs.moveToNext());
		}
		 
		return achievements;
	}
	
/*
 *  TASK FUNCTIONS
 */
	/**
	 * Deletes a Task from the Database with a matching ID
	 * @param task_id
	 */
	public void deleteTask(int task_id){
		SQLiteDatabase db = getWritableDatabase();
		db.delete(TABLE_TASK, TASK_ID + " = ?", new String[]{String.valueOf(task_id)});
		db.close();
	}

	/**
	 * Inserts a Task into the database via a Task Object, but only if the 
	 * task name is Unique.
	 * @param t
	 * @return isUnique: A boolean value that says whether the Task is valid or not.
	 */
	public boolean addTask(Task t) {
		
		boolean isUnique = true;
		
		// If the Task does not exist then proceed
        if(getTask(t.getName()).getIDTask() == -1){
            SQLiteDatabase db = this.getWritableDatabase();
        
            // Store Task Values
	        ContentValues values = new ContentValues();
	        values.put(TASK_USER, t.getIDUser());
	        values.put(TASK_NAME, t.getName());
	        values.put(TASK_IMAGE_REF, t.getImage_Ref());
	        values.put(TASK_LOCATION, t.getLocation());
	        values.put(TASK_DURATION, t.getDuration());
	        values.put(TASK_EXPERIENCE, t.getExperience());
	 
	        // Inserting Task
	        db.insert(TABLE_TASK, null, values);
	        db.close();    
        }else
        	isUnique = false;
        
        return isUnique;
        
    }
	
	/**
	 * Gets an arraylist of all the Tasks a user has available from the
	 * database
	 * @return tasks arraylist
	 */
	public ArrayList<Task> getTasks(){
		ArrayList<Task> tasks =  new ArrayList<Task>();
		SQLiteDatabase db = getReadableDatabase();
		
		// Gets a Cursor for all tasks in the database 
		Cursor curs = db.rawQuery("SELECT "+TASK_ID+", "+TASK_USER+", "+
								TASK_NAME+", "+TASK_IMAGE_REF+", "+TASK_LOCATION+", "+
								TASK_EXPERIENCE+", "+TASK_DURATION+" "+
								"FROM "+TABLE_TASK+" ORDER BY "+TASK_NAME, null);
		
		// If Cursor contains rows than Create Task Object and add to List
		if(curs.moveToFirst()){
			do{
				Task newTask = new Task();
				newTask.setIDTask(Integer.parseInt(curs.getString(0)));
				newTask.setName(curs.getString(2));
				newTask.setImage_Ref(curs.getString(3));
				newTask.setLocation(curs.getString(4));
				newTask.setExperience(Integer.parseInt(curs.getString(5)));
				newTask.setDuration(Integer.parseInt(curs.getString(6)));
				tasks.add(newTask);
			}while(curs.moveToNext());
		}
		
		return tasks;
	}
	
	/**
	 * Gets a List of the Most Recent Tasks for the User since their last visit
	 * from the database
	 * @return tasks arraylist
	 */
	public ArrayList<Task> getMostRecentTasks(){
		ArrayList<Task> tasks =  new ArrayList<Task>();
		SQLiteDatabase db = getReadableDatabase();
		
		// Gets a Cursor for all tasks in the database 
		Cursor curs = db.rawQuery("SELECT "+TASK_ID+", "+TASK_USER+", "+
								TASK_NAME+", "+TASK_IMAGE_REF+", "+TASK_LOCATION+", "+
								TASK_EXPERIENCE+", "+TASK_DURATION+" "+
								"FROM "+TABLE_TASK+" ORDER BY "+TASK_ID+" DESC", null);

		// If Cursor contains rows than Create Task Object and add to List
		if(curs.moveToFirst()){
			do{
				Task newTask = new Task();
				newTask.setIDTask(Integer.parseInt(curs.getString(0)));
				newTask.setName(curs.getString(2));
				newTask.setImage_Ref(curs.getString(3));
				newTask.setLocation(curs.getString(4));
				newTask.setExperience(Integer.parseInt(curs.getString(5)));
				newTask.setDuration(Integer.parseInt(curs.getString(6)));
				tasks.add(newTask);
			}while(curs.moveToNext());
		}
		
		return tasks;
	}
	
	/**
	 * Gets the Task matching the name given from the database.
	 * @param name
	 * @return
	 */
	public Task getTask(String name){
		SQLiteDatabase db = getReadableDatabase();
		
		// Gets a Cursor from the Task Query
		Cursor curs = db.query(TABLE_TASK, new String[]{TASK_ID, TASK_USER, TASK_NAME,
										TASK_IMAGE_REF, TASK_LOCATION, TASK_DURATION, 
										TASK_EXPERIENCE}, TASK_NAME+" = ?", new String[]{name},
										null, null, null);
		
		// Create a Task and Fill if the Cursor has a row available
		Task t = new Task();
		if(curs != null && curs.getCount() > 0){
			curs.moveToFirst();
			t.setIDTask(Integer.parseInt(curs.getString(0)));
			t.setIDUser(Integer.parseInt(curs.getString(1)));
			t.setName(curs.getString(2));
			t.setImage_Ref(curs.getString(3));
			t.setLocation(curs.getString(4));
			t.setDuration(Integer.parseInt(curs.getString(5)));
			t.setExperience(Integer.parseInt(curs.getString(6)));
		}
		
		return t;
	}
}
