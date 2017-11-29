package game;

import java.net.*;
import java.util.ArrayList;

public class server {
	private final static int PORT = 6603;
	private static ServerSocket welcome;
	private static Socket player;
	public static int turn = 0, numPlayers = 0;
	public static game game = new game(false);

	public static boolean gameOn = true;

	public static void main(String args[]) {
		try {
			welcome = new ServerSocket(PORT);
			while (true) {
				player = welcome.accept();
				System.out.println("connected successfully");
				numPlayers++;

			}

		} catch (Exception e) {

		}

	}

}

class clientHandler implements Runnable {

	Socket player;
	String playerName;
	int port;
	int playerNum;
	ArrayList<soldier> playerSoldiers;

	public clientHandler(Socket socket, int playerNum) {
		this.playerNum = playerNum;
		player = socket;
	}

	public void run() {
		int[] move, livingTroops;
		soldier current;
		try {
			while (server.gameOn) {

				while (server.turn % server.numPlayers != playerNum)
					;
				Net_Util.send(player, server.gameOn);
				livingTroops = server.game.getLivingTroops(playerNum);
				Net_Util.send(player, livingTroops);

				playerSoldiers = server.game.getPlayerTroops().get(playerNum);
				for (int i = 0; i < playerSoldiers.size(); i++) {

					move = Net_Util.recIntArr(player);
					for (soldier s : playerSoldiers) {
						if (s.getX() == move[0] && s.getY() == move[1]) {
							current = s;
						}
					}

				}
				
				
			}
			Net_Util.send(player, server.gameOn);
			player.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
