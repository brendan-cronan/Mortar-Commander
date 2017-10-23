package network;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
public class ClientUtils {
	public static final BufferedReader USER_INPUT=new BufferedReader(new InputStreamReader(System.in));	

	public ClientUtils() {

	
	
	
	
	}
	
	public static Socket connectToServer(String serverIP,int portNumber) {
		try {
			return new Socket(serverIP,portNumber);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static String promptInput() {//return a string asking the user to enter commands and list the commands
		return "";
	}
	
	
	
	
	public void free() {
		
	}

}
