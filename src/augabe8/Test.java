package augabe8;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		Swarm swarm = new Swarm();
		swarm.startSwarm();

		int up = 0;
		int down = 0;
		for (Fish f : swarm.getSwarm()) {
			// System.out.println(f.getName() + ", " + f.isAlive());
			// if (f.getWaitCount() == 32)
			// System.out.println(f.getWaitCount());
		}
	}
}
