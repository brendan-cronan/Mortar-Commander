package game;

public class Cell {

	private Vector pos = Vector.DEFAULT;
	private Troop troop;

	public Cell(int x, int y) {
		pos = new Vector(x, y);
	}

	public Cell(int x, int y, Troop t) {
		troop = t;
		pos = new Vector(x, y);
	}

	public Cell(Vector position) {
		pos = new Vector(position);
	}

	public Cell(Vector position, Troop t) {
		troop = t;
		pos = new Vector(position);
	}

	public Vector getPos() {
		return pos;
	}
	
	public Troop getTroop() {
		return troop;
	}
	public Troop removeTroop() {
		Troop temp=troop;
		troop=null;
		return temp;
	}
	/*
	 * @return the Troop that was in the Cell previously.Null if unOccupied.
	 * 
	 */
	public Troop addTroop(Troop newTroop) {
		if(troop==null) {
			troop=newTroop;
			return null;
		}
		else {
			Troop temp=troop;
			troop=newTroop;
			return temp;
		}
		
	}
	
	public boolean isEmpty() {
		return (troop == null) ? true : false;
	}

}
