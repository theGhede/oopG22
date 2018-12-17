package aufgabe9;

public class Test {
	// TODO: custom annotations
	/*
	 * TODO: - output stats for each year - output workload distribution
	 */
	private static Population population;
	private static Organization org1;
	private static Organization org3;
	private static Organization org2;
	private final static int YEARS = 10;

	private static void start() {
		population = new Population();
		org1 = new Organization(population);
		org2 = new Organization(population);
		org3 = new Organization(population);
		for (int i = 0; i < YEARS; i++) {
			if (i > 0) {
				org1.influencing();
				org2.influencing();
				org3.influencing();
			}
			population.yearEnd();
		}
	}

	private void print() {
		// TODO

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
	}

	public static void main(String[] args) {
		// start();
		System.out.println((int) (Math.random() * 3 + 1));
	}
}
