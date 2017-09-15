
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;

public class RoomGen {
	
	final static int CORRIDOR_SIZE = 8;
	final static int DOOR_SIZE = 4;
	final static int MIN_ROOM_HEIGHT = 20;
	final static int MIN_ROOM_WIDTH = 20;
	
	final static int MAX_ROOM_HEIGHT = 160;
	final static int MAX_ROOM_WIDTH = 160;
	
	public static ArrayList<Rectangle> rec = new ArrayList<>();
	public static ArrayList<Corridor> corridors = new ArrayList<>();
	static ArrayList<Block> listUnfinished = new ArrayList<>();
	static ArrayList<Room> listFinished = new ArrayList<>();
	static ArrayList<Room> rooms = new ArrayList<>();
	static ArrayList<Block> allElements = new ArrayList<>();
	static HashMap<Block, ArrayList<Block>> neighbouringBlocks = new HashMap<>();
	
	public static JFrame frame = new JFrame("Room Generation");
	public static JButton restart = new JButton("Restart");
	public static WholeImage image = new WholeImage();
	
	public static void main(String[] args) {

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 700);
		
		restart.setSize(90, 30);
		restart.setLocation(20, 630);

		frame.add(restart);
		frame.add(image);
		
		restart.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				
				image.removeAll();
				clearAll();
				WholeImage.clearAll();
				image = new WholeImage();
				image.revalidate();
				frame.repaint();
			}});
		
		frame.validate();
		frame.getContentPane();
		frame.repaint();
		frame.setVisible(true);

	}
	
	public static Block split(Block currentBlock) {
		int rand = (int) Math.ceil(Math.random()*2);

		// base case
		if ((currentBlock.getWidth() <= MAX_ROOM_WIDTH && currentBlock.getHeight() <= MAX_ROOM_HEIGHT) &&
				(currentBlock.getWidth() >= MIN_ROOM_WIDTH && currentBlock.getHeight() >= MIN_ROOM_HEIGHT)) {
			listFinished.add(new Room(currentBlock));
			return currentBlock;
		} else {
			
			if(currentBlock.getWidth() <= MAX_ROOM_WIDTH && currentBlock.getWidth()>= MIN_ROOM_WIDTH && currentBlock.getHeight() > MAX_ROOM_HEIGHT){
				rand = 1;
			}else if(currentBlock.getWidth() > MAX_ROOM_WIDTH && currentBlock.getHeight() <= MAX_ROOM_HEIGHT && currentBlock.getHeight()>=MIN_ROOM_HEIGHT){
				rand = 2;
			}
			
			switch (rand) {

			// horizontal division
			case 1:

				int y = (int) (Math.random() * (currentBlock.getHeight() - 40)) + 20;
		
				Block blockU = new Block(currentBlock.getX(), currentBlock.getY(), currentBlock.getWidth(),
						y);
				Block blockD = new Block(currentBlock.getX(), currentBlock.getY() + y, currentBlock.getWidth(),
						currentBlock.getHeight() - y);

				listUnfinished.add(blockD);
				if(blockU.getY()<100){
				}

				return split(blockU);

				// vertical division
			case 2:

				int x = (int) (Math.random() * (currentBlock.getWidth() - 40)) + 20;
				
				Block blockL = new Block(currentBlock.getX(), currentBlock.getY(), x,
						currentBlock.getHeight());
				Block blockR = new Block(currentBlock.getX() + x, currentBlock.getY(), currentBlock.getWidth() - x,
						currentBlock.getHeight());

				listUnfinished.add(blockR);

				return split(blockL);
			default:
				System.out.println("mistakes were made");
				return currentBlock;
			}
		}

	}

	public static void createCorridors(){
		
		for(Block currentBlock : listFinished){

			switch((int)(Math.random()*(4))){
			
			//left
			case 0:
				Corridor recL = new Corridor(currentBlock.getX(), currentBlock.getY(),
											 CORRIDOR_SIZE, currentBlock.getHeight());
				Room roomL = new Room(currentBlock.getX() + CORRIDOR_SIZE, currentBlock.getY(),
						              currentBlock.getWidth() - CORRIDOR_SIZE, currentBlock.getHeight());
				corridors.add(recL);
				rooms.add(roomL);
				break;
			//right
			case 1:
				Corridor recR = new Corridor(currentBlock.getX() + (currentBlock.getWidth() - CORRIDOR_SIZE),
						                     currentBlock.getY(), CORRIDOR_SIZE, currentBlock.getHeight());
				Room roomR = new Room(currentBlock.getX(), currentBlock.getY(),
						             currentBlock.getWidth() - CORRIDOR_SIZE, currentBlock.getHeight());
				corridors.add(recR);
				rooms.add(roomR);
				break;
			//up
			case 2:
				Corridor recU = new Corridor(currentBlock.getX(), currentBlock.getY(),
						                     currentBlock.getWidth(), CORRIDOR_SIZE);
				Room roomU = new Room(currentBlock.getX(), currentBlock.getY() + CORRIDOR_SIZE,
						              currentBlock.getWidth(), currentBlock.getHeight() - CORRIDOR_SIZE);
				corridors.add(recU);
				rooms.add(roomU);
				break;
			//down
			case 3:
				Corridor recD = new Corridor(currentBlock.getX(), currentBlock.getY() + (currentBlock.getHeight() - CORRIDOR_SIZE),
						                     currentBlock.getWidth(), CORRIDOR_SIZE);
				Room roomD = new Room(currentBlock.getX(), currentBlock.getY(),
						              currentBlock.getWidth(), currentBlock.getHeight() - CORRIDOR_SIZE);
				corridors.add(recD);
				rooms.add(roomD);
				break;
			default:
				System.out.println("mistakes were made in corridors creation");
				break;
				
			}
		}
	}  

	public static void clearAll(){
		rec.clear();
		corridors.clear();
		listUnfinished.clear();
		listFinished.clear();
		rooms.clear();
		allElements.clear();
		neighbouringBlocks.clear();
		
	}
	}

