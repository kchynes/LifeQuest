package ca.chynes_b51_final;

import java.util.ArrayList;

import ca.chynes_b51_final.MainActivity.PlaceholderFragment;

import android.app.FragmentManager;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;
/*
 * Author: Casey Hynes
 * Date: Dec. 15, 2014
 * 
 * Creates a class with Static variables to act as a Utility Class
 * to be called from any location in order to reduce repeated code
 */
public class LifeQuest {
	
	/**
	 * Checks the Achievement Progress of the Specified achievement ID.
	 * Increments the value, and if the value reaches its goal than notify the
	 * User via a Toast and Tone.
	 * @param c - Context
	 * @param id - Achievement ID
	 */
	public static void checkAchievementProgress(Context c, int id){
		DBHelper db = new DBHelper(c);
		Achievement a = db.getAchievement(id);
		a.setProgress(a.getProgress()+1);
		db.updateAchievement(a);
		if(a.getTotal() == a.getProgress()){
			Toast.makeText(c, "Achievement Unlocked - "+a.getName(), Toast.LENGTH_LONG).show();
			playTone(c);
			checkPlatinumStatus(c);
		}
	}
	
	/**
	 * Checks the Achievement Progress of the Specified achievement ID.
	 * Increments the value based on the addition, and if the value reaches its goal 
	 * than notify the User via a Toast and Tone.
	 * @param c - Context
	 * @param id - Achievement ID
	 * @param addition - Value to Increment the Achievement By
	 */
	public static void checkAchievementProgressWithAddition(Context c, int id, int addition){
		DBHelper db = new DBHelper(c);
		Achievement a = db.getAchievement(id);
		int originalProgress = a.getProgress();
		a.setProgress(a.getProgress()+addition);
		db.updateAchievement(a);
		if(a.getProgress() >= a.getTotal() && originalProgress < a.getTotal()){
			Toast.makeText(c, "Achievement Unlocked - "+a.getName(), Toast.LENGTH_LONG).show();
			playTone(c);
			checkPlatinumStatus(c);
		}
	}
	
	/**
	 * Checks the Platinum achievement, got all achievement, by incrementing
	 * the Platinum achievements if another achievement was earned.
	 * @param c - Context
	 */
	private static void checkPlatinumStatus(Context c){
		DBHelper db = new DBHelper(c);
		Achievement a = db.getAchievement(15); // Platinum Achievement
		a.setProgress(a.getProgress()+1);
		db.updateAchievement(a);
		if(a.getTotal() == a.getProgress()){
			Toast.makeText(c, "Achievement Unlocked - "+a.getName(), Toast.LENGTH_LONG).show();
			playTone(c);
		}
	}
	
	/**
	 * Checks the Users level progress when a task has been completed.
	 * Notifies the Users if they have leveled up, plays a tone, and display 
	 * a Toast explaining to the user that they have leveled.
	 * @param c - Context
	 * @param t - Task
	 */
	public static void checkLevelProgress(Context c, Task t){
		DBHelper db = new DBHelper(c);
		User u = db.getUser(t.getIDUser());
		int oldLevel = u.calculateLevel();
		u.setExperience(u.getExperience()+t.getExperience());
		
		// If leveled
		if(u.calculateLevel() > oldLevel){
			playTone(c);
			Toast.makeText(c,
					u.getName()+" Reached Level "+u.calculateLevel()+"!",
					Toast.LENGTH_LONG).show();
			
			// Check Level Achievement Progress
			checkAchievementProgress(c, 9);
			checkAchievementProgress(c, 10);
			checkAchievementProgress(c, 11);
		}
		db.updateUser(u);
	}
	
	/**
	 * Plays a Notification tone.
	 * @param c
	 */
	private static void playTone(Context c){
		Uri levelup = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		Ringtone r = RingtoneManager.getRingtone(c, levelup);
		r.play();
	}
	
	/**
	 * Initializes all the achievements for the user when they register, and
	 * adds each object to the database.
	 * @param c - Context
	 * @param userid - User ID
	 */
	public static void setupAchievements(Context c, int userid){
		ArrayList<Achievement> achievements = new ArrayList<Achievement>();
		
		// Create Achievement Objects
		Achievement a1 = new Achievement(0, userid, "Welcome to LifeQuest", "Successfully Registered", 0, 1, "Bronze");
		Achievement a2 = new Achievement(0, userid, "Feeling Alive", "Created 5 Quests", 1, 5, "Bronze");
		Achievement a3 = new Achievement(0, userid, "25s a Crowd", "Created 25 Quests", 1, 25, "Silver");
		Achievement a4 = new Achievement(0, userid, "MidQuest Crisis", "Created 50 Quests", 1, 50, "Gold");
		Achievement a5 = new Achievement(0, userid, "Everybody gets one", "Complete 1 Quest", 0, 1, "Bronze");
		Achievement a6 = new Achievement(0, userid, "Go ahead, make my Quest", "Complete 25 Quest", 0, 25, "Silver");
		Achievement a7 = new Achievement(0, userid, "May the Quest be with you", "Complete 50 Quest", 0, 50, "Gold");
		Achievement a8 = new Achievement(0, userid, "Questception", "Quested for 100 Hours", 0, 100, "Silver");
		Achievement a9 = new Achievement(0, userid, "Quest Lightly", "Reach Level 5", 1, 5, "Bronze");
		Achievement a10 = new Achievement(0, userid, "I am the one who Quests", "Reach Level 10", 1, 10, "Silver");
		Achievement a11 = new Achievement(0, userid, "Prestigious", "Reach Level 50", 1, 50, "Gold");
		Achievement a12 = new Achievement(0, userid, "Autobiography", "Viewed the About Page", 0, 1, "Bronze");
		Achievement a13 = new Achievement(0, userid, "C-C-C-C-Combo Breaker", "Quit a Quest", 0, 1, "Bronze");
		Achievement a14 = new Achievement(0, userid, "Helping Hand", "Viewed the Help Page", 0, 1, "Bronze");
		Achievement a15 = new Achievement(0, userid, "Platnium", "Finished All Achievements", 0, 15, "Gold");
		
		// Add Achievements Objects to List
		achievements.add(a1);
		achievements.add(a2);
		achievements.add(a3);
		achievements.add(a4);
		achievements.add(a5);
		achievements.add(a6);
		achievements.add(a7);
		achievements.add(a8);
		achievements.add(a9);
		achievements.add(a10);
		achievements.add(a11);
		achievements.add(a12);
		achievements.add(a13);
		achievements.add(a14);
		achievements.add(a15);
		
		// Insert all Achievements
		DBHelper db = new DBHelper(c);
		db.addAchievements(achievements);
	}
	
}
