package aufgabe6;

public class Test {

	/*
	 * While all classes have their own toString() method print(o) is shorter than
	 * System.out.println() and print for the Iterable classes disply information
	 * slightly differently than toString() and output it directly with some added
	 * text.
	 */
	public static void print(Object o) {
		System.out.println(o.toString());
	}

	public static void main(String[] args) throws Exception {
		Pack pack1 = new Pack("eastern pack");
		Pack pack2 = new Pack("western pack");
		AllPacks packs = new AllPacks("East & West");

		// Note: maximum possible age is:
		System.out.println(pack1.getTime());

		// 3+ years old
		Animal animal1 = new Animal(2374, (int) (Math.random() * 8367840 + 1576800), false, 0, (Math.random() * 15 + 5),
				(Math.random() * 9 + 1), true, (int) (Math.random() * 3) + 1);
		// 8+ years old, alpha since at least a year
		Animal animal2 = new Animal(1743, (int) (Math.random() * 5739840 + 4204800), true,
				(int) (Math.random() * 1460 + 365), (Math.random() * 15 + 5), 0, false, 0);
		// 3+ years old
		Animal animal3 = new Animal(2018, (int) (Math.random() * 8367840 + 1576800), true, (int) (Math.random() * 1825),
				(Math.random() * 15 + 5), 0, true, (int) (Math.random() * 5));
		// 3+ years old
		Animal animal4 = new Animal(1847, (int) (Math.random() * 8367840 + 1576800), false, 0, (Math.random() * 15 + 5),
				(Math.random() * 9 + 1), true, (int) (Math.random() * 5 + 1));
		// 3+ years old
		Animal animal5 = new Animal(3192, (int) (Math.random() * 8367840 + 1576800), false, 0, (Math.random() * 15 + 5),
				(Math.random() * 9 + 1), false, 0);

		pack1.add(animal1);
		pack1.add(animal2);
		pack1.add(animal3);
		pack1.add(animal4);
		pack1.add(animal5);

		// 3+ years old
		Animal animal6 = new Animal(1892, (int) (Math.random() * 8367840 + 1576800), false, 0, (Math.random() * 15 + 5),
				(Math.random() * 9 + 1), false, 0);
		// 3+ years old
		Animal animal7 = new Animal(8904, (int) (Math.random() * 8367840 + 1576800), false, 0, (Math.random() * 15 + 5),
				(Math.random() * 9 + 1), false, 0);

		// pack 2 is only males with no alphas in order to test allStats()
		pack2.add(animal6);
		pack2.add(animal7);

		// test if animals can be added to packs
		packs.add(animal1);
		packs.add(pack1);
		packs.add(pack2);

		System.out.println("Printing an animal & their hormones info:");
		print(animal1);
		animal1.checkHormones();
		System.out.println("\n The 2 packs:");
		packs.print();
		System.out.println("Average values for Pack 1:");
		pack1.allStats();
		System.out.println("Average values for Pack 2:");
		pack2.allStats();

		// newborn of animal 1, just 0-3 days old
		int dateOfBirth = animal1.getTime();
		animal1.changeChildren(1);
		Animal newborn = new Animal(8328, (dateOfBirth - 24 * 60), false, (int) (Math.random() * 1094 + 1),
				(Math.random() * 15 + 5), (Math.random() * 9 + 1), true, 0);
		pack1.add(newborn);
		System.out.println("Animal 1 gives birth to a newborn:");
		pack1.print();

		// animal4 looses a child and becomes more stressed
		animal4.changeChildren(-1);
		animal4.addCortisol(animal4.getCortisol() * 0.3);
		animal4.addAdrenaline(4);
		System.out.println("Animal 4 loses a child:");
		pack1.print();

		// animal 2 looses alpha status & animal5 gains alpha status
		animal2.changeRank();
		animal2.addCortisol((Math.random() * 14 + 10));
		animal2.addAdrenaline(Math.random() * 7 + 7);
		animal5.changeRank();
		animal5.addCortisol(animal2.getCortisol() * 0.7);
		System.out.println("Leadership struggle:");
		pack1.print();

		// animal 2 leaves the pack
		pack1.removeByID(1743);
		pack1.print();

		// remove pack2 from packs
		Pack pack3 = new Pack("northern pack");
		packs.add(pack3);
		packs.print();
		System.out.println("\n" + "Removing northern pack:");
		packs.removeByName("northern pack");
		packs.print();
		System.out.println("Removing eastern pack:");
		packs.removeByName("eastern pack");
		packs.print();
		System.out.println("Removing western pack:");
		packs.removeByName("western pack");
		packs.print();
	}
}

// Beschreibung wer an welchem Teil gearbeitet hat, entsprechend der Angabe
/*
 * Elias Nachbaur (01634010):
 */
/*
 * Florian Fusstetter (00709759): Aufgabe6
 */
/*
 * Ignjat Karanovic (01529940):
 */