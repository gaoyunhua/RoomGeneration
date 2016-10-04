import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Block extends JPanel {
	private int x;
	private int y;
	private int lengthX;
	private int lengthY;
	private int index;
	private ArrayList<Block> blocks_around;
	private Rectangle div;
	private int current_door_num = 0;
	private int potential_door_num = 0;
	private ArrayList<Line2D> doors;
	
	private final int DOOR_NUMBER_MIN = 1;
	private final int DOOR_NUMBER_MAX = 3;
	

	public Block(int x, int y, int lengthX, int lengthY) {
		this.x = x;
		this.y = y;
		this.lengthX = lengthX;
		this.lengthY = lengthY;

		doors = new ArrayList<Line2D>(); 
		div = new Rectangle(x, y, lengthX, lengthY);
		RoomGen.rec.add(div);

	}

	public boolean isOnRight(Block b) {
		return b.getX() == this.getX() + this.getLX()
				&& ((this.getY() <= b.getY() && b.getY() <= this.getY()
						+ this.getLY())
						|| (b.getY() + b.getLY() >= this.getY() && (b.getY() + b
								.getLY()) <= this.getY() + this.getLY()) || (b
						.getY() <= this.getY() && (b.getY() + b.getLY()) >= this
						.getY() + this.getLY()));
	}
	public boolean isOnLeft(Block b){
		return ((b.getX()+b.getLX()) == this.getX()) && ((this.getY() <= b.getY() && b.getY() <= this.getY()
				+ this.getLY()) || (b.getY() + b.getLY() >= this.getY() && (b.getY() + b
						.getLY()) <= this.getY() + this.getLY()) || (b
				.getY() <= this.getY() && (b.getY() + b.getLY()) >= this
				.getY() + this.getLY()) );
				
	}
	public boolean isUp(Block b){
		return ((b.getY()+b.getLY()) == this.getY() && ((this.getX() <= b.getX() && b.getX() <= this.getX()
				+ this.getLX()) || (b.getX() + b.getLX() >= this.getX() && (b.getX() + b
						.getLX()) <= this.getX() + this.getLX()) || (b
				.getX() <= this.getX() && (b.getX() + b.getLX()) >= this
				.getX() + this.getLX()) ));
	}	
	public boolean isDown(Block b){
		return b.getY() == this.getY() + this.getLY()
				&& ((this.getX() <= b.getX() && b.getX() <= this.getX()
				+ this.getLX())
				|| (b.getX() + b.getLX() >= this.getX() && (b.getX() + b
						.getLX()) <= this.getX() + this.getLX()) || (b
				.getX() <= this.getX() && (b.getX() + b.getLX()) >= this
				.getX() + this.getLX()));
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getLX() {
		return lengthX;
	}

	public int getLY() {
		return lengthY;
	}

	public Rectangle getRec() {
		return div;
	}

	public void setIndex(int i) {
		index = i;
	}

	public int getIndex() {
		return index;
	}

	public void setDoorNum(){
		potential_door_num = (int) (Math.random()*DOOR_NUMBER_MAX) + DOOR_NUMBER_MIN;
		System.out.println(potential_door_num);
	}
	public int getPotentialDoorNum(){
		return potential_door_num;
	}
	public int getCurrentDoorNum(){
		return current_door_num;
	}
	public void addDoor(){
		current_door_num++;
	}
	public void setDoor(int x,int y, boolean horizontal){
		if(horizontal){
			doors.add(new Line2D.Float(x, y,x+RoomGen.DOOR_SIZE,y));
		}else{
			doors.add(new Line2D.Float(x, y,x,y+RoomGen.DOOR_SIZE));
		}
	}
	
	// decide whether the block are touching
	public boolean touching(Block b) {
		if (isOnRight(b) || isOnLeft(b) || isUp(b) || isDown(b)) {
			return true;
		} else {
			return false;
		}
	}

	// create arraylist for blocks around
	public void generateBlocksAround() {
		int index = 0;

		blocks_around = new ArrayList<Block>();
		for (int i = 0; i < RoomGen.all_elements.size(); i++) {
			if (RoomGen.all_elements.get(i).getIndex() == this.getIndex()) {
				index = i;
			} else if (this.touching(RoomGen.all_elements.get(i))) {
				blocks_around.add(RoomGen.all_elements.get(i));
			} else {
			}
		}
		RoomGen.blocks_around.put(this, blocks_around);

	}
}
