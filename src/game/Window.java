package game;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Window extends JPanel{
	
	private JButton[][] board;
	private static JFrame boardFrame;
	private static JPanel commandFrame;
	private static int sizeOfBoard;
	private static ButtonListener buttonListener;
	
	public Window(GameState state) {
		board = new JButton[state.game.length][state.game[0].length];
		buttonListener = new ButtonListener();
		setLayout(new GridLayout(state.game.length, state.game[0].length));
		for (int i = 0; i < board.length; i++){
			for (int j = 0; j < board[i].length; j++){
				board[i][j] = new JButton();
				board[i][j].addActionListener(buttonListener);
				board[i][j].setActionCommand(i + " " + j);
				add(board[i][j]);
			}
		}
		
	}
	
	public static void main(String[] args){
		
		// create the window
		boardFrame = new JFrame("Mortar Commander");
		boardFrame.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// DO_NOTHING_ON_CLOSE
		
		// initialize and add game board
		sizeOfBoard = 10;
		GameState state = new GameState(sizeOfBoard);
		Window window = new Window(state);
		boardFrame.add(window, constraints);
		
		// add some command buttons
		commandFrame = new JPanel();
		JButton endTurn = new JButton("End Turn");
		endTurn.addActionListener(buttonListener);
		endTurn.setActionCommand("endTurn");
		commandFrame.add(endTurn);
		JButton attack = new JButton("Attack");
		attack.addActionListener(buttonListener);
		attack.setActionCommand("attack");
		commandFrame.add(attack);
		JButton move = new JButton("Move");
		move.addActionListener(buttonListener);
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
		}
	}

}
