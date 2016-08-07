public class Room{
	private int x;
	private int y;
	private int lengthX;
	private int lengthY;
	
	int type;
	
	public Room(Block b){
		x = b.getX();
		y = b.getY();
		lengthX = b.getLX();
		lengthY = b.getLY();
	}
	public int chooseType(){
		type = (int) (Math.random()*(2));
		System.out.println("type"+type);
		
		return type;
	}

}
