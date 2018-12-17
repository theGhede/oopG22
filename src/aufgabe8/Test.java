package aufgabe8;

public class Test {

	public static void main(String[] args) {
		/*
		 * NOTE:
		 * 
		 * Since "Testläufe der Simulation von Fischschwärmen" is plural
		 * synchronisation with only two classes and multiple swarms proved itself to be
		 * difficult. Synchronized printing requires to synchronize the group of all
		 * swarms which are to be synchronized & this lead to an additional class, which
		 * is a group of n swarms. An upside of having all swarms grouped together makes
		 * adjusting the amount of swarms created and run very easy.
		 * 
		 */
		SynchroGroup group = new SynchroGroup(3);
		group.start();
		/*
		 * NOTE:
		 * 
		 * while the different swarms use a stack the ordering of the output will vary
		 * depending on which swarm finishes first
		 */
	}
}
