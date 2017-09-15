import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

class Block {
	private int x;
	private int y;
	private int width;
	private int height;
	private int index;
	private int currentDoorNumber = 0;
	private int potentialDoorNumber = 0;

	//save each side to simplify checking intersection on sides
	// horizonLine = [x1, length]

	private ArrayList<Block> blocksAround;
	private Rectangle rectangle;
	private ArrayList<Line2D> doors;
	
	private final int DOOR_NUMBER_MIN = 1;
	private final int DOOR_NUMBER_MAX = 5;
	

	Block(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		doors = new ArrayList<>();
		rectangle = new Rectangle(x, y, width, height);
		RoomGen.rec.add(rectangle);

	}

	boolean inRange(int min, int max, int pointA, int pointB) {
		boolean bool =  (pointA >= min && pointA + RoomGen.DOOR_SIZE <= max) ||
				(pointB - RoomGen.DOOR_SIZE >= min && pointB <= max);
		return bool;
	}


	boolean isOnRight(Block block) {
		return block.getX() == getX() + getWidth() &&
				(inRange(getY(), getY() + getHeight(), block.getY(), block.getY() + block.getHeight()) ||
				 inRange(block.getY(), block.getY() + block.getHeight(), getY(), getY() + getHeight()));
	}


	boolean isOnLeft(Block block){
		return block.getX() + block.getWidth() == getX() &&
				(inRange(getY(), getY() + getHeight(), block.getY(), block.getY() + block.getHeight()) ||
				 inRange(block.getY(), block.getY() + block.getHeight(), getY(), getY() + getHeight()));
	}

	boolean isUp(Block block){
		return block.getY() + block.getHeight() == getY() &&
				(inRange(getX(), getX() + getWidth(), block.getX(), block.getX() + block.getWidth()) ||
				 inRange(block.getX(), block.getX() + block.getWidth(), getX(), getX() + getWidth()));
	}	

	boolean isDown(Block block){
		return (block.getY() == getY() + getHeight()) &&
				(inRange(getX(), getX() + getWidth(), block.getX(), block.getX() + block.getWidth()) ||
				inRange(block.getX(), block.getX() + block.getWidth(), getX(), getX() + getWidth()));

	}

	public int getX() {
		return x;
	}

	public int getY() { return y; }

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	Rectangle getRec() {
		return rectangle;
	}

	void setIndex(int i) {
		index = i;
	}

	int getIndex() {
		return index;
	}

	void setPotentialDoorNumber() {
		potentialDoorNumber = (int) (Math.random()*DOOR_NUMBER_MAX) + DOOR_NUMBER_MIN;
	}

	int getPotentialDoorNum() { return potentialDoorNumber; }

	int getCurrentDoorNum(){
		return currentDoorNumber;
	}

	void addDoor(){
		currentDoorNumber++;
	}
	
	void setDoor(int x, int y, boolean horizontal){
		if(horizontal){
			doors.add(new Line2D.Float(x, y,x+RoomGen.DOOR_SIZE,y));
		}else{
			doors.add(new Line2D.Float(x, y,x,y+RoomGen.DOOR_SIZE));
		}
	}

	public ArrayList<Line2D> getDoors(){
		return doors;
	}

	public boolean isTouching(Block block) {
		return (isOnRight(block) || isOnLeft(block) || isUp(block) || isDown(block));
	}

	public void generateBlocksAround() {

		blocksAround = new ArrayList<>();
		for (int i = 0; i < RoomGen.allElements.size(); i++) {
			if (isTouching(RoomGen.allElements.get(i)) && !this.equals(RoomGen.allElements.get(i))) {
				blocksAround.add(RoomGen.allElements.get(i));
			} else {
			}
		}
		RoomGen.neighbouringBlocks.put(this, blocksAround);

	}
}
