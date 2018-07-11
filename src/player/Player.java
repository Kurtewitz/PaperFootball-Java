package player;

import java.util.ArrayList;

import model.Model;
import view.PaperFootball;

public abstract class Player {

	private int score;
	private int nr;
	
	private boolean isHuman;
	
	PaperFootball main;
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
	
	/**
	 * @return this Player's number
	 */
	public int nr() {return nr;}
	/**
	 * @return this Player's score
	 */
	public int score() {return score;}
	/**
	 * @return <code>true</code> if this Player is a human, <code>false</code> if this Player is a bot
	 */
	public boolean isHuman() {return isHuman;}
	
	/**
	 * update this Player's model to reflect the current state of the model of the main class (used for resetting after goal)
	 */
	public void updateModel() {
		model = main.model();
	}
	
	/**
	 * calculate the bot's turn
	 * @return ArrayList of integer arrays (each of length 2) representing the x and y coordinate of the points to be clicked
	 */
	public abstract ArrayList<int[]> doTurn();
	
}
