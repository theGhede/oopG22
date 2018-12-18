package aufgabe9;

import java.util.ArrayList;
import java.util.List;

public class Test {
	/*
	 * TODO:
	 * 
	 * - custom annotations
	 * 
	 * - output workload distribution
	 */

	// these need to be visible for the Aspect
	// package is the lowest visibility where this is the case
	static Population population;
	static List<Organization> industry;
	static Organization org1;
	static Organization org3;
	static Organization org2;
	private final static int YEARS = 11;
	static int currentYear = 0;

	public static void start() {
		population = new Population();
		org1 = new Organization(population);
		org2 = new Organization(population);
		org3 = new Organization(population);
		industry = new ArrayList<>();
		industry.add(org1);
		industry.add(org2);
		industry.add(org3);
		for (int i = 0; i < YEARS; i++) {
			if (i > 0)
				currentYear = i;
			System.out.println("\n" + "		YEAR " + currentYear + ":");
			if (i > 0) {
				org1.influencing();
				org2.influencing();
				org3.influencing();
			}
			population.yearEnd();
		}
	}

	public static void main(String[] args) {
		start();
	}
}
