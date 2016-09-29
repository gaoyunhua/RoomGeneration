import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import javax.swing.JPanel;


public class Block extends JPanel{
	private int x;
	private int y;
	private int lengthX;
	private int lengthY;

	Rectangle div;
	
	public Block(int x,int y,int lengthX, int lengthY){
		this.x = x;
		this.y = y;
		this.lengthX = lengthX;
		this.lengthY = lengthY;
        
        div = new Rectangle(x,y,lengthX,lengthY);
        RoomGen.rec.add(div);
        
			
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getLX(){
		return lengthX;
	}
	public int getLY(){
		return lengthY;
	}
	public Rectangle getRec(){
		return div;
	}
}
