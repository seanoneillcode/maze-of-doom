package com.halycon.maze.simulation.entity;

import java.util.ArrayList;
import java.util.List;

import com.halycon.maze.business.DefaultConstants;
import com.halycon.maze.core.Direction;
import com.halycon.maze.core.Vector;
import com.halycon.maze.simulation.entity.state.AttackState;
import com.halycon.maze.simulation.entity.state.StateChangeTiming;
import com.halycon.maze.simulation.entity.state.TimedAction;


public class Weapon extends Entity {

	private String name;
	private StateChangeTiming<AttackState> stateChangeTiming;
	private Direction direction;
	
	public Weapon(String name, Vector position) {
		super(position, new Vector(DefaultConstants.WEAPON_SIZE));
		this.name = name;
		List<TimedAction<AttackState>> actions = new ArrayList<TimedAction<AttackState>>();
		actions.add(new TimedAction<AttackState>(AttackState.IDLE, 0f));
		actions.add(new TimedAction<AttackState>(AttackState.START, 0.06f));
		actions.add(new TimedAction<AttackState>(AttackState.MIDDLE, 0.06f));
		actions.add(new TimedAction<AttackState>(AttackState.END, 0.2f));
		stateChangeTiming = new StateChangeTiming<AttackState>(actions);
	}
	
	public String getName() {
		return name;
	}
	
	public boolean canAttack() {
		return !stateChangeTiming.isRunning();
	}
	
	public AttackState getAttackState() {
		return (AttackState) stateChangeTiming.getCurrentAction().getAction();
	}
	
	public void attack(Direction direction) {
		stateChangeTiming.start();
		this.direction = direction;
	}
	
	public void update(float delta, Vector position) {
		stateChangeTiming.update(delta);
		this.pos = new Vector(position);
		this.size = new Vector(DefaultConstants.WEAPON_SIZE);
		if (stateChangeTiming.getCurrentAction().getAction() == AttackState.END) {
			switch (direction) {
			case DOWN:
				pos.y -= size.y;
				break;
			case UP:
				pos.y += size.y;
				break;
			case LEFT:
				pos.x -= size.x;
				break;
			case RIGHT:
				pos.x += size.x;
				break;
			}
		}
	}
}
