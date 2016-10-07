package simulation.entity.mechanism;

import java.util.List;

import core.Vector;

public class MechanismFactory {

	private MechanismFactory(){}
	
	public static Mechanism getMechanism(Mechanism mechanism) {
		Mechanism instance = null;
		switch (mechanism.getMechanismType()) {
		case DOOR:
			instance = new Door(mechanism);
			break;
		case PLATE:
			instance = new PressurePlate(mechanism);
			break;
		case ACTIVE_PLATE:
			instance = new ActivePressurePlate(mechanism);
			break;
		default:
			break;
		
		}
		return instance;
	}
	
	public static Mechanism getMechanism(Vector position, MechanismType mechanismType, boolean active, String id, List<String> pairIds) {
		Mechanism instance = null;
		switch (mechanismType) {
		case DOOR:
			instance = new Door(position, mechanismType, active, id, pairIds);
			break;
		case PLATE:
			instance = new PressurePlate(position, mechanismType, active, id, pairIds);
			break;
		case ACTIVE_PLATE:
			instance = new ActivePressurePlate(position, mechanismType, active, id, pairIds);
			break;
		default:
			break;
		
		}
		return instance;
	}
}
