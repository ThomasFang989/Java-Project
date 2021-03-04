/*
 * Authors: Thomas Fang and Shinho Kim
 * Date: 2021-01-31
 * User Manual:
 * This is a JAVA assignments "Design Cycle". We made the connect 4. It allows user to type down their names and put them on the labels on top.
 * It will count how many times player 1 and 2 won. You can reset the board. When someone won or tie, it will show up messages. 
 * It allows the players to click the buttons on the bottom. You should make 4 balls connected, either diagonal, vertical, or horizontal to win.
 * 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;



public class Connect41 implements ActionListener{
	//This declares the variables that will be used below.
	JFrame frame = new JFrame();
	JButton[][] button = new JButton[5][5];
	int[][] board = new int[5][5];
	final int BLANK = 0;
	final int X_MOVE = 1;
	final int O_MOVE = 2;
	final int X_TURN = 0;
	final int O_TURN = 1;
	int turn = X_TURN;
	Container center = new Container();
	JLabel xLabel = new JLabel("X wins:0");
	JLabel oLabel = new JLabel("O wins:0");
	JButton xChangeName = new JButton("Change X's Name.");
	JButton oChangeName = new JButton("Change O's Name.");
	JButton reset = new JButton("Reset");
	JTextField xChangeField = new JTextField();
	JTextField oChangeField = new JTextField();
	Container north = new Container();
	String xPlayerName = "X";
	String oPlayerName = "O";
	int xwins = 0;
	int owins = 0;
	
	public Connect41()  {
		//This sets the frame.
		frame.setSize(800, 400);
		frame.setLayout(new BorderLayout());
		center.setLayout(new GridLayout(5,5));
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[0].length; j++) {
				button[j][i] = new JButton(j+","+i);
				center.add(button[j][i]);
				button[j][i].addActionListener(this);
				button[j][i].setEnabled(false);
			}
			
		}
		for (int i = 0; i < button.length; i++) {
			button[i][4].setEnabled(true);
		}
		frame.add(center, BorderLayout.CENTER);
		north.setLayout(new GridLayout(5,5));
		north.add(xLabel);
		north.add(oLabel);
		north.add(xChangeName);
		xChangeName.addActionListener(this);
		north.add(oChangeName);
		oChangeName.addActionListener(this);
		reset.addActionListener(this);
		north.add(xChangeField);
		north.add(oChangeField);
		north.add(reset);
		frame.add(north, BorderLayout.NORTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	


	public static void main(String[] args) {
		new Connect41();
	}


	
	public void actionPerformed(ActionEvent event) {
		JButton current;
		boolean gridButton = false;
		if (event.getSource().equals(reset)) {
			clearBoard();
								}
		else {
			for (int i = 0; i < button.length; i ++) {
			for (int j = 0; j < button[0].length; j++) {
					
				if (event.getSource().equals(button[j][i])) {
				gridButton = true;
				current = button[j][i];
				if (board[j][i] == BLANK) {
					if (turn == X_TURN) {
						current.setText("X");
						button[j][i].setBackground(Color.RED);
						button[j][i].setOpaque(true);
						board[j][i] = X_MOVE;
						turn = O_TURN;
					}
					
					else {
						current.setText("O");
						button[j][i].setBackground(Color.YELLOW);
						button[j][i].setOpaque(true);
						board[j][i] = O_MOVE;
						turn = X_TURN;
					}
					if (i > 0) {
						button[j][i-1].setEnabled(true);
					}
					
					//check for wins and ties
					if (checkWin(X_MOVE) == true) {
						//X wins
						xwins++;
						xLabel.setText(xPlayerName + "  wins: " + xwins);
						JOptionPane.showMessageDialog(frame, "X won this time!");
						clearBoard();
						}
					else if (checkWin(O_MOVE) == true) {
						//O wins
						owins++; 
						oLabel.setText(oPlayerName + "  wins: " + owins);
						JOptionPane.showMessageDialog(frame, "Y won this time!");
						clearBoard();
					}
					else if (checkTie() == true) {
						JOptionPane.showMessageDialog(frame, "It is a tie!");
						clearBoard();
						//Tie
					}
				}
			}
		}
			}
	

		
		if (gridButton == false) {
			if (event.getSource().equals(xChangeName) == true) {
				xPlayerName = xChangeField.getText();
				xLabel.setText(xPlayerName + " wins:" + xwins);
			}
			else if (event.getSource().equals(oChangeName) == true) {
				oPlayerName = oChangeField.getText();
				oLabel.setText(oPlayerName + " wins:" + owins);
			}
			//else {
			//	JButton current1;
			//	for (int row = 0; row < button.length; row ++) {
			//		for (int column = 0; column < button[0].length; column++) {
			//			board[row][column] = X_MOVE;
			//			current1 = button[row][column] ;
			//			current1.setText("X");
			//			current1.setEnabled(false);
			//			if (checkWin(X_MOVE) == true) {
			//				System.out.println("X wins!");
			//				
			//			}
			//		}
			//	}
			//}
		}
			}
	}
	//Here is all the conditions that a player might wins.
	public boolean checkWin(int player) {
		//vertical
		if (board[0][1] == player && board[0][2] == player && board[0][3] == player && board[0][4] == player) {
			return true;
		}
		if (board[1][1] == player && board[1][2] == player && board[1][3] == player && board[1][4] == player) {
			return true;
		}
		if (board[2][1] == player && board[2][2] == player && board[2][3] == player && board[2][4] == player) {
			return true;
		}
		if (board[3][1] == player && board[3][2] == player && board[3][3] == player && board[3][4] == player) {
			return true;
		}
		if (board[4][1] == player && board[4][2] == player && board[4][3] == player && board[4][4] == player) {
			return true;
		}
		//--------------
		if (board[0][0] == player && board[0][1] == player && board[0][2] == player && board[0][3] == player) {
			return true;
		}
		if (board[1][0] == player && board[1][1] == player && board[1][2] == player && board[1][3] == player) {
			return true;
		}
		if (board[2][0] == player && board[2][1] == player && board[2][2] == player && board[2][3] == player) {
			return true;
		}
		if (board[3][0] == player && board[3][1] == player && board[3][2] == player && board[3][3] == player) {
			return true;
		}
		if (board[4][0] == player && board[4][1] == player && board[4][2] == player && board[4][3] == player) {
			return true;
		}
		//horizontal
		if (board[1][0] == player && board[2][0] == player && board[3][0] == player && board[4][0] == player) {
			return true;
		}
		if (board[1][1] == player && board[2][1] == player && board[3][1] == player && board[4][1] == player) {
			return true;
		}
		if (board[1][2] == player && board[2][2] == player && board[3][2] == player && board[4][2] == player) {
			return true;
		}
		if (board[1][3] == player && board[2][3] == player && board[3][3] == player && board[4][3] == player) {
			return true;
		}
		if (board[1][4] == player && board[2][4] == player && board[3][4] == player && board[4][4] == player) {
			return true;
		}
		//-----------------------
		if (board[0][0] == player && board[1][0] == player && board[2][0] == player && board[3][0] == player) {
			return true;
		}
		if (board[0][1] == player && board[1][1] == player && board[2][1] == player && board[3][1] == player) {
			return true;
		}
		if (board[0][2] == player && board[1][2] == player && board[2][2] == player && board[3][2] == player) {
			return true;
		}
		if (board[0][3] == player && board[1][3] == player && board[2][3] == player && board[3][3] == player) {
			return true;
		}
		if (board[0][4] == player && board[1][4] == player && board[2][4] == player && board[3][4] == player) {
			return true;
		}
		//diagonal
		if (board[0][0] == player && board[1][1] == player && board[2][2] == player && board[3][3] == player) {
			return true;
		}
		if (board[0][4] == player && board[1][3] == player && board[2][2] == player && board[3][1] == player) {
			return true;
		}
		//----------
		if (board[1][1] == player && board[2][2] == player && board[3][3] == player && board[4][4] == player) {
			return true;
		}
		if (board[1][3] == player && board[2][2] == player && board[3][1] == player && board[4][0] == player) {
			return true;
		}
		if (board[0][1] == player && board[1][2] == player && board[2][3] == player && board[3][4] == player) {
			return true;
		}
		if (board[1][0] == player && board[2][1] == player && board[3][2] == player && board[4][3] == player) {
			return true;
		}
		if (board[1][4] == player && board[2][3] == player && board[3][2] == player && board[4][1] == player) {
			return true;
		}
		if (board[0][3] == player && board[1][2] == player && board[2][1] == player && board[3][0] == player) {
			return true;
		}
		
		return false;
	}
	
	//tie condition
	public boolean checkTie() {
		for (int row = 0; row < board.length; row++) {
			for (int column = 0; column < board[0].length; column++) {
				if (board[row][column] == BLANK) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	//This method clears the board.
	public void clearBoard() {
		for (int a = 0; a < board.length; a++) {
			for (int b = 0; b < board[0].length; b++) {
				button[a][b].setEnabled(false);
				board[a][b] = BLANK;
				button[a][b].setText("");
				button[a][b].setBackground(Color.LIGHT_GRAY);
				button[a][b].setOpaque(true);
			}
		}
		for (int i = 0; i < button.length; i++) {
			button[i][4].setEnabled(true);
		}
		turn = X_TURN;
	}
}





