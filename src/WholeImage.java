import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;


public class WholeImage extends JPanel {
	
	private Block main;
	private int count = 0;
	private ArrayList<Block> path = new ArrayList<Block>();
	
	public WholeImage(){
		Block base = new Block(20, 20, 800, 600);
		RoomGen.list_unfinished.add(base);
		

		 for(int i = 0; i<RoomGen.list_unfinished.size();i++){
			 RoomGen.split(RoomGen.list_unfinished.get(i));
 		 }

		 RoomGen.createCorridors();
		 
		 // put rooms into all elements and choose their type
		 for(int i = 0;i<RoomGen.rooms.size();i++){
			 RoomGen.rooms.get(i).chooseType();
			 RoomGen.all_elements.add(RoomGen.rooms.get(i));
		
		 }
		 //put corridors into all elements
		 for(int i = 0;i<RoomGen.corridor.size();i++){
			 RoomGen.corridor.get(i).setIndex(i+RoomGen.all_elements.size());
			 RoomGen.all_elements.add(RoomGen.corridor.get(i));
		
		 }
		 //set index for all and generate blocksAround
		 for(int i = 0;i<RoomGen.all_elements.size();i++){
			 RoomGen.all_elements.get(i).setIndex(i);
			 RoomGen.all_elements.get(i).generateBlocksAround();
			 RoomGen.all_elements.get(i).setDoorNum();
		 }
		 for(int i = 0;i<RoomGen.all_elements.size();i++){
			 RoomGen.all_elements.get(i).setDoorNum();
		 }
		 
	        Block b = createPath(RoomGen.all_elements.get((int)(Math.random()*RoomGen.all_elements.size())));
	}
	public Block createPath(Block b){
		main = b;
		int index = (int) (Math.random()*RoomGen.blocks_around.get(b).size());
		Block next;
		path.add(b);
		
		
		//make sure that elements of path are distinct 
		if(path.contains(RoomGen.blocks_around.get(b).get(index))){
			if(index != 0){
				next = RoomGen.blocks_around.get(b).get(index-1);
			}else{
				next = RoomGen.blocks_around.get(b).get(index+1);
			}
		}else{
			next = RoomGen.blocks_around.get(b).get(index);
		}
		count++;
		if(count == 15){
			path.add(next);
			return next;
		}else{
			return createPath(next);
		}
		}
	
	
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // fixes the immediate problem.
        Graphics2D g2 = (Graphics2D) g;
        
        for(int i =0;i<RoomGen.rooms.size();i++){
        
        		g2.setColor(Color.BLACK);
        		g2.draw(RoomGen.rooms.get(i).getRec());
        	
        	g2.drawString(RoomGen.rooms.get(i).getTypeName()+" "+RoomGen.rooms.get(i).getPotentialDoorNum(), 
        
        			(RoomGen.rooms.get(i).getX())+(RoomGen.rooms.get(i).getLX()/2), 
        			(RoomGen.rooms.get(i).getY())+(RoomGen.rooms.get(i).getLY()/2));
        }
        //corridors
        for(int i =0;i<RoomGen.corridor.size();i++){
            g2.setColor(Color.CYAN);
        	g2.fill(RoomGen.corridor.get(i).getRec());
        	g2.setColor(Color.black);
        	g2.draw(RoomGen.corridor.get(i).getRec());
        }
        //main and adjacent
//        g2.setColor(Color.pink);
//        g2.fill(RoomGen.rooms.get(2).getRec());
//        g2.setColor(Color.black);
//        g2.draw(RoomGen.rooms.get(2).getRec());
//        
//       for(int i =0;i<RoomGen.blocks_around.get(RoomGen.rooms.get(2)).size();i++){
//        	g2.setColor(Color.yellow);
//        	g2.fill(RoomGen.blocks_around.get(RoomGen.rooms.get(2)).get(i).getRec());
//     
//        	g2.setColor(Color.black);
//        	g2.draw(RoomGen.blocks_around.get(RoomGen.rooms.get(2)).get(i).getRec());
//       
//        }
        
        g2.setColor(Color.GREEN);
		for(int i = 0;i<path.size();i++){
		    g2.setColor(Color.GREEN);
			g2.fill(path.get(i).getRec());
			g2.setColor(Color.black);
			g2.draw(path.get(i).getRec());
			g2.drawString(i+"", path.get(i).getX()+(path.get(i).getLX()/2), path.get(i).getY()+(path.get(i).getLY()/2));
			
		}
        g2.setColor(Color.RED);
        g2.fill(main.getRec());
        System.out.println("path size "+path.size());
    }
        
}



//createPath - from random initial into the one with full potential when we get to it, when full potential detected - add block to array of finished, subtract from unfinished
//if(unfinished not null){go backwards - find closest which has free potential and start a new path from it)








