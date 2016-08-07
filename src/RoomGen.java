import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JComboBox.KeySelectionManager;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RoomGen {
	
	public static ArrayList<Line2D> lines = new ArrayList<Line2D>();

	static ArrayList<Block> list_unfinished = new ArrayList<Block>();
	static ArrayList<Block> list_finished = new ArrayList<Block>();
	
	static ArrayList<Room> rooms = new ArrayList<Room>();

	public static void main(String[] args) {

		JFrame frame = new JFrame("FrameDemo");
		frame.addKeyListener(new KeyListener() {
			
			public void keyTyped(KeyEvent e) {
				if(' '==e.getKeyChar())
				{
					//main(null);
				}
			
				
			}
			
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		
		frame.add(new WholeImage());
		frame.validate();

		frame.getContentPane();
		frame.repaint();

		frame.pack();
		frame.setVisible(true);

	}

	public static Block split(Block b) {
		int r = (int) Math.ceil(Math.random()*2);
		
		
		if (b.getLX() < 100 || b.getLY() < 100) {
			list_finished.add(b);
			return b;
		} else {
			switch (r) {

			// horizontal line
			case 1:

				int y = (int) (Math.random() * (b.getLY()-10))+10;
		
				Block blockU = new Block(b.getX(), b.getY(), b.getLX(),
						y);
				Block blockD = new Block(b.getX(), b.getY() + y, b.getLX(),
						b.getLY() - y);

				list_unfinished.add(blockD);
				if(blockU.getY()<100){
					r = 2;
				}

				return split(blockU);

				// vertical line
			case 2:

				int x = (int) (Math.random() * (b.getLX()-10))+10;
				
				Block blockL = new Block(b.getX(), b.getY(), x,
						b.getLY());
				Block blockR = new Block(b.getX() + x, b.getY(), b.getLX() - x,
						b.getLY());

				list_unfinished.add(blockR);

				return split(blockL);
			default:
				System.out.println("mistakes were made");
				return b;
			}
		}

	}

	
	public static void createCorridors(){
		
		int size = 5;
		for(int i =0;i<list_finished.size();i++){
			switch((int)(Math.random()*(4-0))+0){
			
			
			//left
			case 0:
				Line2D lineL = new Line2D.Float(list_finished.get(i).getX()+size, list_finished.get(i).getY(),list_finished.get(i).getX()+size,list_finished.get(i).getY()+list_finished.get(i).getLY());
				lines.add(lineL);
				break;
			//right
			case 1:
				Line2D lineR = new Line2D.Float(list_finished.get(i).getX()+(list_finished.get(i).getLX()-size), list_finished.get(i).getY(),list_finished.get(i).getX()+(list_finished.get(i).getLX()-size),list_finished.get(i).getY()+list_finished.get(i).getLY());
				lines.add(lineR);
				break;
			//up
			case 2:
				Line2D lineU = new Line2D.Float(list_finished.get(i).getX(), list_finished.get(i).getY()+size,list_finished.get(i).getX()+list_finished.get(i).getLX(),list_finished.get(i).getY()+size);
				lines.add(lineU);
				break;
			//down
			case 3:
				Line2D lineD = new Line2D.Float(list_finished.get(i).getX(), list_finished.get(i).getY()+(list_finished.get(i).getLY()-size),list_finished.get(i).getX()+list_finished.get(i).getLX(),list_finished.get(i).getY()+(list_finished.get(i).getLY()-size));
				lines.add(lineD);
				break;
			default:
				System.out.println("mistakes were made in corridor creation");
				break;
				
			}
		}
	}  
	}


