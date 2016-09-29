import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


public class WholeImage extends JPanel {
	
	public WholeImage(){
		Block base = new Block(20, 20, 800, 600);
		RoomGen.list_unfinished.add(base);
		

		 for(int i = 0; i<RoomGen.list_unfinished.size();i++){
			 RoomGen.split(RoomGen.list_unfinished.get(i));
 		 }

		 RoomGen.createCorridors();
		 
		 for(int i = 0;i<RoomGen.list_finished.size();i++){
			 RoomGen.list_finished.get(i).chooseType();
		 }
		 

	}
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // fixes the immediate problem.
        Graphics2D g2 = (Graphics2D) g;
        
        
        for(int i =0;i<RoomGen.list_finished.size();i++){
        
        		g2.setColor(Color.BLACK);
        		g2.draw(RoomGen.list_finished.get(i).getRec());
        	
        	g2.drawString(RoomGen.list_finished.get(i).getTypeName(), 
        			(RoomGen.list_finished.get(i).getX())+(RoomGen.list_finished.get(i).getLX()/2), 
        			(RoomGen.list_finished.get(i).getY())+(RoomGen.list_finished.get(i).getLY()/2));
        }
        for(int i =0;i<RoomGen.corridor.size();i++){
            g2.setColor(Color.CYAN);
        	g2.fill(RoomGen.corridor.get(i));
        	g2.setColor(Color.black);
        	g2.draw(RoomGen.corridor.get(i));
        }
      
    }
}
