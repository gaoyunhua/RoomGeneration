package roomgeneration;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.*;

import javax.swing.Timer;

import javax.swing.JPanel;


public class WholeImage extends JPanel {

	private static final int BASE_BLOCK_X = 20;
	private static final int BASE_BLOCK_Y = 20;
	private static final int BASE_BLOCK_WIDTH = 800;
	private static final int BASE_BLOCK_HEIGHT = 600;

	private static final int DELAY = 100;

	private static final Color COLOR_OF_DOORS = Color.RED;
	private static final Color COLOR_OF_OBJECTS_OUTLINE = Color.BLACK;
	private static final Color COLOR_OF_CORRIDORS = Color.CYAN;
	private static final Color COLOR_OF_POTENTIAL_PATH = Color.PINK;
	private static final Color COLOR_OF_ANIMATED_PATH = Color.GREEN;
	private static final Color COLOR_OF_CURRENT_BLOCK = Color.DARK_GRAY;

	public Timer timer;

	private ArrayList<Block> path;
	private ArrayList<Block> pathAnimationList;
	private ArrayList<Line2D> doors;
	private ArrayList<Block> finishedDoors;
	private ArrayList<Block> unfinishedDoors;
	private ArrayList<Block> pathEnd = new ArrayList<>();

	private Block currentBlockAnimation;
	private LinkedList<Integer> pathIndexes;
	private LinkedList<Integer> pathIndexesStore;
	ArrayList<Block> rest;
	int count;
	private boolean stop;
	
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

		rest = new ArrayList<>(RoomGen.allElements);

		//----------------------------------------PATH-------------------------
	        Block b = createPath(RoomGen.allElements.get((int)(Math.random()*RoomGen.allElements.size())));
	        pathIndexes.add(b.getIndex());
	        pathEnd.add(b);

	        int count = 0;
	        while(!rest.isEmpty() && count <= 10) {
	        	createPath(findNewPath());
	        	count++;
			}

		//-----------------------------------PATH----------------------------
		pathIndexesStore = new LinkedList<>(pathIndexes);
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
		pathIndexes = new LinkedList<>();

		count =0;
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


		if(rest.isEmpty()) return currentBlock;

		int indexOfNeighbouringBlock = (int) (Math.random() * RoomGen.neighbouringBlocks.get(currentBlock).size());

		pathIndexes.add(currentBlock.getIndex());

		if(!path.contains(currentBlock)){
			path.add(currentBlock);
		} else {
			pathIndexes.add(currentBlock.getIndex());
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
		Block next = null;
		
		//Initialise  next block
		if(unfinishedDoors.contains(RoomGen.neighbouringBlocks.get(currentBlock).get(indexOfNeighbouringBlock))){
			next = RoomGen.neighbouringBlocks.get(currentBlock).get(indexOfNeighbouringBlock);
		}else{
			for(int i = 0; i < RoomGen.neighbouringBlocks.get(currentBlock).size(); i++){
				if(unfinishedDoors.contains(RoomGen.neighbouringBlocks.get(currentBlock).get(i))){
					next = RoomGen.neighbouringBlocks.get(currentBlock).get(i);
					break;
				}else if(i == RoomGen.neighbouringBlocks.get(currentBlock).size() - 1){
					return currentBlock;
				}
			}
		}
		if(next != null) {
			addDoor(currentBlock, next);

			currentBlock.addDoor();
			next.addDoor();

			//if current block reaches full door potential when adding door to next,
			//add it to the list of finished blocks and remove it from the list of unfinished ones
			if (currentBlock.getPotentialDoorNum() == currentBlock.getCurrentDoorNum()) {
				finishedDoors.add(currentBlock);
				for (int i = 0; i < unfinishedDoors.size(); i++) {
					if (unfinishedDoors.get(i).equals(currentBlock)) {
						unfinishedDoors.remove(i);
					}
				}
				rest.remove(currentBlock);
			}

			//path ends if next reaches full door potential when adding door from b to next
			//next is put to finished list and removed from unfinished list
			if (next.getPotentialDoorNum() == next.getCurrentDoorNum()) {
				finishedDoors.add(next);
				path.add(next);
				pathIndexes.add(next.getIndex());

				for (int i = 0; i < unfinishedDoors.size(); i++) {
					if (unfinishedDoors.get(i).equals(next)) {
						unfinishedDoors.remove(i);
					}
				}
				rest.remove(next);
				return createPath(findNewPath());
			}
			//otherwise the path continues
			else {
				return createPath(next);
			}
		}
		return currentBlock;
		}
	
	

//find start block for a new path	
public Block findNewPath(){

	System.out.println("rest size: " + rest.size());
	if(!rest.isEmpty()) {
		return rest.get((int) (Math.random() * rest.size()));
	} else {
		return RoomGen.allElements.get(0);
	}
}	

	
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // fixes the immediate problem.
        Graphics2D g2 = (Graphics2D) g;

        for(Corridor corridor : RoomGen.corridors){
            g2.setColor(COLOR_OF_CORRIDORS);
        	g2.fill(corridor.getRec());
        }

        //-----------------------------------------PATH----------------------------------------------------

		g2.setColor(COLOR_OF_POTENTIAL_PATH);
		for(Block block : path) {
			g2.fill(block.getRec());
		}
        g2.setColor(COLOR_OF_ANIMATED_PATH);
		for(int i = 0;i<pathAnimationList.size();i++){
			g2.fill(pathAnimationList.get(i).getRec());
		}

		g2.setColor(COLOR_OF_CURRENT_BLOCK);
		if(currentBlockAnimation != null) {
			g2.fill(currentBlockAnimation.getRec());
		}
        //-----------------------------PATH-----------------------------------------------

		g2.setColor(COLOR_OF_OBJECTS_OUTLINE);
		for(Block block : RoomGen.allElements) {
			g2.draw(block.getRec());
		}

		g2.setColor(COLOR_OF_DOORS);
        g2.setStroke(new BasicStroke(2));
        
        for(Line2D door : doors){
        	g2.draw(door);
        }
    }

	void animate() {
		ActionListener listener = e -> {
            if(!pathIndexes.isEmpty()) {
				currentBlockAnimation = RoomGen.allElements.get(pathIndexes.removeFirst());
				pathAnimationList.add(currentBlockAnimation);
				revalidate();
				setVisible(true);
            }else{
                stop = true;
                timer.stop();
            }
            repaint();
        };
		timer = new Timer(DELAY, listener);

		timer.start();
		if(stop) {
			timer.stop();
		}
	}

	void restartAnimation() {
		pathIndexes = new LinkedList<>(pathIndexesStore);
		pathAnimationList.clear();
		currentBlockAnimation = null;

		timer.restart();
	}
}
