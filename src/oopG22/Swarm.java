package oopG22;

public class Swarm {
	// what kind of swarm is
	String type;
	int swarmsize;
	int minDistance;
	boolean existing;
	// selects first animal to move (type dependent rules)
	int select;
	Animal[] swarm;
}
