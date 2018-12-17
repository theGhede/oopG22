package aufgabe9;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Test {
	// TODO: custom annotations
	/*
	 * TODO:
	 * 
	 * - output workload distribution
	 */
	private static Population population;
	private static List<Organization> industry;
	private static Organization org1;
	private static Organization org3;
	private static Organization org2;
	private final static int YEARS = 10;
	private static int currentYear = 0;

	private static void start() {
		population = new Population();
		org1 = new Organization(population);
		org2 = new Organization(population);
		org3 = new Organization(population);
		industry = new ArrayList<>();
		industry.add(org1);
		industry.add(org2);
		industry.add(org3);
		for (int i = 0; i < 1; i++) {
			currentYear = i;
			System.out.println("\n" + "YEAR " + currentYear + ":");
			if (i > 0) {
				org1.influencing();
				org2.influencing();
				org3.influencing();
			}
			population.yearEnd();
			printStats();
		}
	}

	private static void printStats() {
		printCensus();
		printWishStats();
		printRep();
	}

	private static void printCensus() {
		System.out.println("Census of population in Year " + currentYear + " is " + population.census() + ".");
	}

	private static void printWishStats() {
		System.out.println("On average every person made " + population.avgWishes() + " wishes this year.");
		System.out.println("These wishes have an average strength of " + population.avgDesires() + ".");
		System.out.println("Excluding negative values the average strength is: " + population.avgPositives() + ".");
	}

	private static void printRep() {
		System.out.println("The industries products are represented by the populations wishes: ");
		Stream<Double> r = industry.stream().map(e -> e.representedWishes());
		r.forEach(System.out::println);
		System.out
				.println("(avg representation for each organization, from org1 to org" + industry.size() + ")" + "\n");
	}

	public static void main(String[] args) {
		start();
	}
}
