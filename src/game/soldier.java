package game;

public class soldier {
	
	private int numSteps;
	private int numAttacks;
	private int sight;
	private int x;
	private int y;
	
	public soldier(){
		numSteps = 3;
		numAttacks = 1;
		sight = 3;
		x = 0;
		y = 0;
		
	}
	
	
	public soldier(int step, int attack, int vision, int xpos, int ypos){
		numSteps = step;
		numAttacks = attack;
		sight = vision;
		x = xpos;
		y = ypos;
	}

	public void setX(int xpos){
		x = xpos;
	}
	
	public void setY(int ypos){
		y = ypos;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public int getNumSteps(){
		return numSteps;
	}
	
	public int getSight(){
		return sight;
	}
	
	
}
