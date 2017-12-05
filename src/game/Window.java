
package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Window extends JPanel {

	private static Window window;
	private static Client client;
	
	private static Socket serverSocket;
	private static JTextField serverIP;
	private JFrame boardFrame;
	private GridBagConstraints constraints;
	private JButton[][] board;
	private int sizeOfBoard;
	private ButtonListener buttonListener;
	private static ConnectListener connectListener;
	private static ArrayList<int[]> playerTroops;
	private static ArrayList<int[]> enemyTroops;
	private static JLabel message;
	private game theGame;
	private static boolean connected;
	private static boolean updated;
	private static boolean isMove;
	private static int actions;

	public Window() {
		
		// connect to server
		JFrame connectPanel = new JFrame("Connect");
		connectPanel.setLayout(new GridBagLayout());
		connectPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagConstraints gridBag = new GridBagConstraints();
		connectListener = new ConnectListener();
		
		serverIP = new JTextField("", 20);
		connectPanel.add(serverIP, gridBag);
		
		JButton connectButton = new JButton("Connect");
		connectButton.addActionListener(connectListener);
		connectButton.setActionCommand("Connect");
		gridBag.gridy = 1;
		connectPanel.add(connectButton, gridBag);
		
		connectPanel.pack();
		connectPanel.setVisible(true);
		
		while(!connected){
			System.out.print(""); // don't remove this
		}
		connectPanel.setVisible(false);
		// create the window
		boardFrame = new JFrame("Mortar Commander");
		boardFrame.setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // DO_NOTHING_ON_CLOSE
		theGame = new game();

		sizeOfBoard = theGame.BOARD_SIZE;
		board = new JButton[sizeOfBoard][sizeOfBoard];
		buttonListener = new ButtonListener();
		setLayout(new GridLayout(sizeOfBoard, sizeOfBoard));
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = new JButton();
				board[i][j].addActionListener(buttonListener);
				board[i][j].setActionCommand(i + " " + j);
				board[i][j].setPreferredSize(new Dimension(30, 30));
				board[i][j].setEnabled(false);
				add(board[i][j]);
			}
		}

		boardFrame.add(this, constraints);

		// add some command buttons
		message = new JLabel("_");
		constraints.gridy = 1;
		boardFrame.add(message, constraints);

		boardFrame.pack();
		boardFrame.setVisible(true);
		client = new Client();
		// tell the server if the user closes the window
		/*
		 * boardFrame.addWindowListener(new java.awt.event.WindowAdapter() {
		 * 
		 * @Override public void windowClosing(java.awt.event.WindowEvent
		 * windowEvent) { if (h.serverSocket != null) { String a = "quit\n" ;
		 * Net_Util.send(h.serverSocket, a); } System.exit(0); } });
		 */
		
	}

	public static void main(String[] args) {

		// ask user for size of board
		/*
		 * Object[] options = {"5", "6", "7", "8", "9", "10"}; String s =
		 * (String) JOptionPane.showInputDialog(null, "Choose a board size",
		 * "Title", 1, null, options, null); sizeOfBoard = Integer.parseInt(s);
		 */
		
		// initialize and add game board
		connected = false;
		window = new Window();
		
		boolean gameOn = true;
		int[] rawPlayerTroops = null;
		int[] rawEnemyTroops = null;
		playerTroops = new ArrayList<int[]>();
		updated = false;
		isMove = false;
		while(gameOn){
			System.out.println("Game is started");
			// is the game still going on?
			try {
				System.out.println("gimme dat phat bool " + System.currentTimeMillis());
				System.out.println("Remote socket: " + serverSocket.getPort());
				System.out.println("Local socket: " + serverSocket.getLocalPort());
				String state = Net_Util.recString(serverSocket);
				if(state.equalsIgnoreCase("true")){
					gameOn = true;
				}else{
					gameOn = false;
				}
				//gameOn = Net_Util.recBool(serverSocket);
				System.out.println("recieved gameOn as " + gameOn);
			} catch (IOException e) {
				System.out.println("Failed to receive boolean from server");
				e.printStackTrace();
				break;
			}
			
			// get new list of player troops
			try {
				System.out.println("trying to receive int array");
				rawPlayerTroops = Net_Util.recIntArr(serverSocket);
				System.out.println("Received player troops");
			} catch (IOException e) {
				System.out.println("Failed to recieve own troop coordinates from server");
				e.printStackTrace();
				break;
			}
			playerTroops.clear();
			for(int i = 0; i < rawPlayerTroops.length; i+=2){
				int[] troop = { rawPlayerTroops[i], rawPlayerTroops[i+1] };
				playerTroops.add(troop);
			}
			
			// get new list of enemy troops
			try {
				rawEnemyTroops = Net_Util.recIntArr(serverSocket);
				System.out.println("Reicieved enemy troops");
			} catch (IOException e) {
				System.out.println("Failed to recieve initial enemy troop coordinates from server");
				e.printStackTrace();
				break;
			}
			enemyTroops.clear();
			for(int i = 0; i < rawEnemyTroops.length; i+=2){
				int[] troop = { rawEnemyTroops[i], rawEnemyTroops[i+1] };
				enemyTroops.add(troop);
			}
			
			// update the board
			client.updateButtons();
			System.out.println("Updated buttons");
			// three actions
			actions = 3;
			while(actions > 0){
				System.out.println("Turn " + (4 - actions));
				// wait for a command to be completed
				while(!updated){
					System.out.print(""); // don't remove this
				}
				
				// if a troop moved, update emeny troops
				if(isMove){
					try {
						rawEnemyTroops = Net_Util.recIntArr(serverSocket);
						enemyTroops.clear();
						for(int i = 0; i < rawEnemyTroops.length; i+=2){
							int[] troop = { rawEnemyTroops[i], rawEnemyTroops[i+1] };
							enemyTroops.add(troop);
						}
					} catch (IOException e) {
						System.out.println("Failed to recieve enemy troop coordinates from server after moving");
						e.printStackTrace();
					}
					client.updateButtons();
				}
				updated = false;
				actions--;
			}
		}
	}
	
	void updateMessage(String newmessage){
		message.setText(newmessage);
	}

	private class Client {

		public Client() {

			// list of user's troops
			/*
			playerTroops = new ArrayList<soldier>();
			playerTroops.add(new soldier(3, 1, 3, 5, 5));
			enemyTroops = new ArrayList<soldier>();
			enemyTroops.add(new soldier(3, 1, 3, 7, 7));
			*/

			//updateButtons();

		}

		void updateButtons() {

			// disable and discolor all buttons
			for (int i = 0; i < sizeOfBoard; i++) {
				for (int j = 0; j < sizeOfBoard; j++) {
					board[i][j].setEnabled(false);
					board[i][j].setBackground(null);
				}
			}

			// set color and enabled of JButtons
			for (int h = 0; h < playerTroops.size(); h+=2) {
				int x = playerTroops.get(h)[0];
				int y = playerTroops.get(h+1)[1];
				board[x][y].setBackground(Color.GREEN);
				board[x][y].setEnabled(true);
				int troopSight = 3;
				for (int i = troopSight; i >= (-1 * troopSight); i--) {
					for (int j = troopSight; j >= (-1 * troopSight); j--) {
						if (x + i < sizeOfBoard && y + j < sizeOfBoard && x + i >= 0 && y + j >= 0) {
							board[x + i][y + j].setEnabled(true);
							int[] coords = { x+i, y+i };
							if (enemyTroops.contains(coords)) {
								board[x + i][y + i].setBackground(Color.RED);
							} else if (playerTroops.contains(coords)) {
								board[x + i][y + i].setBackground(Color.GREEN);
							} else {
								board[x + i][y + i].setBackground(null);
							}
						}
					}
				}
			}
		}
	}

	private class ButtonListener implements ActionListener {

		boolean firstClick = true;
		int fromx;
		int fromy;
		int tox;
		int toy;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(actions > 0){
				System.out.println(arg0.getActionCommand());
				String[] command = arg0.getActionCommand().split(" ");
				int[] coords = { fromx, fromy };
				if (firstClick) {
					fromx = Integer.parseInt(command[0]);
					fromy = Integer.parseInt(command[1]);
					if (playerTroops.contains(coords)) {
						firstClick = false;
					} else {
						window.updateMessage("Select one of your own troops");
					}
				} else {
					tox = Integer.parseInt(command[0]);
					toy = Integer.parseInt(command[1]);
					if (enemyTroops.contains(coords)) {
						String serverCommand =  "attack " + fromx + " " + fromy + " " + tox + " " + toy;
						Net_Util.send(serverSocket, serverCommand);
					} else if(playerTroops.contains(coords)){
						window.updateMessage("Don't attack your own troops");
					} else {
						String serverCommand =  "move " + fromx + " " + fromy + " " + tox + " " + toy;
						Net_Util.send(serverSocket, serverCommand);
						isMove = true;
					}
					firstClick = true;
					updated = true;
				}
			} else {
				window.updateMessage("It is not your turn");
			}
		}
	}
	
	private class ConnectListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			if(arg0.getActionCommand().equals("Connect")){
				try{
					serverSocket = Net_Util.connectToServer( serverIP.getText() ,6603);
					connected = true;
				} catch(Exception e) {
					System.out.println("Failed to connect");
				}
			}
			//Net_Util.send(serverSocket, "ready");
		}
	}
}
