package game;

import java.net.*;

public class server {
	private final static int  PORT = 6603;
	private static ServerSocket welcome;
	private static Socket player;
	
	public static void main(String args[]){
		try{
			welcome = new ServerSocket(PORT);
			while(true){
				player = welcome.accept();
				System.out.println("connected successfully");
				
				
				
				
				
				
				
				
				
				
				
				
				
			}
		
		
		
		
	}catch(Exception e){
		
	}
	

}
	
	
	
}


public class clientHandler implements Runnable{
	
	Socket player;
	String playerName;
	int port;
	
	public clientHandler(Socket socket){
		
		player = socket;
		try{
			
			
			
			
			
			
			
			
		}
		
		
	}
	
	
	
	
	
}