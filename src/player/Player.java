package player;

import java.util.ArrayList;

import model.LineModel;
import model.Model;
import model.PointModel;
import view.PaperFootball;

/**
 * Abstract class representing a player in the game storing some basic information, but lacking the crucial doTurn() method.
 * For actual implementations check the {@link Human} or {@link Network} or more interestingly {@link AI} classes.
 * @author Michał Lipiński
 * @date 14.04.2017
 * @updated 10.09.2018 version 0.3
 */
public abstract class Player {

	/** amount of goals this Player has shot. */
	private int score;
	
	/** this Player's number (either 1 or 2) */
	private final int nr;
	
	/** is this Player human */
	private final boolean isHuman;
	
	/** is this player playing on this machine or over network.*/
	private final boolean local;
	
	/** this game's main class */
	protected final PaperFootball main;
	/** this game's main model. Reset after every goal or choke. */
	protected Model model;
	
	public Player(int nr, boolean isHuman, PaperFootball main, boolean local) {
		
		this.nr = nr;
		score = 0;
		
		this.isHuman = isHuman;
		
		this.main = main;
		model = main.model();
		
		this.local = local;
		
	}
	
	/**
	 * Player's main method, depends on the subclass implementation, but generally speaking:
	 * calculate a bot's turn, do nothing for a human player to make his turn by GUI interaction or wait to receive a player's turn over network
	 * @return ArrayList of integer arrays (each of length 2) representing the x and y coordinate of the points to be clicked
	 */
	public abstract ArrayList<int[]> doTurn();
	
	
	/**
	 * set this Player's score
	 * @param score
	 */
	public void setScore(int score) {this.score = score;}
	
	/** @return this Player's number */
	public int nr() {return nr;}
	
	/** @return this Player's score */
	public int score() {return score;}
	
	/** @return <code>true</code> if this Player is a human ({@link Human} or {@link Network}), <code>false</code> if this Player is a bot ({@link AI})*/
	public boolean isHuman() {return isHuman;}
	
	/** update this Player's model to reflect the current state of the model of the main class (used for resetting after goal) */
	public void updateModel() {
		model = main.model();
	}
	
	/** @return <code>true</code> if this Player is a local Player playing on this machine (instance of {@link AI} or {@link Human}), <code>false</code> if this Player is playing over network (instance of {@link Network})*/
	public boolean isLocal() {
		return local;
	}
	
	
	/**
	 * Convert an ArrayList of LineModel into a Turn
	 * @param startingPoint the PointModel this Turn starts at
	 * @param usedLines ArrayList of {@link LineModel}s used for this Turn
	 * @return {@link Turn}
	 */
	public Turn convertToTurn(PointModel startingPoint, ArrayList<LineModel> usedLines) {
		
		Turn t = new Turn(this, model);
		
		PointModel currentPoint = startingPoint;
		
		for(LineModel l : usedLines) {
			
			if(l.from().equals(currentPoint)) {
				t.addMove(new Move(currentPoint.x(), currentPoint.y(), l.to().x(), l.to().y()));
				currentPoint = l.to();
			}
			else if(l.to().equals(currentPoint)) {
				t.addMove(new Move(currentPoint.x(), currentPoint.y(), l.from().x(), l.from().y()));
				currentPoint = l.from();
			}
		}
		return t;
	}
	
	
	
}
