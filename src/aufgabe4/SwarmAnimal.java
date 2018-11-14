package aufgabe4;

public interface SwarmAnimal extends SocialAnimal {
	/*
	 * "Ein Tier, das sich in der Luft oder im Wasser zumindest zeitweise im Schwarm
	 * fortbewegen kann und dabei nur an wenigen Nachbartieren orientiert."
	 * 
	 * Die Betonung liegt hierbei auf fortbewegen, statt leben. ground = 0 ist nicht
	 * sinnvoll.
	 */

	/* Entweder benötigen wir hier ein History-Constraint, dass swarm() nicht
	 * aufgerufen wird wenn kein Schwarm existiert oder dieser Fall wird in der
	 * Implementierung der Methode direkt berücksichtigt
	 * 
	 * Auf jeden Fall allerdings gilt hier, dass nach swarm() this nach wie vor
	 * entweder Teil eines Schwarms oder vllt. immer noch einzeln ist
	 * (-> keine Nachbedingung) */
	void swarm();

	void leave();
	// Nachbedingung für leave() - this ist in keinem Schwarm
}
