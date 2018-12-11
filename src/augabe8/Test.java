package augabe8;

public class Test {

	public static void main(String[] args) {
		Swarm swarm = new Swarm();
		swarm.startSwarm();
		for (Fish f : swarm.getSwarm()) {
			// System.out.println(f.getX() + ", " + f.getY());
		}
		swarm.print(0);
	}
}
