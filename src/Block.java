import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;

class Block extends JPanel {
	private int x;
	private int y;
	private int width;
	private int height;
	private int index;
	private ArrayList<Block> blocksAround;
	private Rectangle rectangle;
	private int currentDoorNumber = 0;
	private int potentialDoorNumber = 0;
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

	int RightSide = this.getX() + this.getWidth();
	int BottomSide = this.getY() + this.getHeight();
	
	boolean isOnRight(Block block) {
		int BlockBottomSide = block.getY() + block.getHeight();
		
		boolean isThisYSmaller =  this.getY() <= block.getY();
		boolean isBlockYSmaller = block.getY() <= BottomSide;
	
		
		
		return block.getX() == RightSide
				&& ((isThisYSmaller && isBlockYSmaller)
						|| (block.getY() + block.getHeight() >= this.getY() && (block.getY() + block
								.getHeight()) <= BottomSide) || (block
						.getY() <= this.getY() && (block.getY() + block.getHeight()) >= BottomSide));
	}
	boolean isOnLeft(Block b){
		return ((b.getX()+b.getWidth()) == this.getX()) && ((this.getY() <= b.getY() && b.getY() <= this.getY()
				+ this.getHeight()) || (b.getY() + b.getHeight() >= this.getY() && (b.getY() + b
						.getHeight()) <= this.getY() + this.getHeight()) || (b
				.getY() <= this.getY() && (b.getY() + b.getHeight()) >= this
				.getY() + this.getHeight()) );
				
	}
	boolean isUp(Block b){
		return ((b.getY()+b.getHeight()) == this.getY() && ((this.getX() <= b.getX() && b.getX() <= this.getX()
				+ this.getWidth()) || (b.getX() + b.getWidth() >= this.getX() && (b.getX() + b
						.getWidth()) <= this.getX() + this.getWidth()) || (b
				.getX() <= this.getX() && (b.getX() + b.getWidth()) >= this
				.getX() + this.getWidth()) ));
	}	
	boolean isDown(Block b){
		return b.getY() == this.getY() + this.getHeight()
				&& ((this.getX() <= b.getX() && b.getX() <= this.getX()
				+ this.getWidth())
				|| (b.getX() + b.getWidth() >= this.getX() && (b.getX() + b
						.getWidth()) <= this.getX() + this.getWidth()) || (b
				.getX() <= this.getX() && (b.getX() + b.getWidth()) >= this
				.getX() + this.getWidth()));
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Rectangle getRec() {
		return rectangle;
	}

	public void setIndex(int i) {
		index = i;
	}

	public int getIndex() {
		return index;
	}

	public void setDoorNum() {
		potentialDoorNumber = (int) (Math.random()*DOOR_NUMBER_MAX) + DOOR_NUMBER_MIN;
		System.out.println(potentialDoorNumber);
	}

	public int getPotentialDoorNum(){
		return potentialDoorNumber;
	}

	public int getCurrentDoorNum(){
		return currentDoorNumber;
	}

	public void addDoor(){
		currentDoorNumber++;
	}
	
	public void setDoor(int x,int y, boolean horizontal){
		if(horizontal){
			doors.add(new Line2D.Float(x, y,x+RoomGen.DOOR_SIZE,y));
		}else{
			doors.add(new Line2D.Float(x, y,x,y+RoomGen.DOOR_SIZE));
		}
	}
	public ArrayList<Line2D> getDoors(){
		return doors;
	}
	

	public boolean isTouching(Block b) {
		return (isOnRight(b) || isOnLeft(b) || isUp(b) || isDown(b));
	}

	// create arraylist for blocks around
	public void generateBlocksAround() {

		blocksAround = new ArrayList<>();
		for (int i = 0; i < RoomGen.all_elements.size(); i++) {
			if (RoomGen.all_elements.get(i).getIndex() == this.getIndex()) {
				index = i;
			} else if (this.isTouching(RoomGen.all_elements.get(i))) {
				blocksAround.add(RoomGen.all_elements.get(i));
			} else {
			}
		}
		RoomGen.blocks_around.put(this, blocksAround);

	}
}
