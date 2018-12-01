package aufgabe7;

import java.util.List;

public class LaborInventar {

	private String name;
	private List<Vivarium> inventory;
	private List<Animals> labAnimals;

	public LaborInventar(String name, List<Vivarium> inventory, List<Animals> labAnimals) {
		this.name = name;
		this.inventory = inventory;
		this.labAnimals = labAnimals;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void neu(int length, int width, int height, int type) {
		if (type == 0) {
			CheapTerrarium vivarium = new CheapTerrarium(length, width, height);
			this.inventory.add(vivarium);
		} else if (type == 1) {
			ExpensiveTerrarium vivarium = new ExpensiveTerrarium(length, width, height);
			this.inventory.add(vivarium);
		} else if (type == 2) {
			CheapAquarium vivarium = new CheapAquarium(length, width, height);
			this.inventory.add(vivarium);
		} else if (type == 3) {
			ExpensiveAquarium vivarium = new ExpensiveAquarium(length, width, height);
			this.inventory.add(vivarium);
		} else {
			System.out.println("Input correct type for neu: 0 = cheap terrarium / 1 = expensive terrarium /"
					+ " 2 = cheap aquarium / 3 = expensive aquarium");
		}

	}

	public void defekt() {
		for (Vivarium vivarium : this.inventory) {
			if (!vivarium.isIntact()) {
				this.inventory.remove(vivarium);
				// -> "löscht EIN defektes Vivarium"
				break;
			}
		}
	}

	public Vivarium stelleBereit(Animals animals) {
		for (Vivarium v : this.inventory) {
			// TODO: dynamic checks for
			// type: -1 = unspecific / 0 = terra / 1 = aqua
			// expensive: -1 = unspecific / 0 = chap / 1 = expensive
			v.setInhabitant(animals);
			animals.setContainer(v);
			this.inventory.remove(v);
			return v;
		}
		return null;
	}

	public void retourniere(Vivarium vivarium) {
		vivarium.getInhabitant().setContainer(null);
		vivarium.setInhabitant(null);
		this.inventory.add(vivarium);
	}

	public int volumenFrei() {
		int vol = 0;
		for (Vivarium vivarium : inventory) {
			if (vivarium.isFree())
				vol += vivarium.volume();
		}
		return vol;
	}

	public int volumenBelegt() {
		int vol = 0;
		for (Vivarium vivarium : inventory) {
			if (!vivarium.isFree())
				vol += vivarium.volume();
		}
		return vol;
	}

	// TODO:
	public void inventarListe() {
		// LaborID
		for (Vivarium vivarium : inventory) {
			// name
			System.out.println(vivarium.toString());
			// plus information regarding vivarium.getInhabitant()
		}
	}

	public void schwarmListe() {
		for (Animals animals : labAnimals) {
			System.out.println(animals.toString());
			// plus info regarding animals.getContainer()
		}
	}
}
