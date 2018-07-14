package model;

/**
 * Model representing a simple clickable Point on the playing field.
 * @author Michał Lipiński
 * @date 10.04.2017
 * @updated 14.07.2018 version 0.2
 */
public class PointModel {

	/** the x coordinate of this PointModel on the playing field (0 - 8) */
	private final int x;
	/** the y coordinate of this PointModel on the playing Field (0 - 12) */
	private final int y;
	
	/** is this PointModel still unused - does moving to this PointModel end a player's turn */
	private boolean unused;
	/** is this PointModel the current location of the ball. */
	private boolean isBall;
	/** is this PointModel at the center of the back of the goal - the point a player has to reach to score a goal - basically shortcut for x == 4 && (y == 0 || y == 12)*/
	private boolean isGoal;
	
	//LineModels connecting this PointModel to PointModels in each direction.
	private LineModel north;
	private LineModel northWest;
	private LineModel west;
	private LineModel southWest;
	private LineModel south;
	private LineModel southEast;
	private LineModel east;
	private LineModel northEast;
	
	
	/**
	 * standard constructor
	 * @param x coordinate
	 * @param y coordinate
	 */
	public PointModel(int x, int y) {
		this.x = x;
		this.y = y;
		unused = true;
		isBall = false;
		
		isGoal = false;
	}
	
	/**
	 * copy constructor. After copying you need to call copy.connectLinesAfterCopying(original)
	 * @param original PointModel to be copied
	 */
	public PointModel(PointModel original) {
		
		this.x = original.x();
		this.y = original.y();
		unused = original.unused();
		isBall = original.isBall();
		
		isGoal = false;
	}
	
	/**
	 * Copy the lines connected to this point from the given PointModel
	 * @param original PointModel we want to copy the connections from
	 */
	public void connectLinesAfterCopying(PointModel original) {
		
		if(original.north != null) north = new LineModel(original.north);
		if(original.northWest != null) northWest = new LineModel(original.northWest);
		if(original.west != null) west = new LineModel(original.west);
		if(original.southWest != null) southWest = new LineModel(original.southWest);
		if(original.south != null) south = new LineModel(original.south);
		if(original.southEast != null) southEast = new LineModel(original.southEast);
		if(original.east != null) east = new LineModel(original.east);
		if(original.northEast != null) northEast = new LineModel(original.northEast);
	}
	
	/**
	 * @return <code>true</code> if this is an empty/unused point
	 */
	public boolean unused() {return unused;}
	/**
	 * set this point as used/not empty
	 */
	public void put() { unused = false;}
	
	/**
	 * @return <code>true</code> if this is the currently active point (the starting point of the next move a.k.a. "where the ball is")
	 */
	public boolean isBall() {return isBall;}
	/**
	 * set this point as active (the starting point of the next move)
	 * @param b
	 */
	public void setIsBall(boolean b) {isBall = b;}
	/**
	 * @return the x coordinate of this PointModel
	 */
	public int x() {return x;}
	/**
	 * @return the y coordinate of this PointModel
	 */
	public int y() {return y;}
	
	/**
	 * @return <code>true</code> if this PointModel is the goal and needs to be reached to score
	 */
	public boolean isGoal() {return isGoal;}
	
	/**
	 * set this PointModel as the goal that needs to be reached to score
	 */
	public void setAsGoal() {isGoal = true;}
	
	/**
	 * set the given LineModel as this PointModel's <code>NORTH</code> connection
	 * @param l
	 */
	public void setNorth(LineModel l) {north = l;}
	/**
	 * set the given LineModel as this PointModel's <code>NORTH_WEST</code> connection
	 * @param l
	 */
	public void setNorthWest(LineModel l) {northWest = l;}
	/**
	 * set the given LineModel as this PointModel's <code>WEST</code> connection
	 * @param l
	 */
	public void setWest(LineModel l) {west = l;}
	/**
	 * set the given LineModel as this PointModel's <code>SOUTH_WEST</code> connection
	 * @param l
	 */
	public void setSouthWest(LineModel l) {southWest = l;}
	/**
	 * set the given LineModel as this PointModel's <code>SOUTH</code> connection
	 * @param l
	 */
	public void setSouth(LineModel l) {south = l;}
	/**
	 * set the given LineModel as this PointModel's <code>SOUTH_EAST</code> connection
	 * @param l
	 */
	public void setSouthEast(LineModel l) {southEast = l;}
	/**
	 * set the given LineModel as this PointModel's <code>EAST</code> connection
	 * @param l
	 */
	public void setEast(LineModel l) {east = l;}
	/**
	 * set the given LineModel as this PointModel's <code>NORTH_EAST</code> connection
	 * @param l
	 */
	public void setNorthEast(LineModel l) {northEast = l;}
	
	
	/**
	 * @return the LineModel going from this point to his neighbor in <code>NORTH</code> direction
	 */
	public LineModel getNorth() {return north;}
	/**
	 * @return the LineModel going from this point to his neighbor in <code>NORTH_WEST</code> direction
	 */
	public LineModel getNorthWest() {return northWest;}
	/**
	 * @return the LineModel going from this point to his neighbor in <code>WEST</code> direction
	 */
	public LineModel getWest() {return west;}
	/**
	 * @return the LineModel going from this point to his neighbor in <code>SOUTH_WEST</code> direction
	 */
	public LineModel getSouthWest() {return southWest;}
	/**
	 * @return the LineModel going from this point to his neighbor in <code>SOUTH</code> direction
	 */
	public LineModel getSouth() {return south;}
	/**
	 * @return the LineModel going from this point to his neighbor in <code>SOUTH_EAST</code> direction
	 */
	public LineModel getSouthEast() {return southEast;}
	/**
	 * @return the LineModel going from this point to his neighbor in <code>EAST</code> direction
	 */
	public LineModel getEast() {return east;}
	/**
	 * @return the LineModel going from this point to his neighbor in <code>NORTH_EAST</code> direction
	 */
	public LineModel getNorthEast() {return northEast;}
	
	
	/**
	 *an enum of all 8 possible directions
	 */
	public static enum Dir {
		NORTH, NORTH_WEST, WEST, SOUTH_WEST, SOUTH, SOUTH_EAST, EAST, NORTH_EAST
	}
	
	/**
	 * Get the line going from this PointModel in the given direction
	 * @param direction one of the values of enum Dir
	 * @return LineModel connecting this PointModel to its neighbor in the given direction
	 */
	public LineModel getLine(Dir direction) {
		
		switch (direction) {
		case NORTH:
			return getNorth();
		case SOUTH:
			return getSouth();
		case EAST:
			return getEast();
		case WEST:
			return getWest();
		case NORTH_EAST:
			return getNorthEast();
		case NORTH_WEST:
			return getNorthWest();
		case SOUTH_EAST:
			return getSouthEast();
		case SOUTH_WEST:
			return getSouthWest();

		default:
			return null;
		}
		
	}
	
	/**
	 * @return number of lines connected to this PointModel that are still empty
	 */
	public int possibleMovesAmount() {
		int options = 0;
		for(Dir dir : Dir.values()) {
			if(getLine(dir) != null && getLine(dir).unused()) options++;
		}
		return options;
	}
	
	
	/**
	 * @return an array of "unused" LineModel representing possible moves starting from this PointModel
	 */
	public LineModel[] possibleMoves() {
		LineModel[] moves = new LineModel[possibleMovesAmount()];
		
		int counter = 0;
		for(Dir dir : Dir.values()) {

			if(getLine(dir) != null && getLine(dir).unused()) {
				moves[counter] = getLine(dir);
				counter++;
			}
		}
		return moves;
	}
	
	/**
	 * @return An array of PointModels connected by unused LineModels, a.k.a. "Points the player can click on next".
	 */
	public PointModel[] reachablePoints() {
		//get the list of possible moves...
		LineModel[] possibleMoves = possibleMoves();
		
		//... and create an array of reachable PointModels with the same length
		PointModel[] points = new PointModel[possibleMoves.length];
		
		int counter = 0;
		for(LineModel move : possibleMoves) {
			
			//check if this PointModel is this LineModel's "from" and if yes add it's "to" to available points, otherwise add it's "from".
			points[counter] = move.from().equals(this) ? move.to() : move.from();
			counter++;
			
		}

		return points;
		
	}
	
	
	@Override
	public boolean equals(Object o) {
		return (o instanceof PointModel) && (((PointModel) o).x() == x) && (((PointModel) o).y() == y);
	}
	
	
	
}
