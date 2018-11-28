package aufgabe6;

import java.util.Date;

public class Animal {

	private int id;
	private int birthday; // in minutes
	private boolean alpha;
	private int alphaTime; // in days
	private double cortisol;
	private double adrenaline; // only for betas
	private int cortisolTime;
	private int adrenalineTime;
	private boolean female;
	private int children; // only for females

	public Animal(int id, int birthday, boolean rank, int timeAsAlpha, double cortisol, double adrenaline,
			boolean female, int children) {
		this.id = id;
		this.birthday = birthday;
		this.alpha = rank;
		if (this.alpha)
			setAlphaTime(timeAsAlpha);
		this.cortisol = calcCortisol(cortisol);
		if (!this.alpha)
			calcAdrenaline(adrenaline);
		this.female = female;
		if (this.female)
			this.children = children;
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

	public int getAlphaTime() {
		if (this.getRank())
			return this.alphaTime;
		return -1;
	}

	// time is the time for which the animal has been an alpha
	public void setAlphaTime(int time) {
		// today - birthday - time spent as alpha = date at which animal became alpha
		if (this.getTime() - this.birthday - time > 0) {
			this.alphaTime = this.getTime() - this.birthday - time;
		} else {
			// if value chosen exceeds time alive a random value is
			// generated instead
			this.alphaTime = (int) (((double) (this.getTime() - this.birthday)) / (24 * 60) * (Math.random() * 0.7));
		}
	}

	public int calcAge() {
		return this.getTime() - this.getBirthday();
	}

	public void changeRank() {
		if (this.alpha) {
			this.alpha = false;
			this.cortisol = 0;
			this.adrenaline = 0;
		} else {
			this.alpha = true;
			this.setAlphaTime(0);
			this.cortisol = 0;
			this.adrenaline = 0;
		}
		this.setAdrenalineTime();
		this.setCortisolTime();
	}

	public double getCortisol() {
		return this.cortisol;
	}

	public double getAdrenaline() {
		if (!this.getRank())
			return this.adrenaline;
		return -1;
	}

	public double calcAdrenaline(double a) {
		this.adrenaline = a;
		this.setAdrenalineTime();
		return this.adrenaline;
	}

	public double calcCortisol(double c) {
		this.cortisol = c;
		this.setCortisolTime();
		return this.cortisol;
	}

	public void addCortisol(double c) {
		this.setCortisolTime();
		this.cortisol += c;
	}

	public void addAdrenaline(double a) {
		if (!this.getRank()) {
			this.setAdrenalineTime();
			this.adrenaline += a;
		}

	}

	/*
	 * these saves the age of the animal in two variables and are called when
	 * hormone levels change - for the animals we're testing this of course is the
	 * same as their current age since they have no hormones before they exist as
	 * objects
	 */
	public void setCortisolTime() {
		this.cortisolTime = this.getTime() - this.birthday;
	}

	public void setAdrenalineTime() {
		this.adrenalineTime = this.getTime() - this.birthday;
	}

	public int getCortisolTime() {
		return this.cortisolTime;
	}

	public int getAdrenalineTime() {
		return this.adrenalineTime;
	}

	public boolean isFemale() {
		return this.female;
	}

	public int getChildren() {
		if (this.female)
			return this.children;
		return -1;
	}

	// one method for children dying (c<0) & giving birth (c>0)
	public void changeChildren(int c) {
		if (this.female)
			this.children += c;
	}

	// calculates time lapsed since 1.1.2000
	public int getTime() {
		Date today = new Date();
		// 1.1.1970 is javas baseline and we need to adjust this to 1.1.2000 and convert
		// milliseconds to minutes
		// after the calculation we no longer need to use long
		return (int) (today.getTime() / 60000 - 30 * 525600);
	}

	// we separated this from toString() to help readability
	public void checkHormones() {
		System.out.println("Hormones for Animal " + this.getID() + ":");
		String s = "[ Cortisol: " + this.cortisol + ", changed last at " + this.cortisolTime;
		if (!this.alpha)
			s += " and Adrenaline: " + this.adrenaline + ", changed last at " + this.adrenalineTime;
		System.out.println(s + "]");
	}

	public String toString() {
		String s = "";
		String alphaString = "a beta";
		// adjusting precision of doubles for readability
		String adrenalineString = " and adrenaline: " + (double) Math.round(this.adrenaline * 10000) / 10000 + " ]";
		if (this.alpha) {
			alphaString = "an alpha, became alpha at: " + this.alphaTime;
			adrenalineString = " ]";
		}
		if (this.female) {
			s = "[ ID: " + this.id + ", birthday: " + this.birthday + ", gender: female, number of children: "
					+ this.getChildren() + ", is " + alphaString + ", hormones: cortisol: "
					+ (double) Math.round(this.cortisol * 10000) / 10000 + adrenalineString;
		}
		if (!this.female) {
			s = "[ ID: " + this.id + ", birthday: " + this.birthday + ", gender: male, is " + alphaString
					+ ", hormones: cortisol: " + (double) Math.round(this.cortisol * 10000) / 10000 + adrenalineString;
		}
		return s;
	}
}
