package com.halycon.maze.simulation.entity.mechanism;

import java.util.List;

import com.halycon.maze.core.Vector;
import com.halycon.maze.simulation.entity.Entity;
import com.halycon.maze.simulation.entity.MovingEntity;

public abstract class Mechanism extends Entity implements MeschanismMessageClient {

	private MechanismType type;
	private boolean active;
	private MechanismMessageServer server = MechanismMessageServer.getInstance();
	private String id;
	List<String> pairIds;
	
	public Mechanism(Vector position, MechanismType type, boolean active, String id, List<String> pairIds) {
		super(position, type.getSize());
		this.type = type;
		setActive(active);
		this.id = id;
		this.pairIds = pairIds;
		server.register(id, this);
	}
	
	public Mechanism(Mechanism mechanism) {
		super(mechanism.getPosition(), mechanism.getSize());
		this.type = mechanism.getType();
		setActive(mechanism.isActive());
		this.id = mechanism.getId();
		this.pairIds = mechanism.getPairIds();
		server.register(id, this);
	}
	
	public List<String> getPairIds() {
		return pairIds;
	}
	
	public String getId() {
		return id;
	}
	
	public MechanismType getType() {
		return type;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public MechanismMessageServer getServer() {
		return server;
	}
	
	public void send(String id, boolean value) {
		getServer().send(id, value);
	}
	
	public abstract void update(MovingEntity entity, float delta);
	public abstract void handleCollision(MovingEntity entity, float delta);
}
