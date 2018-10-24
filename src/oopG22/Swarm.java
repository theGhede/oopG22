package oopG22;

public class Swarm {
	// what kind of swarm it is
	String type;
	int swarmsize;
	int minDistance;
	// selects first animal to move (type dependent rules)
	int select;
	Animal[] swarm;
	Bird[] flock;
	Insect[] colony;
}
