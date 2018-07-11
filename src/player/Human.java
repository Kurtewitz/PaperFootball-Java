package player;

import java.util.ArrayList;

import view.PaperFootball;

public class Human extends Player {

	public Human(int nr, PaperFootball main) {
		super(nr, true, main);
	}
	
	
	public ArrayList<int[]> doTurn() {
		return new ArrayList<int[]>();
	}
	
}
