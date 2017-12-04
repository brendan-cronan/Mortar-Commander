
package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Window extends JPanel {

	private static Window window;
	private Client client;

	private JFrame boardFrame;
	private GridBagConstraints constraints;
	private JButton[][] board;
	private int sizeOfBoard;
	private ButtonListener buttonListener;
	private ArrayList<soldier> playerTroops;
	private ArrayList<soldier> enemyTroops;
	private game theGame;

	public Window() {

		// create the window
		boardFrame = new JFrame("Mortar Commander");
		boardFrame.setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // DO_NOTHING_ON_CLOSE
		// boardFrame.setPreferredSize(new Dimension(250, 10000));
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
		JPanel commandFrame = new JPanel();
		JButton endTurn = new JButton("End Turn");
		endTurn.addActionListener(buttonListener);
		endTurn.setActionCommand("endTurn");
		commandFrame.add(endTurn);
		/*
		 * JButton attack = new JButton("Attack");
		 * attack.addActionListener(buttonListener);
		 * attack.setActionCommand("attack"); commandFrame.add(attack); JButton
		 * move = new JButton("Move"); move.addActionListener(buttonListener);
		 * move.setActionCommand("move"); commandFrame.add(move);
		 */
		constraints.gridy = 1;
		boardFrame.add(commandFrame, constraints);

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
		window = new Window();

	}

	private class Client {

		public Client() {

			// list of user's troops
			playerTroops = new ArrayList<soldier>();
			playerTroops.add(new soldier(3, 1, 3, 5, 5));
			enemyTroops = new ArrayList<soldier>();
			enemyTroops.add(new soldier(3, 1, 3, 7, 7));
			// FIXME get from server where troops are spawned

			updateButtons();

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
			for (soldier troop : playerTroops) {
				int x = troop.getX();
				int y = troop.getY();
				board[x][y].setBackground(Color.GREEN);
				board[x][y].setEnabled(true);

				for (int i = troop.getSight(); i >= (-1 * troop.getSight()); i--) {
					for (int j = troop.getSight(); j >= (-1 * troop.getSight()); j--) {
						if (x + i < sizeOfBoard && y + j < sizeOfBoard && x + i >= 0 && y + j >= 0) {
							board[x + i][y + j].setEnabled(true);
							if (isEnemyTroop(x + i, y + i)) {
								board[x + i][y + i].setBackground(Color.RED);
							} else if (isPlayerTroop(x + i, y + i)) {
								board[x + i][y + i].setBackground(Color.GREEN);
							} else {
								board[x + i][y + i].setBackground(null);
							}
						}
					}
				}
			}
		}

		boolean isPlayerTroop(int x, int y) {
			for (soldier troop : playerTroops) {
				if (troop.getX() == x && troop.getY() == y) {
					return true;
				}
			}
			return false;
		}

		boolean isEnemyTroop(int x, int y) {
			for (soldier troop : enemyTroops) {
				if (troop.getX() == x && troop.getY() == y) {
					return true;
				}
			}
			return false;
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
			System.out.println(arg0.getActionCommand());
			String[] command = arg0.getActionCommand().split(" ");
			if (command[0].equals("endTurn")) {
				// tell the game
			} else if (command[0].equals("attack")) {
				// tell the game
			} else if (command[0].equals("move")) {
				// tell the game
			} else {
				if (firstClick) {
					fromx = Integer.parseInt(command[0]);
					fromy = Integer.parseInt(command[1]);
					if (client.isPlayerTroop(fromx, fromy)) {
						firstClick = false;
					}
				} else {
					tox = Integer.parseInt(command[0]);
					toy = Integer.parseInt(command[1]);
					if (client.isEnemyTroop(tox, toy)) {
						firstClick = true;
						// tell the game (x,y)
					}
				}
			}
		}
	}
}
