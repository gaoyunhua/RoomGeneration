package roomgeneration;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.swing.JPanel;


public class WholeImage extends JPanel {

	private static final int BASE_BLOCK_X = 20;
	private static final int BASE_BLOCK_Y = 20;
	private static final int BASE_BLOCK_WIDTH = 800;
	private static final int BASE_BLOCK_HEIGHT = 600;



	private static ArrayList<Block> path;
	private static ArrayList<Block> pathAnimationList;
	private static ArrayList<Line2D> doors;
	private static ArrayList<Block> finishedDoors;
	private static ArrayList<Block> unfinishedDoors;

	static int newStart;
	static int count;
	private  static int countPathIndex;
	
	public WholeImage(){

		init();

		Block base = new Block(BASE_BLOCK_X, BASE_BLOCK_Y, BASE_BLOCK_WIDTH, BASE_BLOCK_HEIGHT);
		RoomGen.listUnfinishedBlocks.add(base);
		

		 for(int i = 0; i<RoomGen.listUnfinishedBlocks.size(); i++){
			 RoomGen.split(RoomGen.listUnfinishedBlocks.get(i));
 		 }

		 RoomGen.createCorridors();
		 
		 // put rooms into all elements and choose their type
		 for(int i = 0;i<RoomGen.rooms.size();i++){
			 RoomGen.rooms.get(i).chooseType();
			 RoomGen.allElements.add(RoomGen.rooms.get(i));
		
		 }
		 //put corridors into all elements
		 for(int i = 0; i<RoomGen.corridors.size(); i++){
			 RoomGen.corridors.get(i).setIndex(i + RoomGen.allElements.size());
			 RoomGen.allElements.add(RoomGen.corridors.get(i));
		
		 }
		 //set index for all and generate blocksAround
		 for(int i = 0; i<RoomGen.allElements.size(); i++){
			 RoomGen.allElements.get(i).setIndex(i);
			 RoomGen.allElements.get(i).setPotentialDoorNumber();
		 }
		 for(int i = 0; i<RoomGen.allElements.size(); i++){
			 RoomGen.allElements.get(i).generateBlocksAround();
		 }
		 unfinishedDoors.addAll(RoomGen.allElements);


		//----------------------------------------PATH-------------------------
	        Block b = createPath(RoomGen.allElements.get((int)(Math.random()*RoomGen.allElements.size())));
	        newStart = path.size()-2;
	        Block a = createPath(findNewPath(newStart));
	        //-----------------------------------PATH----------------------------
	        
	        
//	        while(unfinishedDoors.size()>0){
//	        	roomgeneration.Block a = createPath(findNewPath(path.size()-2));
//	        	count++;
//	        	if(count == 100){
//	        		break;
//	        	}
//	        }
	}

	
	/*creating doors
	 * 1. create path from random point until reaches block n with door full potential
	 * 2. go back on block n-1
	 * 3. if(block n-1 reached full potential) repeat 2.
	 *    else createPath(n-1)
	 * 4.if all block has full potential stop
	 * 
	 * 
	 * 1.
	 * createPath(random block b){
	 * 	
	 *    roomgeneration.Block next = new roomgeneration.Block
	 * }
	 * 
	 */
	
	

	private void init() {
		path = new ArrayList<>();
		doors = new ArrayList<>();
		finishedDoors = new ArrayList<>();
		unfinishedDoors = new ArrayList<>();
		pathAnimationList = new ArrayList<>();

		count =0;
		int countPathIndex = 0;
	}

	private void addDoor(Block currentBlock, Block next) {

		if(currentBlock.isOnRight(next) || currentBlock.isOnLeft(next)) {
			//(int)(Math.random() * (max - min) + min)
			int min = Math.max(currentBlock.getY(), next.getY());
			int max = Math.min(currentBlock.getY() + currentBlock.getHeight() - RoomGen.DOOR_SIZE,
					next.getY() + next.getHeight()) - RoomGen.DOOR_SIZE;


			if (currentBlock.isOnRight(next)) {
				currentBlock.setDoor(next.getX(),
						(int) (Math.random() * (max - min) + min), false);

			} else if (currentBlock.isOnLeft(next)) {
				currentBlock.setDoor(currentBlock.getX(),
						(int) (Math.random() * (max - min) + min), false);

			}
		}
		else if (currentBlock.isUp(next) || currentBlock.isDown(next)) {
			int min = Math.max(currentBlock.getX(), next.getX());
			int max = Math.min(currentBlock.getX() + currentBlock.getWidth() - RoomGen.DOOR_SIZE,
					next.getX() + next.getWidth() - RoomGen.DOOR_SIZE);


			if (currentBlock.isUp(next)) {
				currentBlock.setDoor((int) (Math.random() * (max - min) + min),
									 currentBlock.getY(), true);

			} else if (currentBlock.isDown(next)) {
				currentBlock.setDoor((int) (Math.random() * (max - min) + min),
									 next.getY(), true);
			}
		}

		for(int i = 0;i<currentBlock.getDoors().size();i++) {
			if (!doors.contains(currentBlock.getDoors().get(i))) {
				doors.add(currentBlock.getDoors().get(i));
			}
		}
	}
	
	
	
	//creating path with doors
	public Block createPath(Block currentBlock){

		int indexOfNeighbouringBlock = (int) (Math.random()*RoomGen.neighbouringBlocks.get(currentBlock).size());
		int size = RoomGen.neighbouringBlocks.get(currentBlock).size();
		
		if(!path.contains(currentBlock)){
			path.add(currentBlock);
		}
		
		//set the next index to make sure that elements of path are distinct
		System.out.println("size of neighbours: " + RoomGen.neighbouringBlocks.get(currentBlock).size());
		if(path.contains(RoomGen.neighbouringBlocks.get(currentBlock).get(indexOfNeighbouringBlock))){
			if(indexOfNeighbouringBlock != 0 || indexOfNeighbouringBlock == RoomGen.neighbouringBlocks.get(currentBlock).size() - 1){
				indexOfNeighbouringBlock = indexOfNeighbouringBlock-1;
			}else{
				indexOfNeighbouringBlock = indexOfNeighbouringBlock+1;
			}
		}else{		
		}
		Block next = new Block(0,0,0,0);
		
		//Initialise  next block
		if(unfinishedDoors.contains(RoomGen.neighbouringBlocks.get(currentBlock).get(indexOfNeighbouringBlock))){
			next = RoomGen.neighbouringBlocks.get(currentBlock).get(indexOfNeighbouringBlock);
		}else{
			for(int i = 0; i<RoomGen.neighbouringBlocks.get(currentBlock).size(); i++){
				if(unfinishedDoors.contains(RoomGen.neighbouringBlocks.get(currentBlock).get(i))){
					next = RoomGen.neighbouringBlocks.get(currentBlock).get(i);
					break;
				}else if(i==RoomGen.neighbouringBlocks.get(currentBlock).size()-1){
					return currentBlock;
				}
			}
		}

		addDoor(currentBlock, next);
		
		currentBlock.addDoor();
		next.addDoor();
		
		//if current block reaches full door potential when adding door to next,
		//add it to the list of finished blocks and remove it from the list of unfinished ones
		if(currentBlock.getPotentialDoorNum() == currentBlock.getCurrentDoorNum()){
			finishedDoors.add(currentBlock);
			for(int i = 0; i< unfinishedDoors.size(); i++){
				if(unfinishedDoors.get(i).equals(currentBlock)){
					unfinishedDoors.remove(i);
				}
			}
		}
		
		//path ends if next reaches full door potential when adding door from b to next
		//next is put to finished list and removed from unfinished list
		if(next.getPotentialDoorNum() == next.getCurrentDoorNum()){
			finishedDoors.add(next);
			path.add(next);
			
			for(int i = 0; i< unfinishedDoors.size(); i++){
				if(unfinishedDoors.get(i).equals(next)){
					unfinishedDoors.remove(i);
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
public Block findNewPath(int lastBlockIndex){

	if(path.get(lastBlockIndex).getPotentialDoorNum() == path.get(lastBlockIndex).getCurrentDoorNum()){
		if(lastBlockIndex <= 0) return path.get((int)(Math.random()*path.size()));
		else return findNewPath(lastBlockIndex -1);
	}else{
		return path.get(lastBlockIndex);
	}
}	
	

	
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // fixes the immediate problem.
        Graphics2D g2 = (Graphics2D) g;


        for(Corridor corridor : RoomGen.corridors){
            g2.setColor(Color.CYAN);
        	g2.fill(corridor.getRec());
        }


        //-----------------------------------------PATH----------------------------------------------------
        g2.setColor(Color.GREEN);
		for(int i = 0;i<path.size();i++){

		    g2.setColor(Color.GREEN);
			g2.fill(path.get(i).getRec());

		}

        g2.setColor(Color.pink);
        g2.fill(path.get(newStart).getRec());


		g2.setColor(Color.DARK_GRAY);
		g2.fill(path.get(path.size()-1).getRec());
        //-----------------------------PATH-----------------------------------------------


		for(Block block : RoomGen.allElements) {
			g2.setColor(Color.BLACK);
			g2.draw(block.getRec());
		}

		g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(2));
        
        for(Line2D door : doors){
        	g2.draw(door);
        }
    }

    static void clearAll(){
  
    	path.clear();
    	doors.clear();
    	finishedDoors.clear();
    	unfinishedDoors.clear();
    	newStart = 0;
    	count = 0;
    }
        
}
