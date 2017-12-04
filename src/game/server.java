package game;

import java.net.*;
import java.util.ArrayList;

public class server {
	private final static int  PORT = 6603;
	private static ServerSocket welcome;
	private static Socket player;
	public static int playerTurn = 0;
	public static int playerReady = 0;
	private static int players = 0;
	public static boolean gameOn = false;
	public static game board;
	
	public static void main(String args[]){
		ArrayList<ClientHandler> playerList = new ArrayList<ClientHandler>();
		try{
			welcome = new ServerSocket(PORT);
			while(true){
				player = welcome.accept();
				System.out.println("connected successfully");
				ClientHandler handler = new ClientHandler(player, players);
				playerList.add(handler);

				new Thread(handler).start();
				players++;
					
				//this should allow us to start a new game once everyone has selected that they are ready
				if(playerReady > 0 && playerReady == players){
					board = new game();
					gameOn = true;
					break;
				}
				
				
			}
		board.placeTroopsBoring();
		int winner = board.checkWinner();
		while(winner < 0){
			for(int i = 0; i < players; i++){
				playerTurn = i;
				while(playerList.get(i).getPlaying()){
					
					
				}
				winner = board.checkWinner();	
			}
			
		}
		
		
		
		
	}catch(Exception e){
		
	}
	

}
	
	
	
}


class ClientHandler implements Runnable{
	boolean playing = false;
	Socket player;
	String playerName;
	int port;
	int playerNumber;
	public ClientHandler(Socket socket, int playerNum){
		playerNumber = playerNum;
		player = socket;
		try{
			
			
			
			
			
			
			
			
		}catch(Exception e){
			
		}
		
		
	}
	
	public boolean getPlaying(){
		return playing;
	}

	@Override
	public void run() {
		boolean running = true;
	
		String[] command;
		
		
		while(running){
			try{
				
				command = Net_Util.recString(player).split(" ");
				//has the game started yet? if not, let's check if this player
				//is ready to begin. If so, increment the number of ready players.
				if(!server.gameOn && command[0].equalsIgnoreCase("ready")){
					server.playerReady++;
				}
				if(playerNumber == server.playerTurn && server.board.getLivingTroops(playerNumber).length > 0){
					playing = true;
				//each player will get three actions (move or attack) regardless 
				//of the number of troops
					if(command[0].startsWith("move")){
						int[] coordinates = new int[command.length - 2];
						for(int i = 0; i < command.length; i++ ){
							if(i == 0){
							
							}else{
								coordinates[i-1] = Integer.parseInt(command[i]);
							}
						//once we create the board, here's where we'll first convert
						//the coordinates into a soldier and then move that soldier
							soldier mover = server.board.getTroop(coordinates[0], coordinates[1]);
							server.board.move(mover, coordinates[2], coordinates[3]);
							Net_Util.send(player, server.board.lineOfSight(mover));
						}
					}else if(command[0].startsWith("attack")){
					
						int[] coordinates = new int[command.length - 2];
						for(int i = 0; i < command.length; i++ ){
							if(i == 0){
							
							}else{
								coordinates[i-1] = Integer.parseInt(command[i]);
							}
						}
						server.board.attack(coordinates[1], coordinates[2]);

					}

				}else if(server.board.getLivingTroops(playerNumber).length == 0){
					playing = false;
				}
	
			}catch(Exception e){
				
			}
		}
		
	}
	
	public void startPlayerTurn(){
		Net_Util.send(player, server.board.checkWinner() < 0);
		Net_Util.send(player, server.board.getLivingTroops(playerNumber));
			
		
		
		
		
	}
	
	
	
	
	
}