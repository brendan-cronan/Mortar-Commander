package game;

import java.util.ArrayList;

public class GameState {

	Cell[][] game;
	private int boardSize;

	public GameState(int sizeOfBoard) {
		boardSize = sizeOfBoard;
		game = new Cell[boardSize][boardSize];

		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {

				game[i][j] = new Cell(i, j);

			}
		}
	}

	public ArrayList<Troop> getTroops() {
		ArrayList<Troop> out = new ArrayList<Troop>();
		for (int i = 0; i < boardSize; i++)
			for (int j = 0; j < boardSize; j++)
				if (!game[i][j].isEmpty())
					out.add(game[i][j].getTroop());

		return out;
	}

	public void clearBoard() {
		for (int i = 0; i < boardSize; i++)
			for (int j = 0; j < boardSize; j++)
				game[i][j] = null;
	}
	

	/*
	 * @param two coordinates for old position and new position
	 * @return the Troop that was removed if the Cell was occupied.
	 */
	public Troop moveTroop(int x, int y, int nx, int ny) {
		Troop temp=game[x][y].removeTroop();
		if(game[nx][ny].isEmpty()) {
			game[nx][ny].addTroop(temp);
			return null;
		}
		else {
			return null;
		}
		
	}
	public void moveTroop(Troop t, int newX, int newY) {
		moveTroop(t.getPos(),new Vector(newX,newY));
	}

	public void moveTroop(Vector oldV, Vector newV) {
		moveTroop(oldV.X(),oldV.Y(),newV.X(),newV.Y());
	}

	public void moveTroop(Cell oldC, Cell newC) {
		moveTroop(oldC.getPos(),newC.getPos());
		
	}

}
