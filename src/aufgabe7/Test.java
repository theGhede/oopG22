package aufgabe7;

public class Test {

	public static void main(String[] args) {

		Laboratory lab1 = new Laboratory("Lab 1");
		Laboratory lab2 = new Laboratory("Lab 2");
		Laboratory lab3 = new Laboratory("Lab 3");

		Animals cheapAquA1 = new CheapAquAnimals(10);
		Animals cheapAquA2 = new CheapAquAnimals(8);
		Animals cheapAquA3 = new CheapAquAnimals(21);
		Animals cheapAquA4 = new CheapAquAnimals(6);

		Animals cheapTerrA1 = new CheapTerrAnimals(12);
		Animals cheapTerrA2 = new CheapTerrAnimals(17);
		Animals cheapTerrA3 = new CheapTerrAnimals(16);
		Animals cheapTerrA4 = new CheapTerrAnimals(9);

		Animals expensiveAquA1 = new ExpensiveAquAnimals(13);
		Animals expensiveAquA2 = new ExpensiveAquAnimals(16);
		Animals expensiveAquA3 = new ExpensiveAquAnimals(7);
		Animals expensiveAquA4 = new ExpensiveAquAnimals(15);

		Animals expensiveTerrA1 = new ExpensiveTerrAnimals(22);
		Animals expensiveTerrA2 = new ExpensiveTerrAnimals(15);
		Animals expensiveTerrA3 = new ExpensiveTerrAnimals(18);
		Animals expensiveTerrA4 = new ExpensiveTerrAnimals(9);

		lab1.addAnimals(cheapTerrA1);
		lab1.addAnimals(cheapTerrA2);
		lab1.addAnimals(expensiveTerrA1);
		lab1.addAnimals(expensiveTerrA2);
		lab1.neu(2, 4, 3, 0);
		lab1.neu(1, 5, 2, 0);
		lab1.neu(3, 3, 2, 1);
		lab1.neu(2, 3, 2, 1);
		lab1.neu(3, 4, 2, 1);
		lab1.neu(3, 4, 2, 3);
		lab1.neu(3, 3, 1, 2);
		lab1.inventarListe();
		lab1.schwarmListe();
		lab1.volumenFrei();
		lab1.volumenBelegt();
		System.out.println("\n");

		lab2.addAnimals(cheapAquA1);
		lab2.addAnimals(cheapAquA2);
		lab2.addAnimals(expensiveAquA1);
		lab2.addAnimals(expensiveAquA2);
		// can't add animals twice to the same or different labs
		lab2.addAnimals(expensiveAquA2);
		lab2.addAnimals(expensiveAquA3);
		lab2.addAnimals(expensiveTerrA4);
		lab2.neu(4, 2, 2, 0);
		lab2.neu(4, 4, 1, 1);
		lab2.neu(3, 3, 2, 1);
		lab2.neu(2, 4, 3, 2);
		lab2.neu(2, 2, 2, 2);
		lab2.neu(2, 3, 2, 2);
		lab2.neu(3, 3, 2, 3);
		lab2.neu(2, 5, 1, 3);
		lab2.neu(2, 4, 2, 3);
		lab2.inventarListe();
		lab2.schwarmListe();

		lab3.addAnimals(cheapTerrA3);
		lab3.addAnimals(cheapTerrA4);
		lab3.addAnimals(cheapAquA3);
		lab3.addAnimals(cheapAquA4);
		lab3.addAnimals(expensiveTerrA3);
		lab3.addAnimals(expensiveAquA4);
		lab3.neu(2, 3, 2, 0);
		lab3.neu(3, 3, 2, 0);
		lab3.neu(2, 5, 2, 1);
		lab3.neu(2, 3, 2, 2);
		lab3.neu(1, 4, 2, 2);
		lab3.neu(3, 3, 3, 3);
		lab3.inventarListe();
		lab3.schwarmListe();

		// calling stelleBereit through allocateVivaria, which tries to find a home for
		// all animals owned by the lab (in order of adding them) instead of one animal
		// at a time
		System.out.println("Finding lab animals a home:");
		lab1.allocateVivaria();
		lab2.allocateVivaria();
		lab3.allocateVivaria();

		System.out.println("Check animals of lab2 if they point towards the container they're in:");
		lab2.schwarmListe();

		System.out.println("Check lab1 free & occupied vivarias volume:");
		lab3.inventarListe();
		lab3.volumenFrei();
		lab3.volumenBelegt();

		System.out.println("\n" + "Removing animals from containers and returning them to inventory:");
		lab3.schwarmListe();
		lab3.retourniere(cheapTerrA3.getContainer());
		System.out.println(cheapTerrA3.toString());
		lab3.retourniere(cheapAquA3.getContainer());
		System.out.println(cheapAquA3.toString());
		System.out.println(lab3.stelleBereit(expensiveAquA4) + "\n");

		System.out.println("A mishandling of in lab2 leads to defects in the larger ones they have in inventory:");
		lab2.inventarListe();
		for (Vivarium v : lab2.getInventory()) {
			if (v.volume() > 15)
				v.setIntact(false);
		}
		lab2.defekt();
		// still one more defective vivarium to remove
		lab2.defekt();
		// got all of them
		lab2.defekt();
		lab2.inventarListe();

		// adding foreign lab animals to a labs vivaria
		Animals a = new CheapTerrAnimals(10);
		lab3.addAnimals(a);
		System.out.println(lab1.stelleBereit(a));
	}
}

// Beschreibung wer an welchem Teil gearbeitet hat, entsprechend der Angabe
/*
 * Togather at an IRL meeting: the different classes, constructors and their
 * variables and inheritance, getters & setters, schwarmListe, inventarListe,
 * volume (& related methods), neu, retourniere
 */
/*
 * Elias Nachbaur (01634010):
 */
/*
 * Florian Fusstetter (00709759): toString() methods, Test, generating various
 * output, stelleBereit() & the methods it calls, assertions
 */
/*
 * Ignjat Karanovic (01529940): assertions
 */