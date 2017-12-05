package game;

import java.net.*;
import java.util.ArrayList;

public class server {
	public static int SO_TIMEOUT =1000;
	private final static int PORT = 6603;
	private static ServerSocket welcome;
	private static Socket player;

	public static volatile int playerTurn = 0;
	public static volatile int playersReady = 0;
	private static int players = 0;
	public static boolean gameOn = false;
	public static game board;
	
	public final static int partySize = 4;
	

	public static void main(String args[]) {
		
		
		//playersReady = 0;
		ArrayList<ClientHandler> playerList = new ArrayList<ClientHandler>();
		try {

			welcome = new ServerSocket(PORT);
			
			while (partySize > players) {		
				
				player = welcome.accept();
				System.out.println("connected successfully");

				ClientHandler handler = new ClientHandler(player, players);
				playerList.add(handler);

				new Thread(handler).start();
				players++;

				// this should allow us to start a new game once everyone has
				// selected that they are ready
				
				
				}
			}catch(Exception e){
				
			}
			//boolean playTurn = true;
			board = new game();
			gameOn = true;
			System.out.println("game starting");
			board.NumPlayers(players);
			board.placeTroopsBoring();
			int winner = board.checkWinner();
			while (winner < 0) {
				for (int i = 0; i < players; i++) {
					
					//System.out.print("it is currently this player's turn: " + i);
					playerTurn = i;
					playerList.get(i).playing = true;
					playerList.get(i).startPlayerTurn();
					
					//System.out.println(" Player whose turn it is: " + playerList.get(i).playerNumber);
					while (playerList.get(i).playing) {
						//System.out.print("is the player playing? " + playerList.get(i).playing);
						System.out.print("");
						
						
					}
					winner = board.checkWinner();
				}
				//playTurn = true;

			}

		
	}
	
	public static void playersReady(){
		playersReady++;
	}
}

class ClientHandler implements Runnable {
	public boolean playing = false;

	Socket player;
	String playerName;
	int port;

	int playerNumber;

	public ClientHandler(Socket socket, int playerNum) {
		playerNumber = playerNum;

		player = socket;

	}

	public boolean getPlaying() {
		return playing;
	}

	@Override
	public void run() {
		boolean running = true;

		String[] command;

		while (running) {
			try {
				

				//command = Net_Util.recString(player).split(" ");
				// has the game started yet? if not, let's check if this player
				// is ready to begin. If so, increment the number of ready
				// players.
				//System.out.println(" Server game on is " + server.gameOn);
				if (server.gameOn && server.board.getLivingTroops(playerNumber).length > 0 && playing) {
					command = Net_Util.recString(player).split(" ");

					// each player will get three actions (move or attack)
					// regardless
					// of the number of troops
					for (int x = 0; x < 3; x++) {
						if (command[0].startsWith("move")) {
							int[] coordinates = new int[command.length - 2];
							for (int i = 0; i < command.length; i++) {
								if (i == 0) {

								} else {
									//skipping the first string in the array (move), we map each integer to coordinates
									coordinates[i - 1] = Integer.parseInt(command[i]);
								}
								// once we create the board, here's where we'll
								// first convert
								// the coordinates into a soldier and then move
								// that soldier
								soldier mover = server.board.getTroop(coordinates[0], coordinates[1]);
								server.board.move(mover, coordinates[2], coordinates[3]);
								int[] LoS = server.board.lineOfSight(mover);
								String[] spottedEnemies = new String[LoS.length];
								for(int j = 0; j < LoS.length; j++){
									spottedEnemies[j] = "" + LoS[j];
								}
								
								Net_Util.send(player, spottedEnemies);
							}
						} else if (command[0].startsWith("attack")) {

							int[] coordinates = new int[command.length - 2];
							for (int i = 0; i < command.length; i++) {
								if (i == 0) {

								} else {
									coordinates[i - 1] = Integer.parseInt(command[i]);
								}
							}
							server.board.attack(coordinates[1], coordinates[2]);

						}
						command = Net_Util.recString(player).split(" ");
					}

				} else if (server.board.getLivingTroops(playerNumber).length == 0) {
					playing = false;
				}

			} catch (Exception e) {

			}
		}

	}

	public void startPlayerTurn() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("starting player's turn " + System.currentTimeMillis());
		System.out.println(" check winner returns " +server.board.checkWinner());
		System.out.println(" Player port number is " + player.getPort());
		System.out.println(" Server port number is " + player.getLocalPort());
		playing = true;
		
		/*
		boolean gameContinue = server.board.checkWinner() < 0;
		Net_Util.send(player, gameContinue);
		*/
		String boolbool;
		if(!(server.board.checkWinner() < 0)){
		 boolbool = "false";
		}else{
		boolbool = "true";
		}
		Net_Util.send(player, boolbool);
		
		//System.out.println(" get living troops length " + server.board.getLivingTroops(playerNumber).length);
		int[] liveTroops = server.board.getLivingTroops(playerNumber);
		String[] troops = new String[liveTroops.length];
		for(int i = 0; i < liveTroops.length; i++){
			troops[i] = "" + liveTroops[i];
		}
		Net_Util.send(player, troops);
		
		
		//server.board.getPlayerTroops().get(playerNumber);
		
		ArrayList<Integer> spottedEnemies = new ArrayList<Integer>();
		for(soldier troop: server.board.getPlayerTroops().get(playerNumber)){
			for(int x: server.board.lineOfSight(troop)){
				spottedEnemies.add(x);
			}
		}
		String[] spotted = new String[spottedEnemies.size()];
		for(int i = 0; i < spottedEnemies.size(); i++){
			spotted[i] = "" + spottedEnemies.get(i);
		}
		Net_Util.send(player, spotted);
		//System.out.println("finished starting their turn " + System.currentTimeMillis());
	}
}
