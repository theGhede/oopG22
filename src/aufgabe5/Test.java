package aufgabe5;

import aufgabe5.SocialGroup.TypePredicates;

/*
2. Wählen Sie ein Objekt vom Typ SocialGroup<SteppeHerdAnimal> und
fügen Sie zu diesem Sozialverband durch Aufrufe von move alle Tiere der
in Punkt 1 aufgebauten Sozialverbände der Typen SocialGroup<Zebra> und SocialGroup<Ostrich> hinzu,
für die Ergebnisse von protection bzw. power in bestimmten (je nach Tierart unterschiedlichen) Wertebereichen liegen.
Überprüfen Sie das so aufgefüllte Objekt noch einmal so wie in Punkt 1.
*/

public class Test {
	public static void main(String[] args) {
		// Note: we expect no one to add the same animal twice to the same group or try
		// to add an animal to group A and group B and then try to move it between those
		// two groups, this is an assertion only and the code does not check for this at
		// any point & it makes no logical sense to do so
		SocialGroup<Starling> starlingGroup = new SocialGroup<>();
		SocialGroup<Zebra> zebraGroup = new SocialGroup<>();
		SocialGroup<Ostrich> ostrichGroup = new SocialGroup<>();
		SocialGroup<SteppeHerdAnimal> steppeGroup = new SocialGroup<>();

		/*
		 * Note: in order to not depend on setting hierarchical correctly animal
		 * constructors call setHierarchical(b) with b = true or false, whatever is the
		 * actually correct value for the type since it's the same for each animal of
		 * each type
		 * 
		 * fitness is calculated based on animal specific rules, the constructors only
		 * ask for it because of super.constructor
		 */
		SteppeHerdAnimal steppeAnimal = new SteppeHerdAnimal(0, false, 60);
		// this actually can't be added to any of the 4 groups we made, but FitAnimal is
		// not really an interesting type for objects
		FitAnimal fitAnimal = new FitAnimal(60, true);

		Starling starling1 = new Starling(0, true, 80, true);
		Starling starling2 = new Starling(0, false, 71, true);
		Starling starling3 = new Starling(0, true, 100, false);
		Starling starling4 = new Starling(0, false, 48, true);
		Starling starling5 = new Starling(0, false, 60, true);

		Zebra zebra1 = new Zebra(0, false, 64, true, 0.6);
		Zebra zebra2 = new Zebra(0, true, 63, false, 0.8);
		Zebra zebra3 = new Zebra(0, true, 62, true, 0.3);
		Zebra zebra4 = new Zebra(0, true, 58, true, -0.7);
		Zebra zebra5 = new Zebra(0, false, 56, false, 1.5);

		Ostrich ostrich1 = new Ostrich(0, false, 70, 900);
		Ostrich ostrich2 = new Ostrich(0, true, 68, 1300);
		Ostrich ostrich3 = new Ostrich(0, true, 56, 1100);

		System.out.println("FitAnimal: " + fitAnimal.toString() + " Is this FitAnimal fitter than zebra1? "
				+ fitAnimal.fitter(zebra1));

		starlingGroup.add(starling2);
		starlingGroup.add(starling3);
		starlingGroup.add(starling4);
		// testing addHead() - if new animal is the fittest it becomes the new head
		starlingGroup.add(starling1);
		// testing insert
		starlingGroup.add(starling5);

		
		zebraGroup.add(zebra1);
		zebraGroup.add(zebra2);
		zebraGroup.add(zebra3);
		zebraGroup.add(zebra4);
		zebraGroup.add(zebra5);

		ostrichGroup.add(ostrich1);
		ostrichGroup.add(ostrich2);
		ostrichGroup.add(ostrich3);
		
		Zebra zebra6 = new Zebra(0, true, 64, true, 0.8);
		Zebra zebra7 = new Zebra(0, false, 60, false, 0.5);
		SocialGroup<Zebra> moveZebras = new SocialGroup<>();
		moveZebras.add(zebra6);
		moveZebras.add(zebra7);

		System.out.println("Starlings (these were added out of order to test the 3 methods of adding animals addTail, addHead & insert)");
		starlingGroup.print();

		System.out.println("Ostriches");
		ostrichGroup.print();

		System.out.println("Zebras");
		zebraGroup.print();
		zebraGroup.alpha().print();
		System.out.println(zebraGroup.alpha().getClass() + "\n");

		System.out.println("Moving zebras between two zebra groups");
		moveZebras.print();
		zebraGroup.move(moveZebras, TypePredicates.typePredicate);
		zebraGroup.print();

		System.out.println("Mixing Zebras and Ostriches ... and SteppeHerdAnimals");
		steppeGroup.add(zebra1);
		steppeGroup.add(ostrich1);
		steppeGroup.add(steppeAnimal);
		steppeGroup.print();

		// animals of different groups can be compared, even if it might be questionable
		// to do so - zebra2's fittnes is just within the 10% margin of ostrich2's
		// fitness
		System.out.println("zebra2.fitness: " + zebra2.getFitness() + ", otrich2.fitness: " + ostrich2.getFitness());
		System.out.println("Is zebra2 fitter than ostrich2?");
		System.out.println(zebra2.fitter(ostrich2));
		System.out.println("Is zebra1 fitter than zebra1?");
		System.out.println(zebra1.fitter(zebra1) + "\n");
		// comparing animals of the same type seems more reasonable
		System.out.println("starling1.fitness: " + starling1.getFitness() + ", starling3.fitness: "
				+ starling3.getFitness() + ", starling4.fitness" + starling4.getFitness());
		System.out.println("Is starling1 fitter than starling3?");
		System.out.println(starling1.fitter(starling3));
		System.out.println("Is starling3 fitter than starling1?");
		System.out.println(starling3.fitter(starling1));
		System.out.println("Is starling3 fitter than starling4?");
		System.out.println(starling3.fitter(starling4) + "\n");

		// TODO: use move() instead of moveZebra() & moveOstrich()
		System.out.println("Moving animals between Zebra/Ostrich & SteppeAnimal groups");
		steppeGroup.moveZebras(zebraGroup, TypePredicates.typePredicate);
		steppeGroup.moveOstriches(ostrichGroup, TypePredicates.typePredicate);
		steppeGroup.print();
		zebraGroup.print();
	}

}

/*
 * TODO: Beschreibung wer an welchem Teil gearbeitet hat, entsprechend der
 * Angabe
 */
/* Elias Nachbaur (01634010): */
/* Florian Fusstetter (00709759): */
/* Ignjat Karanovic (01529940): */