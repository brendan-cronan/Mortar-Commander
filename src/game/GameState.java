package game;

public class GameState {

	Cell[][] game;
	private int boardSize;
	
	public GameState(int sizeOfBoard) {
		boardSize=sizeOfBoard;
		game=new Cell[boardSize][boardSize];
		
		for(int i=0;i<boardSize;i++) {
			for(int j=0;j<boardSize;j++) {
				
				game[i][j]=new Cell(i,j);
				
				
			}
		}
	
	
	
	}

}
