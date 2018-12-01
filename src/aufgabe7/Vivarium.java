package aufgabe7;

public class Vivarium {

	// TODO
	// • Terrarium, preiswert
	// • Terrarium, teuer
	// • Aquarium, preiswert
	// • Aquarium, teuer

	public Vivarium(int length, int width, int height) {
		this.length = length;
		this.width = width;
		this.height = height;
		this.intact = true;
		this.inhabitant = null;
	}

	private int length;
	private int width;
	private int height;
	private boolean intact;
	private Animals inhabitant;

	public int volume() {
		int vol = -1;
		vol = this.length * this.width * this.height;
		return vol;
	}

	public boolean isFree() {
		boolean free = true;
		if (this.inhabitant != null) {
			return false;
		}
		return free;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isIntact() {
		return intact;
	}

	public void setIntact(boolean intact) {
		this.intact = intact;
	}

	public Animals getInhabitant() {
		return inhabitant;
	}

	public void setInhabitant(Animals inhabitant) {
		this.inhabitant = inhabitant;
	}
	
	// TODO:
	public String toString() {
		String s = "";
		return s;
	}

}
