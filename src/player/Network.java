package player;

import java.util.ArrayList;

import view.PaperFootball;

/**
 * This class is for sending and receiving messages (game state updates) to an opponent over Network.
 * At this point we don't really care, if there is a human or bot at the other end of the connection.
 * @author Michał Lipiński
 * @date 10.09.2018
 * @updated 10.09.2018 version 0.2.9a
 */
public class Network extends Player {

	
	
	
	
	public Network(int nr, PaperFootball main) {
		super(nr, true, main, false);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<int[]> doTurn() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
