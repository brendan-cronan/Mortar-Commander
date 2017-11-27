package game;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Window extends JPanel{
	
	private JButton[][] board;
	private static JFrame frame;
	private static int sizeOfBoard;
	
	
	public Window(GameState state) {
		board = new JButton[state.game.length][state.game[0].length];
		ButtonListener buttonListener = new ButtonListener();
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
		 frame = new JFrame("Mortar Commander");
		 sizeOfBoard = 10;
		 GameState state = new GameState(sizeOfBoard);
		 Window window = new Window(state);
		 frame.add(window);
		 frame.pack();
		 frame.setVisible(true);
	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println(arg0.getActionCommand());
		}
	}

}
