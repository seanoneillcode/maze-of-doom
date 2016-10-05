package simulation.entity.mechanism;

import java.util.HashMap;
import java.util.Map;

public class MechanismMessageServer {

	Map<String, MeschanismMessageClient> registry;
	
	private static MechanismMessageServer instance = new MechanismMessageServer();
	public static MechanismMessageServer getInstance() {
		return instance;
	}
	private MechanismMessageServer() {
		registry = new HashMap<String, MeschanismMessageClient>();
	}
	
	public void register(String id, MeschanismMessageClient client) {
		registry.put(id, client);
	}
	
	public void unregister(String id) {
		registry.remove(id);
	}

	public void send(String id, boolean value) {
		if (registry.containsKey(id)) {
			registry.get(id).receive(value);
		}
	}
}
