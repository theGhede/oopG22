package augabe8;

public class Test {

	public static void main(String[] args) {
		Swarm swarm = new Swarm();
		swarm.startSwarm();
		for (Fish f : swarm.getSwarm()) {
			// System.out.println(f.getName() + ", " + f.isAlive());
			// System.out.println(f.getX() + ", " + f.getY());
			if (f.getWaitCount() == 32)
				System.out.println("32");
		}
		swarm.print(0);
	}
}
