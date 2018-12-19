package aufgabe9;

import java.util.ArrayList;
import java.util.List;

@MadeBy(lastModification = "19.12.2018")
public class Test {

	// some of these need to be visible for the Aspect
	// (using default visibility package)
	static Population population;
	static List<Organization> industry;
	private static Organization org1;
	private static Organization org3;
	private static Organization org2;
	private final static int YEARS = 11;
	static int currentYear = 0;

	@MadeBy(lastModification = "19.12.2018")
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
				industry.stream().forEach(Organization::influencing);
			}
			population.yearEnd();
		}
	}

	public static void main(String[] args) {
		start();
	}
}
