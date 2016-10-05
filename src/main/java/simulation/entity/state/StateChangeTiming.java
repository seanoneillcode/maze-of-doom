package simulation.entity.state;

import java.util.List;


public class StateChangeTiming<T> {

	private Float timing;
	private List<TimedAction<T>> actions;
	private int currentIndex;
	boolean running;
	
	public StateChangeTiming(List<TimedAction<T>> actions) {
		this.actions = actions;
	}
	
	public void start() {
		if (!running) {
			timing = new Float(0);
			running = true;
		}
	}
	
	public TimedAction<T> getCurrentAction() {
		return actions.get(currentIndex);
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void update(float delta) {
		if (running) {
			timing += delta;
			float currentTime = actions.get(currentIndex).getTiming();
			if (timing > currentTime) {
				timing -= currentTime;
				currentIndex++;
				if (currentIndex > actions.size() - 1) {
					currentIndex = 0;
					running = false;
				}
			}
		}
	}
}
