package game;

public abstract class Troop {

	private int speed,vision;
	private Vector pos=Vector.DEFAULT;
	
	public Troop() {
		
	
	}
	
	public Vector getPos() {
		return pos;
	}

}
