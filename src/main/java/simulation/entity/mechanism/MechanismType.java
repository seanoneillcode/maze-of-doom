package simulation.entity.mechanism;

import core.Vector;

public enum MechanismType {

	DOOR("nodes/doorImage.png", new Vector(16,16), true),
	PLATE("nodes/plateImage.png", new Vector(16,16), false),
	ACTIVE_PLATE("nodes/plateImage.png", new Vector(16,16), false);
	
	private String image;
	private Vector size;
	private boolean covers;
	
	private MechanismType(String image, Vector size, boolean covers) {
		this.image = image;
		this.size = size;
		this.covers = covers;
	}
	
	public boolean getCovers() {
		return covers;
	}
	
	public Vector getSize() {
		return size;
	}
	
	public String getImage() {
		return image;
	}
}
