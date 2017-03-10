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
	public String clickBox[][] = new String[3][3];
	public int turn = 0;

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
		int paddingX = (int) (width * 0.11), paddingY = (int) (height * 0.11);
		int sizeX = (int) (width * 0.15), sizeY = (int) (height * 0.15);

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				// Draw O
				if(clickBox[i][j] != null){
					if(clickBox[i][j] == "X"){
						g2.drawLine((width / 3 * i) + paddingX, (height / 3 * j) + paddingY, (width / 3 * i) + paddingX * 2, (height / 3 * j) + paddingY * 2);
						g2.drawLine((width / 3 * i) + (paddingX * 2), (height / 3 * j) + paddingY, (width / 3 * i) + (paddingX), (height / 3 * j) + paddingY * 2);
					} else if(clickBox[i][j] == "O"){
						g2.drawArc((width / 3 * i) + paddingX, (height / 3 * j) + paddingY, sizeX, sizeY, 0, 360);
					}
				}
			}
		}
	}
	
	public void gameLog(int aux_i, int aux_j, int mouseX, int mouseY){
		System.out.println("\nBOX " + "["+(aux_i + 1)+"]" + "["+(aux_j + 1)+"]" + " Clicked:" + mouseX + "," + mouseY);
		System.out.println("Turn = " + turn + "  Player " + clickBox[aux_i][aux_j]);
		
		// Print Game
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(clickBox[j][i] == null){
					System.out.print("\t|");
				} else{
					System.out.print("\t|" + clickBox[j][i]);
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
			
			if(clickBox[aux_i][aux_j] != "X" && clickBox[aux_i][aux_j] != "O"){
				turn++;
				
				if(turn == 9){
					//endGame();
				} else if(turn % 2 == 0){
					clickBox[aux_i][aux_j] = "O";
				} else if(turn >= 1){
					clickBox[aux_i][aux_j] = "X";
				} 
				repaint();
			}
			gameLog(aux_i, aux_j, mouseX, mouseY);
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
}
