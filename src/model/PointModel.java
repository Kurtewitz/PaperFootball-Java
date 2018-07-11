package model;

public class PointModel {

	private int x;
	private int y;
	
	private boolean empty;
	private boolean active;
	
	private boolean isGoal;
	
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
		empty = true;
		active = false;
		
		isGoal = false;
	}
	
	/**
	 * copy constructor. After copying you need to call newCopy.connectLinesAfterCopying(toCopy)
	 * @param toCopy PointModel to be copied
	 */
	public PointModel(PointModel toCopy) {
		
		this.x = toCopy.x();
		this.y = toCopy.y();
		empty = toCopy.empty();
		active = toCopy.active();
		
		isGoal = false;
	}
	
	/**
	 * Copy the lines connected to this point from the given PointModel
	 * @param toCopy
	 */
	public void connectLinesAfterCopying(PointModel toCopy) {
		
		if(toCopy.north != null) north = new LineModel(toCopy.north);
		if(toCopy.northWest != null) northWest = new LineModel(toCopy.northWest);
		if(toCopy.west != null) west = new LineModel(toCopy.west);
		if(toCopy.southWest != null) southWest = new LineModel(toCopy.southWest);
		if(toCopy.south != null) south = new LineModel(toCopy.south);
		if(toCopy.southEast != null) southEast = new LineModel(toCopy.southEast);
		if(toCopy.east != null) east = new LineModel(toCopy.east);
		if(toCopy.northEast != null) northEast = new LineModel(toCopy.northEast);
	}
	
	/**
	 * @return <code>true</code> if this is an empty/unused point
	 */
	public boolean empty() {return empty;}
	/**
	 * set this point as used/not empty
	 */
	public void put() { empty = false;}
	
	/**
	 * @return <code>true</code> if this is the currently active point (the starting point of the next move)
	 */
	public boolean active() {return active;}
	/**
	 * set this point as active (the starting point of the next move)
	 * @param b
	 */
	public void setActive(boolean b) {active = b;}
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
	public int possibleMoves() {
		int options = 0;
		for(Dir dir : Dir.values()) {
			if(getLine(dir) != null && getLine(dir).empty()) options++;
		}
		return options;
	}
	
	
	@Override
	public boolean equals(Object o) {
		return (o instanceof PointModel) && (((PointModel) o).x() == x) && (((PointModel) o).y() == y);
	}
	
	
	
}
