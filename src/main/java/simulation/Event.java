package simulation;

public class Event {

	private enum State {
		READY,
		STARTED,
		DONE
	}

	private float timer;
	private float length;
	private State state;
	
	public Event(float length) {
		this.length = length;
		reset();
	}
	
	public boolean isStarted() {
		return state == State.STARTED;
	}
	
	public void start() {
		state = State.STARTED;
	}
	
	public boolean isDone() {
		return state == State.DONE;
	}
	
	public void update(float delta) {
		if (state == State.STARTED) {
			timer += delta;
			if (timer >= length) {
				state = State.DONE;
			}			
		}
	}
	
	public float percentDone() {
		return (timer / length) * 100;
	}
	
	public void reset() {
		state = State.READY;
		timer = 0;
	}
}
