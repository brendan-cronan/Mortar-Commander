
package game;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Window extends JPanel{
	
	private JButton[][] board;
	//private static JFrame boardFrame;
	//private static JPanel commandFrame;
	private static int sizeOfBoard;
	private static ButtonListener buttonListener;
	private static CommandListener commandListener;
	
	public Window() {
		board = new JButton[sizeOfBoard][sizeOfBoard];
		buttonListener = new ButtonListener();
		commandListener = new CommandListener();
		setLayout(new GridLayout(sizeOfBoard, sizeOfBoard));
		for (int i = 0; i < board.length; i++){
			for (int j = 0; j < board[i].length; j++){
				board[i][j] = new JButton();
				board[i][j].addActionListener(buttonListener);
				board[i][j].setActionCommand(i + " " + j);
				add(board[i][j]);
			}
		}
		
	}
	
	public static void main(java.lang.String[] args){
		
		// create the window
		JFrame boardFrame = new JFrame("Mortar Commander");
		boardFrame.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// DO_NOTHING_ON_CLOSE
		/*
		boardFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (h.serverSocket != null) {
					String a =  "quit\n" ;
					Net_Util.send(h.serverSocket, a);
				}
				System.exit(0);
			}
		});
		*/
		
		// ask user for size of board
		/*
		Object[] options = {"5", "6", "7", "8", "9", "10"};
		String s = (String) JOptionPane.showInputDialog(null, "Choose a board size", "Title", 1, null, options, null);
		sizeOfBoard = Integer.parseInt(s);
		*/
		
		// initialize and add game board
		game game = new game(true); // FIXME get input for boolean
		sizeOfBoard = game.BOARD_SIZE;
		Window window = new Window();
		boardFrame.add(window, constraints);
		
		// add some command buttons
		JPanel commandFrame = new JPanel();
		JButton endTurn = new JButton("End Turn");
		endTurn.addActionListener(commandListener);
		endTurn.setActionCommand("endTurn");
		commandFrame.add(endTurn);
		JButton attack = new JButton("Attack");
		attack.addActionListener(commandListener);
		attack.setActionCommand("attack");
		commandFrame.add(attack);
		JButton move = new JButton("Move");
		move.addActionListener(commandListener);
		move.setActionCommand("move");
		commandFrame.add(move);
		constraints.gridy = 1;
		boardFrame.add(commandFrame, constraints);
		
		boardFrame.pack();
		boardFrame.setVisible(true);
	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println(arg0.getActionCommand());
			String[] coords = arg0.getActionCommand().split(" ");
			int x = Integer.parseInt(coords[0]);
			int y = Integer.parseInt(coords[1]);
		}
	}
	
	private class CommandListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println(arg0.getActionCommand());
			String command = arg0.getActionCommand();
			if(command.equals("endTurn")){
				// tell the game
			} else if (command.equals("attack")){
				// tell the game
			} else if (command.equals("move")){
				// tell the game
			}
		}
	}

}

