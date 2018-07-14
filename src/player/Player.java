package player;

import java.util.ArrayList;

import model.Model;
import view.PaperFootball;

/**
 * Abstract class representing a player in the game storing some basic information, but lacking the crucial doTurn() method.
 * For actual implementations check the {@link Human} or more interestingly {@link AI} classes.
 * @author Michał Lipiński
 * @date 14.04.2017
 * @updated 14.07.2018 version 0.2
 */
public abstract class Player {

	/** amount of goals this Player has shot. */
	private int score;
	
	/** this Player's number (either 1 or 2) */
	private final int nr;
	
	/** is this Player human */
	private final boolean isHuman;
	
	/** this game's main class */
	private final PaperFootball main;
	/** this game's main model. Reset after every goal or choke. */
	protected Model model;
	
	public Player(int nr, boolean isHuman, PaperFootball main) {
		
		this.nr = nr;
		score = 0;
		
		this.isHuman = isHuman;
		
		this.main = main;
		model = main.model();
		
	}
	
	/**
	 * set this Player's score
	 * @param score
	 */
	public void setScore(int score) {this.score = score;}
	
	/** @return this Player's number */
	public int nr() {return nr;}
	
	/** @return this Player's score */
	public int score() {return score;}
	
	/** @return <code>true</code> if this Player is a human, <code>false</code> if this Player is a bot */
	public boolean isHuman() {return isHuman;}
	
	/** update this Player's model to reflect the current state of the model of the main class (used for resetting after goal) */
	public void updateModel() {
		model = main.model();
	}
	
	/**
	 * calculate the bot's turn
	 * @return ArrayList of integer arrays (each of length 2) representing the x and y coordinate of the points to be clicked
	 */
	public abstract ArrayList<int[]> doTurn();
	
}
