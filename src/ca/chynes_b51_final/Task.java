package ca.chynes_b51_final;

/*
 * Author: Casey Hynes
 * Date: Dec. 15, 2014
 * 
 * Desc: Task Class holds the LifeQuest Task information, that can be
 * populated by a database.
 */
public class Task {
	
	private int IDTask;
	private int IDUser;
	private String Name;
	private String Image_Ref;
	private String Location;
	private int Duration;
	private int Experience;
	
	public Task(){
		this.IDTask = -1;
		this.IDUser = -1;
		this.Name = null;
		this.Image_Ref = null;
		this.Location = null;
		this.Duration = 0;
		this.Experience = 0;
	}
	
	public Task(int idt, int idu, String n, String ir, String l, int d, int e){
		this.IDTask = idt;
		this.IDUser = idu;
		this.Name = n;
		this.Image_Ref = ir;
		this.Location = l;
		this.Duration = d;
		this.Experience = e;
	}

	public int getIDTask() {
		return IDTask;
	}

	public void setIDTask(int iDTask) {
		IDTask = iDTask;
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

	public String getImage_Ref() {
		return Image_Ref;
	}

	public void setImage_Ref(String image_Ref) {
		Image_Ref = image_Ref;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public int getDuration() {
		return Duration;
	}

	public void setDuration(int duration) {
		Duration = duration;
	}

	public int getExperience() {
		return Experience;
	}

	public void setExperience(int experience) {
		Experience = experience;
	}
	
}
