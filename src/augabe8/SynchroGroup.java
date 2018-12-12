package augabe8;

import java.util.Stack;

public class SynchroGroup {

	private Stack<Swarm> group;

	public SynchroGroup(int n) {
		this.group = new Stack<>();
		for (int i = 0; i < n; i++) {
			Swarm s = new Swarm(i);
			s.setSg(this);
			s.makeSwarm();
			this.group.add(s);
		}
		this.choose();
	}

	public void choose() {
		int i = 0;
		for (Swarm swarm : group) {
			int choosing = (int) (Math.random() * 96 + 96 * i);
			for (Fish fish : swarm.getSwarm()) {
					fish.setChosen(choosing);
			}
			i++;
		}
	}

	public void start() {
		for (Swarm swarm : group) {
			swarm.start();
		}
	}
}
