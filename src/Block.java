import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JPanel;


public class Block extends JPanel{
	private int x;
	private int y;
	private int lengthX;
	private int lengthY;
	
	Line2D horizontalLine1;
	Line2D verticalLine1;
	Line2D horizontalLine2;
	Line2D verticalLine2;
	
	public Block(int x,int y,int lengthX, int lengthY){
		this.x = x;
		this.y = y;
		this.lengthX = lengthX;
		this.lengthY = lengthY;
		
        horizontalLine1 = new Line2D.Float(x,y,x+lengthX,y);
        verticalLine1 = new Line2D.Float(x,y,x,y+lengthY);
        horizontalLine2 = new Line2D.Float(x,y+lengthY,x+lengthX,y+lengthY);
        verticalLine2 = new Line2D.Float(x+lengthX,y,x+lengthX,y+lengthY);
        
        RoomGen.lines.add(horizontalLine1);
        RoomGen.lines.add(verticalLine1);
        RoomGen.lines.add(horizontalLine2);
        RoomGen.lines.add(verticalLine2);
        
			
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
	


	@Override
	public String toString() {
		return "Block [x=" + x + ", y=" + y + ", lengthX=" + lengthX
				+ ", lengthY=" + lengthY + ", horizontalLine1="
				+ horizontalLine1 + ", verticalLine1=" + verticalLine1
				+ ", horizontalLine2=" + horizontalLine2 + ", verticalLine2="
				+ verticalLine2 + "]";
	}


}
