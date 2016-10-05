package simulation.entity.state;

public class TimedAction<T> {

	private Float timing;
	private T action;
	
	public TimedAction(T action, Float timing) {
		this.action = action;
		this.timing = timing;
	}
	
	public Float getTiming() {
		return timing;
	}

	public T getAction() {
		return action;
	}
}
