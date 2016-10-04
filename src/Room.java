import java.util.ArrayList;
import java.util.HashMap;

public class Room extends Block{

	private int room_type = -1;

	
	public Room(int x,int y, int lengthX,int lengthY){
		super(x, y, lengthX, lengthY);
	}
	public Room(Block b){
		super(b.getX(),b.getY(),b.getLX(),b.getLY());
	}
	public void chooseType(){
		room_type = (int) (Math.random()*(4));
	}
	public int getType(){
		return room_type;
	}
	public String getTypeName(){
		String name = "";
		switch(room_type){
		case 0:name ="DR"; // dinning room
		break;
		case 1:name = "B"; // bathroom
		break;
		case 2:name = "SR"; //storage room
		break;
		case 3:name = "G"; // game room
		break;
		default:System.out.println("type is not yet assigned");
		break;
		
		}
		return name;
	}

}