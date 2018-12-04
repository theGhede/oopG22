package aufgabe7;

public abstract class AquAnimals extends Animals {

	public AquAnimals(int size) {
		super(size);
	}

	@Override
	public abstract void putInContainer(Vivarium container);

	@Override
	public abstract void putInSecondChoice(Vivarium container);

	@Override
	public abstract String toString();
}
