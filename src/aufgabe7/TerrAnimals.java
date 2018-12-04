package aufgabe7;

public abstract class TerrAnimals extends Animals {

	public TerrAnimals(int size) {
		super(size);
	}

	@Override
	public abstract void putInContainer(Vivarium container);

	@Override
	public abstract void putInSecondChoice(Vivarium container);

	@Override
	public abstract String toString();
}
