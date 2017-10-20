package game;

public final class Vector {

	public static final Vector DEFAULT=new Vector(0,0);
	private final int x,y;
	
	public Vector(int xi, int yi) {
		x=xi;
		y=yi;
	}
	public Vector(Vector other) {
		x=other.X();
		y=other.Y();
	}
	public int X() {
		return x;
	}
	public int Y() {
		return y;
	}
	public Vector setX(int xi) {
		return new Vector(xi,this.Y());
	}
	public Vector setY(int yi) {
		return new Vector(this.X(),yi);
	}
	
	
	
	

}
