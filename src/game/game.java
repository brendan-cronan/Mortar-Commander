package game;
import java.util.*;
public class game {
	private int numPlayers;
	private ArrayList<soldier> allTroops;
	private ArrayList<ArrayList<soldier>> playerTroops;
	private int playerTurn;
	private boolean gameOn;
	private soldier[][] board;
	private final int STARTING_TROOP_COUNT = 3;
	final int BOARD_SIZE = 20;
	private Random r;
	
	public ArrayList<soldier> getAllTroops(){
		return allTroops;
	}
	
	public ArrayList<ArrayList<soldier>> getPlayerTroops(){
		return playerTroops;
	}
	
	public game(){
		playerTurn = 1;
		gameOn = true;
		board = new soldier[BOARD_SIZE][BOARD_SIZE];
		allTroops = new ArrayList<soldier>();
		playerTroops = new ArrayList<ArrayList<soldier>>();
		r = new Random();
		
	}
	
	
	/**  the following method takes the number of players and assigns them troops (3 by default)  */
	public void NumPlayers(int num){
		numPlayers = num;
		for(int i = 0; i < num; i++){
			playerTroops.add(new ArrayList<soldier>());
			for(int j = 0; j < STARTING_TROOP_COUNT; j++){
				soldier s = new soldier();
				allTroops.add(s);
				playerTroops.get(i).add(s);
			}
		}	
	}
	
	
	private boolean checkValid(int target){
		//0 and 400 are the borders of the game board
		if(target < 0 || target > (BOARD_SIZE-1)){
			return false;
		}else{
			return true;
		}
		
	}
	
	public void placeTroopsBoring(){				
		int xpos;
		int ypos;
		for(ArrayList<soldier> player: playerTroops){
			
			//this int tracks which side of the board the troop will spawn on
			int playerNum = playerTroops.indexOf(player) % 4;
			
			for(soldier troop: player){
				do{
			if(0 == playerNum){
				xpos = r.nextInt(BOARD_SIZE);
				ypos = r.nextInt(4);
				
			}else if(1 == playerNum){
				xpos = r.nextInt(4);
				ypos = r.nextInt(BOARD_SIZE);
				
			}else if(2 == playerNum){
				xpos = r.nextInt(4) + BOARD_SIZE -4;
				ypos = r.nextInt(BOARD_SIZE);
				
			}else{
				xpos = r.nextInt(BOARD_SIZE);
				ypos = r.nextInt(4) + BOARD_SIZE -4;
			}
			
			
				}while(board[xpos][ypos] != null);
			
				board[xpos][ypos] = troop;
				troop.setX(xpos);
				troop.setY(ypos);
				
			}
		}
		
	}
	

	
public void placeTroopsCrazy(){
	int xpos;
	int ypos;
	for(soldier troop: allTroops){
		do{
			
			xpos = r.nextInt(BOARD_SIZE);
			ypos = r.nextInt(BOARD_SIZE);
			
			
		}while(null != board[xpos][ypos]);
		
		board[xpos][ypos] = troop;
		troop.setX(xpos);
		troop.setY(ypos);
	}
		
}
	
	

public boolean attack(int xpos, int ypos){
	
	if(checkValid(xpos) && checkValid(ypos)){
	
	if(null != board[xpos][ypos]){
		soldier s = board[xpos][ypos];
		board[xpos][ypos] = null;
		allTroops.remove(s);
		
		for(ArrayList<soldier> player: playerTroops){
			if(player.contains(s)){
				player.remove(s);
			}
		}
		
		return true;
	}
	else{
		return false;
	}
	}
	return false;
	
}


public boolean move(soldier troop, int x, int y){
	boolean xCheck = Math.abs(troop.getX() - x) > troop.getNumSteps() && checkValid(x);
	boolean yCheck = Math.abs(troop.getY() - y) > troop.getNumSteps() && checkValid(y);
	boolean empty = (board[x][y] == null);
	
	
	if(xCheck && yCheck && empty){
		board[x][y] = troop;
		troop.setX(x);
		troop.setY(y);
		return true;
	}
	return false;
	
	
}




public int[] lineOfSight(soldier troop){
	ArrayList<soldier> enemies = new ArrayList<soldier>();
	for(int x = troop.getX() - troop.getSight(); x < troop.getX() + troop.getSight(); x++){
		
		
		for(int y = troop.getY() - troop.getSight(); y < troop.getY() + troop.getSight(); y++){
			if(checkValid(x) && checkValid(y)){
				if(board[x][y] != null){
					enemies.add(board[x][y]);
				}
			}
		}
	}
	ArrayList<Integer> sights = new ArrayList<Integer>();
	for(soldier enemy: enemies){
		sights.add(enemy.getX());
		sights.add(enemy.getY());
	}
	
	int[] positiveSightings = new int[sights.size()];
	for(int i = 0; i < sights.size(); i++){
		positiveSightings[i] = sights.get(i);
	}
	return positiveSightings;
}
	

public void reset(){
	allTroops.clear();
	for(ArrayList<soldier> player: playerTroops){
			player.clear();
	}
	playerTroops.clear();
	
		for (int i = 0; i < BOARD_SIZE; i++){
			for (int j = 0; j < BOARD_SIZE; j++){
				board[i][j] = null;
			}
		}
		
		playerTurn = 1;
		gameOn = true;
	
}

public int checkWinner(){
	if(allTroops.size() > 3){
		return -1;
	}
	
	
	int countPlayers = 0;
	int potentialWinner = -1;
	
	for(ArrayList<soldier> player: playerTroops){
		if(!player.isEmpty()){
			countPlayers++;
			potentialWinner = playerTroops.indexOf(player);
		}
	}
	if(countPlayers == 1){
		gameOn = false;
		return potentialWinner;
	}
	return -1;
}

public ArrayList<soldier> getBoard(){
	return allTroops;
	
}


public int[] getLivingTroops(int player){
	ArrayList<soldier> livingUnits = playerTroops.get(player);
	int[] troopPositions = new int[2 * livingUnits.size()];
	for(int i = 0;i < livingUnits.size(); i++){
		troopPositions[i] += livingUnits.get(i).getX();
		troopPositions[i] += livingUnits.get(i).getY();
		
		
	
	}
	return troopPositions;
}


public boolean getLocationStatus(int x, int y){
	//returns true if location is empty, false if location is filled
	if(board[x][y] == null){
		return true;
	}else{
		return false;
	}
}

public soldier getTroop(int x, int y){
	return board[x][y];
}




}
