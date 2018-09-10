package player;


/**
 * Class for storing a simple move from one point to another. One ore more Moves make up a {@link Turn}
 * @author Michał Lipiński
 * @date 14.04.2017
 * @updated 10.09.2018 version 0.2.9a
 */
public class Move {
	
	private int toX;
	private int toY;
	
	private int fromX;
	private int fromY;
	
	public Move(int fromX, int fromY, int toX, int toY) {
		
		this.toX = toX;
		this.toY = toY;
		
		this.fromX = fromX;
		this.fromY = fromY;
		
	}

	public int getToX() {return toX;}
	public int getToY() {return toY;}
	public int getFromX() {return fromX;}
	public int getFromY() {return fromY;}
	
	
	
}