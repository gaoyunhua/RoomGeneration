
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
	public static ArrayList<Corridor> corridor = new ArrayList<>();
	static ArrayList<Block> list_unfinished = new ArrayList<>();
	static ArrayList<Room> list_finished = new ArrayList<>();
	static ArrayList<Room> rooms = new ArrayList<>();
	static ArrayList<Block> all_elements = new ArrayList<>();
	static HashMap<Block, ArrayList<Block>> blocks_around = new HashMap<>();
	
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
		
		
	//	if (b.getWidth() < 160 && b.getHeight() < 160) {
		if ((currentBlock.getWidth() <= MAX_ROOM_WIDTH && currentBlock.getHeight() <= MAX_ROOM_HEIGHT) && (currentBlock.getWidth() >= MIN_ROOM_WIDTH && currentBlock.getHeight()>=MIN_ROOM_HEIGHT)) {
			list_finished.add(new Room(currentBlock));
			return currentBlock;
		} else {
			
			if(currentBlock.getWidth() <= MAX_ROOM_WIDTH && currentBlock.getWidth()>= MIN_ROOM_WIDTH && currentBlock.getHeight() > MAX_ROOM_HEIGHT){
				rand = 1;
			}else if(currentBlock.getWidth() > MAX_ROOM_WIDTH && currentBlock.getHeight() <= MAX_ROOM_HEIGHT && currentBlock.getHeight()>=MIN_ROOM_HEIGHT){
				rand = 2;
			}//else if(b.getWidth()< MIN_ROOM_WIDTH)
			
			switch (rand) {

			// horizontal division
			case 1:

				int y = (int) (Math.random() * (currentBlock.getHeight()-40))+20;
		
				Block blockU = new Block(currentBlock.getX(), currentBlock.getY(), currentBlock.getWidth(),
						y);
				Block blockD = new Block(currentBlock.getX(), currentBlock.getY() + y, currentBlock.getWidth(),
						currentBlock.getHeight() - y);

				list_unfinished.add(blockD);
				if(blockU.getY()<100){
					rand = 2;
				}

				return split(blockU);

				// vertical division
			case 2:

				int x = (int) (Math.random() * (currentBlock.getWidth()-40))+20;
				
				Block blockL = new Block(currentBlock.getX(), currentBlock.getY(), x,
						currentBlock.getHeight());
				Block blockR = new Block(currentBlock.getX() + x, currentBlock.getY(), currentBlock.getWidth() - x,
						currentBlock.getHeight());

				list_unfinished.add(blockR);

				return split(blockL);
			default:
				System.out.println("mistakes were made");
				return currentBlock;
			}
		}

	}

	public static void createCorridors(){
		
		for(int i =0;i<list_finished.size();i++){
			switch((int)(Math.random()*(4))){
			
			
			//left
			case 0:
				Corridor recL = new Corridor(list_finished.get(i).getX(),list_finished.get(i).getY(),CORRIDOR_SIZE,list_finished.get(i).getHeight());
				Room roomL = new Room(list_finished.get(i).getX()+CORRIDOR_SIZE, list_finished.get(i).getY(),list_finished.get(i).getWidth()-CORRIDOR_SIZE,list_finished.get(i).getHeight());
				corridor.add(recL);
				rooms.add(roomL);
				break;
			//right
			case 1:
				Corridor recR = new Corridor(list_finished.get(i).getX()+(list_finished.get(i).getWidth()-CORRIDOR_SIZE),list_finished.get(i).getY(),CORRIDOR_SIZE,list_finished.get(i).getHeight());
				Room roomR = new Room(list_finished.get(i).getX(), list_finished.get(i).getY(),list_finished.get(i).getWidth()-CORRIDOR_SIZE,list_finished.get(i).getHeight());
				corridor.add(recR);
				rooms.add(roomR);
				break;
			//up
			case 2:
				Corridor recU = new Corridor(list_finished.get(i).getX(),list_finished.get(i).getY(),list_finished.get(i).getWidth(),CORRIDOR_SIZE);
				Room roomU = new Room(list_finished.get(i).getX(), list_finished.get(i).getY()+CORRIDOR_SIZE,list_finished.get(i).getWidth(),list_finished.get(i).getHeight()-CORRIDOR_SIZE);
				corridor.add(recU);
				rooms.add(roomU);
				break;
			//down
			case 3:
				Corridor recD = new Corridor(list_finished.get(i).getX(),list_finished.get(i).getY()+(list_finished.get(i).getHeight()-CORRIDOR_SIZE),list_finished.get(i).getWidth(),CORRIDOR_SIZE);
				Room roomD = new Room(list_finished.get(i).getX(), list_finished.get(i).getY(),list_finished.get(i).getWidth(),list_finished.get(i).getHeight()-CORRIDOR_SIZE);
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

