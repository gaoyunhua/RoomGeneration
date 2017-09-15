package roomgeneration;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;

public class RoomGen {

	private static final int DEFAULT_FRAME_WIDTH = 900;
	private static final int DEFAULT_FRAME_HEIGHT = 700;

	private static final int BUTTON_RESTART_WIDTH = 90;
	private static final int BUTTON_RESTART_HEIGHT = 30;
	private static final int BUTTON_RESTART_X = 20;
	private static final int BUTTON_RESTART_Y = 630;

	final static int CORRIDOR_SIZE = 8;
	final static int CORRIDOR_POSSIBLE_POSITIONS = 4;
	final static int DOOR_SIZE = 4;
	final static int ROOM_HEIGHT_MIN = 20;
	final static int ROOM_WIDTH_MIN = 20;
	final static int ROOM_HEIGHT_MAX = 160;
	final static int MAX_ROOM_WIDTH = 160;

	
	static ArrayList<Rectangle> rec;
	static ArrayList<Corridor> corridors;
	static ArrayList<Block> listUnfinished;
	static ArrayList<Room> listFinished;
	static ArrayList<Room> rooms;
	static ArrayList<Block> allElements;
	static HashMap<Block, ArrayList<Block>> neighbouringBlocks;
	
	private static JFrame frame;
	private static JButton restart;
	private static WholeImage image;
	
	public static void main(String[] args) {

		init();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
		
		restart.setSize(BUTTON_RESTART_WIDTH, BUTTON_RESTART_HEIGHT);
		restart.setLocation(BUTTON_RESTART_X, BUTTON_RESTART_Y);

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

	private static void init() {

		rec = new ArrayList<>();
		corridors = new ArrayList<>();
		listUnfinished = new ArrayList<>();
		listFinished = new ArrayList<>();
		rooms = new ArrayList<>();
		allElements = new ArrayList<>();
		neighbouringBlocks = new HashMap<>();

		frame = new JFrame("roomgeneration.Room Generation");
		restart = new JButton("Restart");
		image = new WholeImage();
	}
	
	static Block split(Block currentBlock) {
		int rand = (int) Math.ceil(Math.random() * 2);

		// base case
		if ((currentBlock.getWidth() <= MAX_ROOM_WIDTH && currentBlock.getHeight() <= ROOM_HEIGHT_MAX) &&
		    (currentBlock.getWidth() >= ROOM_WIDTH_MIN && currentBlock.getHeight() >= ROOM_HEIGHT_MIN)) {

			listFinished.add(new Room(currentBlock));
			return currentBlock;

		} else {
			
			if(currentBlock.getWidth() <= MAX_ROOM_WIDTH && currentBlock.getWidth()>= ROOM_WIDTH_MIN &&
					currentBlock.getHeight() > ROOM_HEIGHT_MAX){
				rand = 1;
			}else if(currentBlock.getWidth() > MAX_ROOM_WIDTH && currentBlock.getHeight() <= ROOM_HEIGHT_MAX &&
					currentBlock.getHeight()>= ROOM_HEIGHT_MIN){
				rand = 2;
			}
			
			switch (rand) {
			// horizontal division
			case 1:

				int y = (int) (Math.random() * (currentBlock.getHeight() - 40)) + 20;
		
				Block blockU = new Block(currentBlock.getX(), currentBlock.getY(), currentBlock.getWidth(), y);
				Block blockD = new Block(currentBlock.getX(), currentBlock.getY() + y, currentBlock.getWidth(),
						currentBlock.getHeight() - y);

				listUnfinished.add(blockD);

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

	static void createCorridors(){
		
		for(Block currentBlock : listFinished){

			final int currentX = currentBlock.getX();
			final int currentY = currentBlock.getY();
			final int currentWidth = currentBlock.getWidth();
			final int currentHeight = currentBlock.getHeight();

			switch((int)(Math.random()*(CORRIDOR_POSSIBLE_POSITIONS))){
			
			//corridor on left
			case 0:
				Corridor corridorL = new Corridor(currentX, currentY, CORRIDOR_SIZE, currentHeight);
				Room roomL = new Room(currentX + CORRIDOR_SIZE, currentY, currentWidth - CORRIDOR_SIZE,
						               currentHeight);

				corridors.add(corridorL);
				rooms.add(roomL);
				break;
			//corridor on right
			case 1:
				Corridor corridorR = new Corridor(currentX + (currentWidth - CORRIDOR_SIZE),
						                     currentY, CORRIDOR_SIZE, currentHeight);
				Room roomR = new Room(currentX, currentY, currentWidth - CORRIDOR_SIZE, currentHeight);

				corridors.add(corridorR);
				rooms.add(roomR);
				break;
			//corridor up
			case 2:
				Corridor corridorU = new Corridor(currentX, currentY, currentWidth, CORRIDOR_SIZE);
				Room roomU = new Room(currentX, currentY + CORRIDOR_SIZE, currentWidth,
									  currentHeight - CORRIDOR_SIZE);
				corridors.add(corridorU);
				rooms.add(roomU);
				break;
			//corridor down
			case 3:
				Corridor corridorD = new Corridor(currentX, currentY + (currentHeight - CORRIDOR_SIZE), currentWidth,
											 CORRIDOR_SIZE);
				Room roomD = new Room(currentX, currentY, currentWidth, currentHeight - CORRIDOR_SIZE);

				corridors.add(corridorD);
				rooms.add(roomD);
				break;
			default:
				System.out.println("mistakes were made in corridors creation");
				break;
				
			}
		}
	}  

	static void clearAll(){
		rec.clear();
		corridors.clear();
		listUnfinished.clear();
		listFinished.clear();
		rooms.clear();
		allElements.clear();
		neighbouringBlocks.clear();
		
	}
	}

