package Aufgabe4;

public interface SchoolAnimal extends Mammal, SocialAnimal {
	// assertion { expected time spent on land = 0, in air = 0 }

	/*
	 * NOTE: It is curious that following the instructions this is clearly not a
	 * SwarmAnimal or HerdAnimal (Context says: this is not a Swarm & this is
	 * aquatic while HerdAnimal is terrestrial), those types (and their subtypes)
	 * have a method to leave their social group but this has none
	 */
}
