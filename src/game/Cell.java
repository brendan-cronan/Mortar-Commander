package game;

public class Cell {

	private Vector pos=Vector.DEFAULT;
	private Troop troop;
	
	
	public Cell(int x,int y) {
		pos=new Vector(x,y);
	}
	public Cell(int x,int y,Troop t) {
		troop=t;
		pos=new Vector(x,y);
	}
	
	
	
	
	public Cell(Vector position) {
		pos=new Vector(position);
	}
	public Cell(Vector position,Troop t) {
		troop=t;
		pos=new Vector(position);
	}
}
