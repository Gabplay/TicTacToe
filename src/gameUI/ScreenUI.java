package gameUI;

import java.awt.*;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ScreenUI extends JPanel{
	// TODO: remove global variables
	public char gameGrid[][] = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
	public int turn = 0;
	public boolean game_finished = false;
	public char last_player = ' ';

	public ScreenUI(){
		MouseHandler mouse = new MouseHandler();
		this.addMouseMotionListener(mouse);
		this.addMouseListener(mouse);
	}

	public void initFrame(){
		JFrame frame = new JFrame();
		ScreenUI screen = new ScreenUI();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(screen);
		frame.setSize(400,400);
		frame.setVisible(true);
		setMinimumSize(getSize());
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int width = getBounds().width, height = getBounds().height;
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(7));

		// Columns
		g2.drawLine(width / 3, 0, width / 3, height);
		g2.drawLine(width / 3 * 2, 0, width / 3 * 2, height);
		// Rows
		g2.drawLine(0, height / 3, width, height / 3);
		g2.drawLine(0, height / 3 * 2, width, height / 3 * 2);
		drawPlayerMoves(g2);
	}

	public void drawPlayerMoves(Graphics2D g2){
		int width = getBounds().width, height = getBounds().height;
		int paddingX = (int) (width * 0.115), paddingY = (int) (height * 0.115);
		int paddingCircleX = (int) (width * 0.095), paddingCircleY = (int) (height * 0.095);
		int sizeX = (int) (width * 0.15), sizeY = (int) (height * 0.15);

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				// Draw X or O
				if(gameGrid[i][j] == 'X'){
					g2.drawLine((width / 3 * i) + paddingX, (height / 3 * j) + paddingY,
								(width / 3 * i) + paddingX * 2, (height / 3 * j) + paddingY * 2);
					g2.drawLine((width / 3 * i) + (paddingX * 2), (height / 3 * j) + paddingY,
								(width / 3 * i) + (paddingX), (height / 3 * j) + paddingY * 2);
				} else if(gameGrid[i][j] == 'O'){
					g2.drawArc((width / 3 * i) + paddingCircleX, (height / 3 * j) + paddingCircleY,
									sizeX, sizeY, 0, 360);
				}
			}
		}
	}
	
	public void gameLog(int aux_i, int aux_j, int mouseX, int mouseY){
		System.out.println("\nBOX " + "["+(aux_i + 1)+"]" + "["+(aux_j + 1)+"]" + " Clicked:" + mouseX + "," + mouseY);
		System.out.println("Turn = " + turn + "  Player " + gameGrid[aux_i][aux_j]);
		
		// Print Game
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(gameGrid[j][i] == ' '){
					System.out.print("\t|");
				} else{
					System.out.print("\t|" + gameGrid[j][i]);
				}
			}
			System.out.println();
		}
	}

	private class MouseHandler implements MouseListener, MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent e) {}
		@Override
		public void mouseMoved(MouseEvent e) {}
		@Override
		public void mouseClicked(MouseEvent e) {
			int width = getBounds().width, height = getBounds().height;
			int aux_i = 0, aux_j = 0;
			int mouseX = e.getX(), mouseY = e.getY();

			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if (mouseX > width / 3 * i) {
						aux_i = i;
					}
					if (mouseY > height / 3 * j) {
						aux_j = j;
					}
				}
			}
			if(gameGrid[aux_i][aux_j] != 'X' && gameGrid[aux_i][aux_j] != 'O' && !game_finished){
				turn++;
				if((turn & 1) == 0){
					gameGrid[aux_i][aux_j] = 'O';
					last_player = 'O';
					repaint();
					gameLog(aux_i, aux_j, mouseX, mouseY);
				} else if(turn >= 1){
					last_player = 'X';
					gameGrid[aux_i][aux_j] = 'X';
					repaint();
					gameLog(aux_i, aux_j, mouseX, mouseY);
				}
				endGame();
			}
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

	public void endGame(){
		if(game_finished){
			System.out.println("\nPlayer " + last_player + " WON!\n");
		} else{
			if ((gameGrid[0][0] == gameGrid[0][1] && gameGrid[0][0] == gameGrid[0][2] && gameGrid[0][0] != ' ')
					|| (gameGrid[1][0] == gameGrid[1][1] && gameGrid[1][0] == gameGrid[1][2] && gameGrid[1][0] != ' ')
					|| (gameGrid[2][0] == gameGrid[2][1] && gameGrid[2][0] == gameGrid[2][2] && gameGrid[2][0] != ' ')
					|| (gameGrid[0][0] == gameGrid[1][0] && gameGrid[0][0] == gameGrid[2][0] && gameGrid[0][0] != ' ')
					|| (gameGrid[0][1] == gameGrid[1][1] && gameGrid[0][1] == gameGrid[2][1] && gameGrid[0][1] != ' ')
					|| (gameGrid[0][2] == gameGrid[1][2] && gameGrid[0][2] == gameGrid[2][2] && gameGrid[0][2] != ' ')
					|| (gameGrid[0][0] == gameGrid[1][1] && gameGrid[0][0] == gameGrid[2][2] && gameGrid[0][0] != ' ')
					|| (gameGrid[0][2] == gameGrid[1][1] && gameGrid[0][2] == gameGrid[2][0] && gameGrid[0][2] != ' ')){
				game_finished = true;
				endGame();
			} else if(turn == 9){
				System.out.println("\nDRAW\n");
			}
		}
	}
}
