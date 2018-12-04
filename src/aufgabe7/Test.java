package aufgabe7;

public class Test {

	public static void main(String[] args) {

		Laboratory lab1 = new Laboratory("Lab 1");
		Laboratory lab2 = new Laboratory("Lab 2");
		Laboratory lab3 = new Laboratory("Lab 3");

		CheapAquAnimals cheapAquA1 = new CheapAquAnimals(10);
		CheapAquAnimals cheapAquA2 = new CheapAquAnimals(8);
		CheapAquAnimals cheapAquA3 = new CheapAquAnimals(21);
		CheapAquAnimals cheapAquA4 = new CheapAquAnimals(6);

		CheapTerrAnimals cheapTerrA1 = new CheapTerrAnimals(12);
		CheapTerrAnimals cheapTerrA2 = new CheapTerrAnimals(17);
		CheapTerrAnimals cheapTerrA3 = new CheapTerrAnimals(16);
		CheapTerrAnimals cheapTerrA4 = new CheapTerrAnimals(9);

		ExpensiveAquAnimals expensiveAquA1 = new ExpensiveAquAnimals(13);
		ExpensiveAquAnimals expensiveAquA2 = new ExpensiveAquAnimals(16);
		ExpensiveAquAnimals expensiveAquA3 = new ExpensiveAquAnimals(7);
		ExpensiveAquAnimals expensiveAquA4 = new ExpensiveAquAnimals(15);

		ExpensiveTerrAnimals expensiveTerrA1 = new ExpensiveTerrAnimals(22);
		ExpensiveTerrAnimals expensiveTerrA2 = new ExpensiveTerrAnimals(15);
		ExpensiveTerrAnimals expensiveTerrA3 = new ExpensiveTerrAnimals(18);
		ExpensiveTerrAnimals expensiveTerrA4 = new ExpensiveTerrAnimals(9);

		// TODO: convert these to lab.neu()
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
		System.out.println(lab1.volumenFrei() + "\n");

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

		System.out.println("");

		// retourniere

		System.out.println("A vivarium in lab2 is defekt:");

	}
}

// TODO
// Beschreibung wer an welchem Teil gearbeitet hat, entsprechend der Angabe
/*
 * Elias Nachbaur (01634010):
 */
/*
 * Florian Fusstetter (00709759): toString(), dynamic methods, objects for
 * testing, methods for generating output
 */
/*
 * Ignjat Karanovic (01529940):
 */
