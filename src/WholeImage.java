import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;


public class WholeImage extends JPanel {
	
	private Block main;
	private static ArrayList<Block> path = new ArrayList<Block>();
	private static ArrayList<Line2D> doors = new ArrayList<Line2D>();
	private static ArrayList<Block> finished_doors = new ArrayList<Block>();
	private static ArrayList<Block> unfinished_doors = new ArrayList<Block>();
	static int new_start;
	static int count =0;
	
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
		 unfinished_doors.addAll(RoomGen.all_elements);
		 
	        Block b = createPath(RoomGen.all_elements.get((int)(Math.random()*RoomGen.all_elements.size())));
	        new_start = path.size()-2;
	        Block a = createPath(findNewPath(new_start));
	        
	        
//	        while(unfinished_doors.size()>0){
//	        	Block a = createPath(findNewPath(path.size()-2));
//	        	count++;
//	        	if(count ==20){
//	        	//	break;
//	        	}
//	        }
	        
	        System.out.println("last room potential: "+b.getPotentialDoorNum());
	        System.out.println("finished doors: "+finished_doors.size());
	        System.out.println("unfinished doors: "+unfinished_doors.size());
	        System.out.println("all elements: "+RoomGen.all_elements.size());
	}
	
	
	
	
	
	/*creating doors
	 * 1. create path from random point until reach block n with door full potential
	 * 2. go back on block n-1
	 * 3. if(block n-1 reached full potential) repeat 2.
	 *    else createPath(n-1)
	 * 4.if all block has full potential stop
	 * 
	 * 
	 * 1.
	 * createPath(random block b){
	 * 	
	 *    Block next = new Block
	 * }
	 * 
	 */
	
	
	
	
	
	
	
	//creating path with doors
	public Block createPath(Block b){
		main = b;
		int index = (int) (Math.random()*RoomGen.blocks_around.get(b).size());
		Block next;
		
		if(!path.contains(b)){
			path.add(b);
		}
		
		//set the next index to make sure that elements of path are distinct 
		if(path.contains(RoomGen.blocks_around.get(b).get(index))){
			if(index != 0){
				index = index-1;
			}else{
				index = index+1;
			}
		}else{		
		}
		next = new Block(0,0,0,0);
		
		//Initialise  next block
		if(unfinished_doors.contains(RoomGen.blocks_around.get(b).get(index))){
			next = RoomGen.blocks_around.get(b).get(index);
		}else{
			for(int i =0;i<RoomGen.blocks_around.get(b).size();i++){
				if(unfinished_doors.contains(RoomGen.blocks_around.get(b).get(i))){
					next = RoomGen.blocks_around.get(b).get(i);
					break;
				}else if(i==RoomGen.blocks_around.get(b).size()-1){
					return b;
				}
			}
		}

		
		//check which side b and next share and add door on it, also add one door to both b and next
		//-------------------------------------------------------------------------------------------------
		
		
		
		
		if(b.isOnRight(next)){	
			//(int)(Math.random() * (max - min) + min)
			int min = Math.max(b.getY(), next.getY());
			int max = Math.min(b.getY()+b.getLY(), next.getY()+next.getLY())-RoomGen.DOOR_SIZE;
			
			System.out.println("i am on right, index: "+index);
			
			b.setDoor(next.getX(), (int)(Math.random()*(max-min)+min), false);
			
			for(int i = 0;i<b.getDoors().size();i++){
				if(!doors.contains(b.getDoors().get(i))){
					doors.add(b.getDoors().get(i));
				}
			}
			
			
		}else if(b.isOnLeft(next)){
			System.out.println("i am on left, index: "+index);
			
			int min = Math.max(b.getY(), next.getY());
			int max = Math.min(b.getY()+b.getLY(), next.getY()+next.getLY());
			
			b.setDoor(b.getX(), (int)(Math.random()*(max-min)+min), false);
			
			for(int i = 0;i<b.getDoors().size();i++){
				if(!doors.contains(b.getDoors().get(i))){
					doors.add(b.getDoors().get(i));
				}
			}
			
		}else if(b.isUp(next)){
			System.out.println("i am above, index: "+index);
			
			int min = Math.max(b.getX(), next.getX());
			int max = Math.min(b.getX()+b.getLX(), next.getX()+next.getLX());
			
			b.setDoor((int)(Math.random()*(max-min)+min), b.getY(), true);
			
			for(int i = 0;i<b.getDoors().size();i++){
				if(!doors.contains(b.getDoors().get(i))){
					doors.add(b.getDoors().get(i));
				}
			}
			
		}else if(b.isDown(next)){
			System.out.println("i am down, index: "+index);
			
			int min = Math.max(b.getX(), next.getX());
			int max = Math.min(b.getX()+b.getLX(), next.getX()+next.getLX());
			
			b.setDoor((int)(Math.random()*(max-min)+min), next.getY(), true);
			
			for(int i = 0;i<b.getDoors().size();i++){
				if(!doors.contains(b.getDoors().get(i))){
					doors.add(b.getDoors().get(i));
				}
			}
		}
		//-----------------------------------------------------------------------------
		
		
		
		
		b.addDoor();
		next.addDoor();
		
		//when current block reaches full door potential when adding door to next,
		//add it to the list of finished blocks and remove it from the list of unfinished ones
		if(b.getPotentialDoorNum() == b.getCurrentDoorNum()){
			finished_doors.add(b);
			for(int i = 0;i<unfinished_doors.size();i++){
				if(unfinished_doors.get(i).equals(b)){
					unfinished_doors.remove(i);
				}
			}
		}
		
		//path ends if next reaches full door potential when adding door from b to next
		//next is put to finished list and removed from unfinished list
		if(next.getPotentialDoorNum() == next.getCurrentDoorNum()){
			finished_doors.add(next);
			path.add(next);
			
			for(int i = 0;i<unfinished_doors.size();i++){
				if(unfinished_doors.get(i).equals(next)){
					unfinished_doors.remove(i);
				}
			}
			return next;
		}
		//otherwise the path continues
		else{
			return createPath(next);
		}
		}
	
	
	
//find start block for a new path	
public Block findNewPath(int last_block_index){
	
	for(int i = path.size()-1;i>=0;i = i-1){
		
	}
	
	if(path.get(last_block_index).getPotentialDoorNum() == path.get(last_block_index).getCurrentDoorNum()){
		if(last_block_index<0){
			System.out.println("this should not happen");
			return path.get((int)(Math.random()*path.size()));
		}else{
			System.out.println("going back one more time");
			return findNewPath(last_block_index -1);
		}
	}else{
		System.out.println("we found new start, index: "+last_block_index);
		return path.get(last_block_index);
	}
}	
	
	
	
	
	
	
	
	
	
	
	
	
	
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // fixes the immediate problem.
        Graphics2D g2 = (Graphics2D) g;
        
        for(int i =0;i<RoomGen.rooms.size();i++){
        
        		g2.setColor(Color.BLACK);
        		g2.draw(RoomGen.rooms.get(i).getRec());
        	
//        	g2.drawString(RoomGen.rooms.get(i).getTypeName()+" "+RoomGen.rooms.get(i).getPotentialDoorNum(), 
//        
//        			(RoomGen.rooms.get(i).getX())+(RoomGen.rooms.get(i).getLX()/2), 
//        			(RoomGen.rooms.get(i).getY())+(RoomGen.rooms.get(i).getLY()/2));
        }
        //corridors
        for(int i =0;i<RoomGen.corridor.size();i++){
            g2.setColor(Color.CYAN);
        	g2.fill(RoomGen.corridor.get(i).getRec());
        	g2.setColor(Color.black);
        	g2.draw(RoomGen.corridor.get(i).getRec());
        }
        
        g2.setColor(Color.GREEN);
		for(int i = 0;i<path.size();i++){
		    g2.setColor(Color.GREEN);
			g2.fill(path.get(i).getRec());
			g2.setColor(Color.black);
			g2.draw(path.get(i).getRec());
			g2.drawString(i+"", path.get(i).getX()+(path.get(i).getLX()/2)+5, path.get(i).getY()+(path.get(i).getLY()/2)+10);
			
		}
		
        g2.setColor(Color.pink);
        g2.fill(path.get(new_start).getRec());
        
		
          g2.setColor(Color.DARK_GRAY);
          g2.fill(path.get(path.size()-1).getRec());
          
          g2.setColor(Color.RED);
        System.out.println("path size "+path.size());
        
        //painting doors
        g2.setStroke(new BasicStroke(2));
        
        for(int i = 0;i<doors.size();i++){
        	g2.draw(doors.get(i));
        }
        for(int i = 0;i<RoomGen.all_elements.size();i++){
        	g2.drawString(RoomGen.all_elements.get(i).getCurrentDoorNum()+"/"+RoomGen.all_elements.get(i).getPotentialDoorNum(), 
        		(RoomGen.all_elements.get(i).getX())+(RoomGen.all_elements.get(i).getLX()/2), 
       			(RoomGen.all_elements.get(i).getY())+(RoomGen.all_elements.get(i).getLY()/2));
        }
    }
    
    public static void clearAll(){
  
    	path.clear();
    	doors.clear();
    	finished_doors.clear();
    	unfinished_doors.clear();
    	new_start=0;
    	count =0;
    }
        
}
