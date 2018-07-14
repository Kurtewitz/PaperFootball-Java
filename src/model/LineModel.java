package model;

/**
 * Model representing a connection between two {@link PointModel}s
 * @author Michał Lipiński
 * @date 10.04.2017
 * @updated 14.07.2018 version 0.2
 */
public class LineModel {

	/** First end of this LineModel, for most purposes "from" and "to" are interchangeable*/
	private PointModel from;
	/** Second end of this LineModel, for most purposes "from" and "to" are interchangeable */
	private PointModel to;
	
	/** is this a still unused and available connection */
	private boolean unused;
	/** the nr of the player that drew this Line (used this line for his move) */
	private int drawnByPlayer;
	
	/**
	 * standard constructor for creating an empty connection between two PointModels
	 * @param from
	 * @param to
	 */
	public LineModel(PointModel from, PointModel to) {
		this.from = from;
		this.to = to;
		unused = true;
		drawnByPlayer = 0;
	}
	
	/**
	 * constructor allowing creation of non-empty lines (the field)
	 * @param from
	 * @param to
	 * @param isEdge
	 */
	public LineModel(PointModel from, PointModel to, boolean isEdge) {
		
		this(from, to);
		unused = !isEdge;
	}
	
	/**
	 * copy constructor
	 * @param toCopy LineModel to be copied
	 */
	public LineModel(LineModel toCopy) {
		
		this.from = new PointModel(toCopy.from());
		this.to = new PointModel(toCopy.to());
		
		unused = toCopy.unused();
		drawnByPlayer = toCopy.drawnByPlayer();
		
		
	}
	
	/**
	 * set this LineModel as already used
	 * @param player number of player that used this LineModel (for coloring purposes)
	 */
	public void draw(int player) {
		unused = false;
		drawnByPlayer = player;
	}
	
	/**
	 * set this LineModel's <code>empty</code> flag
	 * @param b
	 */
	public void setUnused(boolean b) {unused = b;}
	
	/**
	 * @return <code>true</code> if this LineModel is empty/unused
	 */
	public boolean unused() {return unused;}
	
	/**
	 * @return the number of the player that used/drew this LineModel
	 */
	public int drawnByPlayer() {return drawnByPlayer;}
	
	/**
	 * @return the PointModel this LineModel start from
	 */
	public PointModel from() {return from;}
	
	/**
	 * @return the PointModle this LineModel goes to
	 */
	public PointModel to() {return to;}
	
	@Override
	public boolean equals(Object o) {
		
		return (o instanceof LineModel) && ( 
				( ( ( LineModel ) o ).from().equals(from) && ( ( LineModel ) o ).to().equals(to) )
				||
				( ( ( LineModel ) o ).from().equals(to) && ( ( LineModel ) o ).to().equals(from) )
				);
	}
	
}
