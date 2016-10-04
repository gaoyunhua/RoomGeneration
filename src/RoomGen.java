
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;

public class RoomGen {
	
	public static ArrayList<Rectangle> rec = new ArrayList<Rectangle>();
	public static ArrayList<Corridor> corridor = new ArrayList<Corridor>();
	static ArrayList<Block> list_unfinished = new ArrayList<Block>();
	static ArrayList<Room> list_finished = new ArrayList<Room>();
	static ArrayList<Room> rooms = new ArrayList<Room>();
	static ArrayList<Block> all_elements = new ArrayList<Block>();
	static HashMap<Block, ArrayList<Block>> blocks_around = new HashMap<Block, ArrayList<Block>>();
	
	final static int CORRIDOR_SIZE = 8;
	final static int DOOR_SIZE = 4;

	public static void main(String[] args) {

		JFrame frame = new JFrame("Room Generation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 700);
		
		frame.add(new WholeImage());
		frame.validate();

		frame.getContentPane();
		frame.repaint();
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
	}

