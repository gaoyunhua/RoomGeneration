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
		 
//		 for(int i = 0;i<RoomGen.list_finished.size();i++){
//			 Room r = new Room(RoomGen.list_finished.get(i));
//			 System.out.println(r.chooseType());
//		 }

	}
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // fixes the immediate problem.
        Graphics2D g2 = (Graphics2D) g;

        for(int i=0;i<RoomGen.lines.size();i++){
        	g2.draw(RoomGen.lines.get(i));
        	
        }
      
    }

}
