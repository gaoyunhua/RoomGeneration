
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RoomGen {
	
	final static int CORRIDOR_SIZE = 8;
	final static int DOOR_SIZE = 4;
	final static int MIN_ROOM_HEIGHT = 20;
	final static int MIN_ROOM_WIDTH = 20;	
	
	final static int MAX_ROOM_HEIGHT = 160;
	final static int MAX_ROOM_WIDTH = 160;
	
	public static ArrayList<Rectangle> rec = new ArrayList<Rectangle>();
	public static ArrayList<Corridor> corridor = new ArrayList<Corridor>();
	static ArrayList<Block> list_unfinished = new ArrayList<Block>();
	static ArrayList<Room> list_finished = new ArrayList<Room>();
	static ArrayList<Room> rooms = new ArrayList<Room>();
	static ArrayList<Block> all_elements = new ArrayList<Block>();
	static HashMap<Block, ArrayList<Block>> blocks_around = new HashMap<Block, ArrayList<Block>>();
	
	public static JFrame frame = new JFrame("Room Generation");
//	public static JPanel panel = new JPanel();
	public static JButton restart = new JButton("Restart");
	public static WholeImage image = new WholeImage();
	
	public static void main(String[] args) {

		//JFrame frame = new JFrame("Room Generation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 700);
		
//		panel.setSize(700, 600);
		
		restart.setSize(90, 30);
		restart.setLocation(20, 630);
		


		frame.add(restart);
		frame.add(image);
//		frame.add(panel);
		
//		panel.add(new WholeImage());
		
		restart.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				
				image.removeAll();
				clearAll();
				WholeImage.clearAll();
				image = new WholeImage();
				image.revalidate();
				frame.repaint();
			}});
		
//		panel.repaint();
//		panel.revalidate();
//		panel.setVisible(true);
		
		frame.validate();

		frame.getContentPane();
		frame.repaint();
		frame.setVisible(true);

	}
	
	public static Block split(Block b) {
		int r = (int) Math.ceil(Math.random()*2);
		
		
	//	if (b.getLX() < 160 && b.getLY() < 160) {
		if ((b.getLX() <= MAX_ROOM_WIDTH && b.getLY() <= MAX_ROOM_HEIGHT) && (b.getLX() >= MIN_ROOM_WIDTH && b.getLY()>=MIN_ROOM_HEIGHT)) {
			list_finished.add(new Room(b));
			return b;
		} else {
			
			if(b.getLX() <= MAX_ROOM_WIDTH && b.getLX()>= MIN_ROOM_WIDTH && b.getLY() > MAX_ROOM_HEIGHT){
				r = 1;
			}else if(b.getLX() > MAX_ROOM_WIDTH && b.getLY() <= MAX_ROOM_HEIGHT && b.getLY()>=MIN_ROOM_HEIGHT){
				r = 2;
			}//else if(b.getLX()< MIN_ROOM_WIDTH)
			
			switch (r) {

			// horizontal division
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

				// vertical division
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
				Corridor recL = new Corridor(list_finished.get(i).getX(),list_finished.get(i).getY(),CORRIDOR_SIZE,list_finished.get(i).getLY());
				Room roomL = new Room(list_finished.get(i).getX()+CORRIDOR_SIZE, list_finished.get(i).getY(),list_finished.get(i).getLX()-CORRIDOR_SIZE,list_finished.get(i).getLY());
				corridor.add(recL);
				rooms.add(roomL);
				break;
			//right
			case 1:
				Corridor recR = new Corridor(list_finished.get(i).getX()+(list_finished.get(i).getLX()-CORRIDOR_SIZE),list_finished.get(i).getY(),CORRIDOR_SIZE,list_finished.get(i).getLY());
				Room roomR = new Room(list_finished.get(i).getX(), list_finished.get(i).getY(),list_finished.get(i).getLX()-CORRIDOR_SIZE,list_finished.get(i).getLY());
				corridor.add(recR);
				rooms.add(roomR);
				break;
			//up
			case 2:
				Corridor recU = new Corridor(list_finished.get(i).getX(),list_finished.get(i).getY(),list_finished.get(i).getLX(),CORRIDOR_SIZE);
				Room roomU = new Room(list_finished.get(i).getX(), list_finished.get(i).getY()+CORRIDOR_SIZE,list_finished.get(i).getLX(),list_finished.get(i).getLY()-CORRIDOR_SIZE);
				corridor.add(recU);
				rooms.add(roomU);
				break;
			//down
			case 3:
				Corridor recD = new Corridor(list_finished.get(i).getX(),list_finished.get(i).getY()+(list_finished.get(i).getLY()-CORRIDOR_SIZE),list_finished.get(i).getLX(),CORRIDOR_SIZE);
				Room roomD = new Room(list_finished.get(i).getX(), list_finished.get(i).getY(),list_finished.get(i).getLX(),list_finished.get(i).getLY()-CORRIDOR_SIZE);
				corridor.add(recD);
				rooms.add(roomD);
				break;
			default:
				System.out.println("mistakes were made in corridor creation");
				break;
				
			}
		}
	}  
	public static void clearAll(){
		rec.clear();
		corridor.clear();
		list_unfinished.clear();
		list_finished.clear();
		rooms.clear();
		all_elements.clear();
		blocks_around.clear();
		
	}
	}

