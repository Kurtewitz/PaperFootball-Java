package player;

import java.util.ArrayList;

import view.PaperFootball;

/**
 * A human player.
 * Mostly empty, since the human player will complete his turns by interacting with the GUI.
 * @author Michał Lipiński
 * @date 14.04.2017
 * @updated 14.07.2018 version 0.2
 */
public class Human extends Player {

	public Human(int nr, PaperFootball main) {
		super(nr, true, main);
	}
	
	/**
	 * Since this is a human player that will complete his or her turn by interacting with the GUI,
	 * this method returns simply an empty ArrayList
	 */
	public ArrayList<int[]> doTurn() {
		return new ArrayList<int[]>();
	}
	
}
