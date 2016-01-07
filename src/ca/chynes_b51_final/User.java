package ca.chynes_b51_final;

/*
 * Author: Casey Hynes
 * Date: Dec. 15, 2014
 * 
 * Desc: User Class holds the LifeQuest User information, that can be
 * populated by a database.
 */
public class User {
	private int IDUser;
	private String Name;
	private int YearBorn;
	private int Experience;
	
	public User(){
		this.IDUser = -1;
		this.Name = null;
		this.YearBorn = 1900;
		this.Experience = 0;
	}
	
	public User(int id, String n, int y, int e){
		this.IDUser = id;
		this.Name = n;
		this.YearBorn = y;
		this.Experience = e;
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

	public int getYearBorn() {
		return YearBorn;
	}

	public void setYearBorn(int yearBorn) {
		YearBorn = yearBorn;
	}

	public int getExperience() {
		return Experience;
	}

	public void setExperience(int experience) {
		Experience = experience;
	}
	
	public int calculateLevel(){
		
		return (this.Experience/250)+1;
	}
	
}
