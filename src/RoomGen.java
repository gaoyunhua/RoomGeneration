import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
	
	public static ArrayList<Rectangle> rec = new ArrayList<Rectangle>();
	public static ArrayList<Rectangle> corridor = new ArrayList<Rectangle>();
	static ArrayList<Block> list_unfinished = new ArrayList<Block>();
	static ArrayList<Room> list_finished = new ArrayList<Room>();
	
	final static int room_size = 5;

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
		
		
		if (b.getLX() < 160 && b.getLY() < 160) {
			list_finished.add(new Room(b));
			return b;
		} else {
			
			if(b.getLX() < 160 && b.getLY() > 160){
				r = 1;
			}else if(b.getLX() > 160 && b.getLY() < 160){
				r = 2;
			}
			
			switch (r) {

			// horizontal line
			case 1:

				int y = (int) (Math.random() * (b.getLY()-40))+20;
		
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

				int x = (int) (Math.random() * (b.getLX()-40))+20;
				
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
		
		for(int i =0;i<list_finished.size();i++){
			switch((int)(Math.random()*(4))){
			
			
			//left
			case 0:
				Rectangle recL = new Rectangle(list_finished.get(i).getX(),list_finished.get(i).getY(),room_size,list_finished.get(i).getLY());
				corridor.add(recL);
				break;
			//right
			case 1:
				Rectangle recR = new Rectangle(list_finished.get(i).getX()+(list_finished.get(i).getLX()-room_size),list_finished.get(i).getY(),room_size,list_finished.get(i).getLY());
				corridor.add(recR);
				break;
			//up
			case 2:
				Rectangle recU = new Rectangle(list_finished.get(i).getX(),list_finished.get(i).getY(),list_finished.get(i).getLX(),room_size);
				corridor.add(recU);
				break;
			//down
			case 3:
				Rectangle recD = new Rectangle(list_finished.get(i).getX(),list_finished.get(i).getY()+(list_finished.get(i).getLY()-room_size),list_finished.get(i).getLX(),room_size);
				corridor.add(recD);
				break;
			default:
				System.out.println("mistakes were made in corridor creation");
				break;
				
			}
		}
	}  
	}

