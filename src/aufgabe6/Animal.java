package aufgabe6;

import java.util.Date;

public class Animal {
	
	private int id;
	private int birthday; // in minutes
	private boolean alpha;
	private int timeAsAlpha; // in days
	private double cortisol;
	private double adrenaline; // only for betas
	private boolean female;
	private int children; // only for females
	
	public Animal(int id, int birthday, boolean rank, int timeAsAlpha, double cortisol, double adrenaline, boolean female, int children) {
		this.id = id;
		this.birthday = birthday;
		this.alpha = rank;
		if(this.alpha) setTimeAsAlpha(timeAsAlpha);
		this.cortisol = calcCortisol(cortisol);
		if(!this.alpha) calcAdrenaline(adrenaline);
		this.female = female;
		if(this.female) this.children = children;
	}

	public int getID() {
		return this.id;
	}
	
	public int getBirthday() {
		return this.birthday;
	}
	
	public boolean getRank() {
		return this.alpha;
	}
	
	public int getTimeAsAlpha() {
		if(this.getRank()) return this.timeAsAlpha;
		return -1;
	}
	
	public void setTimeAsAlpha(int time) {
		if(this.getTime() - time > 0) {
			this.timeAsAlpha = time;
		} else {
			// if timeAsAlpha chosen during creation exceeds time alive a random value is generated instead
			this.timeAsAlpha = (int)(((double)(this.getTime() - this.getBirthday()))/(24*60) * (Math.random() * 0.7));
		}
	}
	
	public int calcAge() {
		return this.getTime() - this.getBirthday();
	}
	
	public void changeRank() {
		if (this.alpha) {
			this.alpha = false;
			this.cortisol = 0;
			this.adrenaline = 0; // is this meant by "forget previous hormonal values"? or is = 0 sufficient?
		} else {
			this.alpha = true;
			this.cortisol = 0;
			this.adrenaline = 0;
		}
	}
	
	public double getCortisol() {
		return this.cortisol;
	}
	
	public double getAdrenaline() {
		if(!this.getRank()) return this.adrenaline;
		return -1;
	}
	
	public double calcAdrenaline(double a) {
		this.adrenaline = a;
		return this.adrenaline;
	}
	
	public double calcCortisol(double c) {
		this.cortisol = c;
		return this.cortisol;
	}
	
	public void addCortisol(double c) {
		this.cortisol += c;
	}
	
	public void addAdrenaline(double a) {
		if(!this.getRank()) this.adrenaline += a;
	}
	
	public boolean isFemale() {
		return this.female;
	}
	
	public int getChildren () {
		if(this.female) return this.children;
		return -1;
	}
	
	// one method for children dying (c<0) & giving birth (c>0)
	public void changeChildren(int c) {
		if(this.female)	this.children += c;
	}
	
	// calculates time lapsed since 1.1.2000
	public int getTime() {
		Date today = new Date();
		// 1.1.1970 is javas baseline and we need to adjust this to 1.1.2000 and convert milliseconds to minutes
		// after the calculation we no longer need to use long
		return (int)(today.getTime()/60000 - 30 * 525600);
	}
	

	public String toString() {
		String s = "";
		String alphaString = "a beta";
		// adjusting precision of doubles for readability
		String adrenalineString = " and adrenaline: " + (double)Math.round(this.adrenaline*10000)/10000 + " ]";
		if(this.alpha) {
			alphaString = "an alpha, time as alpha: " + this.timeAsAlpha;
			adrenalineString = " ]";
		}
		if(this.female) {
			s = "[ ID: " + this.id + ", birthday: " + this.birthday + ", gender: female, number of children: "
					+ this.getChildren() + ", is " + alphaString + ", hormones: cortisol: "
					+ (double)Math.round(this.cortisol*10000)/10000 + adrenalineString;
		}
		if(!this.female) {
			s = "[ ID: " + this.id + ", birthday: " + this.birthday + ", gender: male, is "
					+ alphaString + ", hormones: cortisol: " + (double)Math.round(this.cortisol*10000)/10000 + adrenalineString;
		}
		return s;
	}
}
