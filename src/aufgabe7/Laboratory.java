package aufgabe7;

import java.util.ArrayList;
import java.util.List;

public class Laboratory {

	private String name;
	/*
	 * considering all methods which add or remove items to or from inventory there
	 * always be only empty vivaria in it - this list holds all vivaria of any type
	 * which belong to this lab
	 */
	private List<Vivarium> inventory;
	/*
	 * Note: since we remove filled vivaria from inventory as soon as we fill them
	 * and need some datastructure to keep track of the labs lab animals we use this
	 * second list NOT for Vivarium but Animals and can also use it to find filled
	 * vivaria by looking at the container found in Animals
	 */
	private List<Animals> labAnimals;

	public Laboratory(String name) {
		this.name = name;
		this.inventory = new ArrayList<>();
		this.labAnimals = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public List<Vivarium> getInventory() {
		return inventory;
	}

	/*
	 * Assertion:
	 * 
	 * precondition & postcondition - lenght, width, height sind sinnvolle Werte
	 * 
	 * postcondition - Nach der Ausführung von neu sind ein leeres Vivarium mehr in
	 * inventory als vor dem Ausführen (& min. ein leeres Vivarium)
	 */
	public void neu(int length, int width, int height, int type) {
		if (type == 0) {
			Vivarium vivarium = new CheapTerrarium(length, width, height);
			this.inventory.add(vivarium);
		} else if (type == 1) {
			Vivarium vivarium = new ExpensiveTerrarium(length, width, height);
			this.inventory.add(vivarium);
		} else if (type == 2) {
			Vivarium vivarium = new CheapAquarium(length, width, height);
			this.inventory.add(vivarium);
		} else if (type == 3) {
			Vivarium vivarium = new ExpensiveAquarium(length, width, height);
			this.inventory.add(vivarium);
		} else {
			System.out.println("Input correct type for 'neu': 0 = cheap terrarium / 1 = expensive terrarium /"
					+ " 2 = cheap aquarium / 3 = expensive aquarium \n");
		}
	}

	// add Animals to the Laboratory
	public void addAnimals(Animals animals) {
		if (!animals.isOwned()) {
			this.labAnimals.add(animals);
			animals.setOwned(true);
		} else {
			System.out.println(animals.toString() + " is already part of '" + this.name + "'s pool of lab animals \n");
		}
	}

	public void defekt() {
		for (Vivarium vivarium : this.inventory) {
			if (!vivarium.isIntact()) {
				this.inventory.remove(vivarium);
				System.out.println("Defect vivarium " + vivarium.toString() + " was removed from inventory of Lab '"
						+ this.name + "'.");
				// -> "löscht EIN defektes Vivarium aus der Inventarliste"
				break;
			}
		}
	}

	/*
	 * Assertion:
	 * 
	 * postcondition - Nach dem Ausführen sind entweder inventory.length()-1 oder
	 * inventory.length() viele Vivarien in inventory & es wird im Fall 1 ein zuvor
	 * leeres Vivarium entfernt - die Anzahl der befüllten Vivarien in inventory ist
	 * vor und nach Ausführen stelleBereit unverändert
	 */
	/*
	 * Note: uses next available container of the correct type that is large enough
	 * (= "ein passendes Vivarium"); not using the best available container that
	 * wastes the least space
	 */
	public Vivarium stelleBereit(Animals animals) {
		if (this.labAnimals.contains(animals)) {
			for (Vivarium v : this.inventory) {
				if (animals.getContainer() == null && v.getInhabitant() == null && v.volume() >= animals.getSize()) {
					animals.putInContainer(v);
					if (v.getInhabitant() != null) {
						this.inventory.remove(v);
						return v;
					}
				}
			}
			// option to put cheap animals in expensive containers if no cheap one fits
			for (Vivarium v : this.inventory) {
				if (animals.getContainer() == null && v.getInhabitant() == null && v.volume() >= animals.getSize()) {
					animals.putInSecondChoice(v);
					if (v.getInhabitant() != null) {
						this.inventory.remove(v);
						return v;
					}
				}
			}
			System.out.println("Can't find a suitable container for " + animals.toString());
			return null;
		}
		System.out.println(animals.toString() + " is not part of this Laboratories lab animals!");
		return null;
	}

	// output for allocating vivaria and animals in bulk
	public void allocateVivaria() {
		System.out.println("Fitting lab Animals in Lab " + this.name + "into vivaria if possible:");
		// adding all animals to vivaria with stelleBereit for all 3 labs
		for (Animals a : this.labAnimals) {
			System.out.println(this.stelleBereit(a));
		}
		System.out.println("\n");
	}

	/*
	 * Assertion: postcondition - falls das übergebene Vivarium "inhabitants" hat,
	 * ist nach Ausführen von retourniere das Vivarium leer und wieder Teil von
	 * inventory und das Tier, welches zuvür in dem Vivarium gelebt hat hat keinen
	 * container mehr
	 */
	public void retourniere(Vivarium vivarium) {
		if (vivarium.getInhabitant() != null) {
			System.out.println("Returning" + vivarium.toString() + "to inventory and removing it's inhabitants.");
			vivarium.getInhabitant().setContainer(null);
			vivarium.setInhabitant(null);
			this.inventory.add(vivarium);
		} else {
			System.out.println("This container is already empty.");
		}
	}

	public void volumenFrei() {
		int vol = 0;
		for (Vivarium vivarium : this.inventory) {
			if (vivarium.isFree())
				vol += vivarium.volume();
		}
		System.out.println("Total volume of available vivaria in Lab '" + this.name + "': " + vol);
	}

	public void volumenBelegt() {
		int vol = 0;
		/*
		 * inventory has no information about filled vivaria, thus we need to look at
		 * our animals and the containers holding them
		 */
		for (Animals animals : this.labAnimals) {
			if (animals.getContainer() != null)
				vol += animals.getContainer().volume();
		}
		System.out.println("Total volume of inhabited vivaria in Lab '" + this.name + "': " + vol);
	}

	public void inventarListe() {
		System.out.println("Vivaria in '" + this.name + "'s inventory:");
		for (Vivarium vivarium : inventory) {
			/*
			 * Note:
			 * "stellebereit gibt ein passendes Vivarium für einen Versuchstierschwarm zurück und entfernt dieses aus der Inventarliste"
			 * 
			 * removing all the vivaria from inventory as soon as we put animals in them is
			 * going to print a list of only empty vivaria belonging to this laboratory
			 * 
			 * vivaria currently housing animals are as requested no longer part of the
			 * inventory even though this is unintuitive in our opinion
			 */
			System.out.println(vivarium.toString());
		}
		System.out.println("\n");
	}

	public void schwarmListe() {
		System.out.println("Lab animals owned by '" + this.name + "':");
		for (Animals animals : this.labAnimals) {
			System.out.println(animals.toString());
		}
		System.out.println("\n");
	}
}
