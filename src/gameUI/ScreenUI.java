package gameUI;

import gameLogic.TicTacToe;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class ScreenUI extends JPanel{
	public static TicTacToe game = new TicTacToe();

	public ScreenUI(){
		MouseHandler mouse = new MouseHandler();
		this.addMouseMotionListener(mouse);
		this.addMouseListener(mouse);
	}

	public void initUI(){
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ScreenUI screen = new ScreenUI();
		frame.add(screen);
		frame.setSize(500,500);
		frame.setVisible(true);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(7));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		game.setWidth(getBounds().width);
		game.setHeight(getBounds().height);

		// Columns
		g2.drawLine(game.getWidth() / 3, 0, game.getWidth() / 3, game.getHeight());
		g2.drawLine(game.getWidth() / 3 * 2, 0, game.getWidth() / 3 * 2, game.getHeight());
		// Rows
		g2.drawLine(0, game.getHeight() / 3, game.getWidth(), game.getHeight() / 3);
		g2.drawLine(0, game.getHeight() / 3 * 2, game.getWidth(), game.getHeight() / 3 * 2);
		drawPlayerMoves(g2);
	}

	public void drawPlayerMoves(Graphics2D g2){
		char gameGrid[][] = game.getGameGrid();
		int paddingX = (int) (game.getWidth() * 0.115), paddingY = (int) (game.getHeight() * 0.115);
		int paddingCircleX = (int) (game.getWidth() * 0.095), paddingCircleY = (int) (game.getHeight() * 0.095);
		int sizeX = (int) (game.getWidth() * 0.15), sizeY = (int) (game.getHeight() * 0.15);

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				// Draw X or O
				if(gameGrid[i][j] == 'X'){
					g2.drawLine((game.getWidth() / 3 * i) + paddingX, (game.getHeight() / 3 * j) + paddingY,
								(game.getWidth() / 3 * i) + paddingX * 2, (game.getHeight() / 3 * j) + paddingY * 2);
					g2.drawLine((game.getWidth() / 3 * i) + (paddingX * 2), (game.getHeight() / 3 * j) + paddingY,
								(game.getWidth() / 3 * i) + (paddingX), (game.getHeight() / 3 * j) + paddingY * 2);
				} else if(gameGrid[i][j] == 'O'){
					g2.drawArc((game.getWidth() / 3 * i) + paddingCircleX, (game.getHeight() / 3 * j) + paddingCircleY,
									sizeX, sizeY, 0, 360);
				}
			}
		}
	}
	
	public void gameLog(int aux_i, int aux_j, int mouseX, int mouseY){
		char gameGrid[][] = game.getGameGrid();
		System.out.println("\nBOX " + "["+(aux_i + 1)+"]" + "["+(aux_j + 1)+"]" + " Clicked:" + mouseX + "," + mouseY);
		System.out.println("\nTurn = " + game.getTurn() + "  Player " + gameGrid[aux_i][aux_j]);
		
		// Print gameGrid in console
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(j == 2){
					System.out.print(" " + gameGrid[j][i]);
				} else{
					System.out.print(" " + gameGrid[j][i] + " |");
				}
			}
			if(i != 2){
				System.out.println("\n-----------");
			}
		}
	}

	private class MouseHandler implements MouseListener, MouseMotionListener{
		@Override
		public void mouseDragged(MouseEvent e) {}
		@Override
		public void mouseMoved(MouseEvent e) {}
		@Override
		public void mouseClicked(MouseEvent e) {
			char gameGrid[][] = game.getGameGrid();
			int aux_i = 0, aux_j = 0;
			int mouseX = e.getX(), mouseY = e.getY();

			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if (mouseX > game.getWidth() / 3 * i) {
						aux_i = i;
					}
					if (mouseY > game.getHeight() / 3 * j) {
						aux_j = j;
					}
				}
			}
			if(gameGrid[aux_i][aux_j] != 'X' && gameGrid[aux_i][aux_j] != 'O' && !game.isGame_finished()){
				game.setTurn(game.getTurn() + 1);
				if((game.getTurn() & 1) == 0){
					gameGrid[aux_i][aux_j] = 'O';
					game.setLast_player('O');
					repaint();
					gameLog(aux_i, aux_j, mouseX, mouseY);
				} else if(game.getTurn() >= 1){
					game.setLast_player('X');
					gameGrid[aux_i][aux_j] = 'X';
					repaint();
					gameLog(aux_i, aux_j, mouseX, mouseY);
				}
				checkGameStatus();
			}
			game.setGameGrid(gameGrid);
		}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
	}

	public void resetGame() {
		char gameGrid[][] = game.getGameGrid();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				gameGrid[i][j] = ' ';
			}
		}
		game.setGameGrid(gameGrid);
		game.setGame_finished(false);
		game.setTurn(0);
		repaint();
	}

	public void checkGameStatus(){
		char gameGrid[][] = game.getGameGrid();
		String scoreboard = "";

		if(game.isGame_finished()){
			System.out.println("\nPlayer " + game.getLast_player() + " WON!\n");

			if(game.getLast_player() == 'X'){
				game.setPlayerXWins(game.getPlayerXWins() + 1);
				scoreboard += "PLAYER \"X\" WON!\n";
			} else if(game.getLast_player() == 'O'){
				game.setPlayerOWins(game.getPlayerOWins() + 1);
				scoreboard += "PLAYER \"O\" WON!\n";
			} if(game.getLast_player() == 'D'){
				scoreboard += "DRAW!\n";
				game.setDraws(game.getDraws() + 1);
			}

			scoreboard += "\n\n--------- SCOREBOARD ---------\n" +
						"\nWins Player X: " + game.getPlayerXWins() +
						"\n\nWins Player O: " + game.getPlayerOWins() +
						"\n\nDraws: " + game.getDraws() + "\n";

			JOptionPane.showMessageDialog(null,  scoreboard, "TicTacToe", JOptionPane.INFORMATION_MESSAGE);
			resetGame();
		} else{
			if ((gameGrid[0][0] == gameGrid[0][1] && gameGrid[0][0] == gameGrid[0][2] && gameGrid[0][0] != ' ')
					|| (gameGrid[1][0] == gameGrid[1][1] && gameGrid[1][0] == gameGrid[1][2] && gameGrid[1][0] != ' ')
					|| (gameGrid[2][0] == gameGrid[2][1] && gameGrid[2][0] == gameGrid[2][2] && gameGrid[2][0] != ' ')
					|| (gameGrid[0][0] == gameGrid[1][0] && gameGrid[0][0] == gameGrid[2][0] && gameGrid[0][0] != ' ')
					|| (gameGrid[0][1] == gameGrid[1][1] && gameGrid[0][1] == gameGrid[2][1] && gameGrid[0][1] != ' ')
					|| (gameGrid[0][2] == gameGrid[1][2] && gameGrid[0][2] == gameGrid[2][2] && gameGrid[0][2] != ' ')
					|| (gameGrid[0][0] == gameGrid[1][1] && gameGrid[0][0] == gameGrid[2][2] && gameGrid[0][0] != ' ')
					|| (gameGrid[0][2] == gameGrid[1][1] && gameGrid[0][2] == gameGrid[2][0] && gameGrid[0][2] != ' ')){
				game.setGame_finished(true);
				checkGameStatus();
			} else if(game.getTurn() == 9){
				game.setGame_finished(true);
				game.setLast_player('D');
				checkGameStatus();
			}
		}
	}
}
