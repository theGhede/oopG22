package aufgabe7;

public class CheapTerrAnimals extends TerrAnimals {

	public CheapTerrAnimals(int size) {
		super(size);
	}

	@Override
	public void putInContainer(Vivarium container) {
		container.housesCheapTerrAnimals(this);
		/*
		 * Note: if a container was found and its inhabitant (=this animal) set, the
		 * animals pointer towards the container it's in is set so the Vivarium points
		 * to the Animal and the Animal points to the Vivarium
		 */
		if (container.getInhabitant() != null && container.getInhabitant().equals(this))
			this.setContainer(container);
	}

	@Override
	public void putInSecondChoice(Vivarium container) {
		container.housesCheapTerrSecondChoice(this);
		if (container.getInhabitant() != null && container.getInhabitant().equals(this))
			this.setContainer(container);
	}

	@Override
	public String toString() {
		String s = "<Type: cheap terrestrial animal, Size: " + this.getSize();
		if (this.getContainer() != null && !this.getShortString()) {
			this.getContainer().setShortString(true);
			s += ", Housed in:" + this.getContainer().toString();
		}
		s += ">";
		this.setShortString(false);
		return s;
	}
}
