package ca.chynes_b51_final;

import android.content.Context;
import android.widget.Toast;

/*
 * Author: Casey Hynes
 * Date: Dec. 15, 2014
 * 
 * Makes an Achievement Object that replicates the columns in the SQLite
 * Database.
 */
public class Achievement {
	
	private int IDAchievement;
	private int IDUser;
	private String Name;
	private String Description;
	private int Progress;
	private int Total;
	private String Type;
	
	public Achievement(){
		this.IDAchievement = -1;
		this.IDUser = -1;
		this.Name = null;
		this.Description = null;
		this.Progress = 0;
		this.Total = 1000;
		this.Type = null;
	}
	
	public Achievement(int ida, int idu, String n, String d, int p, int t, String type){
		this.IDAchievement = ida;
		this.IDUser = idu;
		this.Name = n;
		this.Description = d;
		this.Progress = p;
		this.Total = t;
		this.Type = type;
	}

	public int getIDAchievement() {
		return IDAchievement;
	}

	public void setIDAchievement(int iDAchievement) {
		IDAchievement = iDAchievement;
	}

	public int getIDUser() {
		return IDUser;
	}

	public void setIDUser(int iDUser) {
		IDUser = iDUser;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getProgress() {
		return Progress;
	}

	public void setProgress(int progress) {
		Progress = progress;
	}

	public int getTotal() {
		return Total;
	}

	public void setTotal(int total) {
		Total = total;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
	
}
