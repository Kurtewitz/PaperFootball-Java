package player;

import java.util.ArrayList;

import model.LineModel;
import model.Model;

/**
 * This class is mostly a wrapper for a list of {@link Move}s representing a player's turn.
 * @author Michał Lipiński
 * @date 14.04.2017
 * @updated 10.09.2018 version 0.2.9a
 */
public class Turn {
	
	/** Model used for checking the validity of moves */
	private Model m;
	/** Player making this turn. */
	private Player p;
	
	/** List of Moves */
	private ArrayList<Move> moves;
	
	
	Turn(Player p, Model m) {
		this.m = m;
		this.p = p;
		
		moves = new ArrayList<Move>();
	}
	
	/**
	 * Add a Move to this Turn
	 * @param move Move to be added to the list of Moves for this Turn.
	 * @return <code>true<code> if the Move has been successfully added, <br>
	 * <code>false</code> otherwise
	 */
	public boolean addMove(Move move) {
		LineModel l = m.getLine(move.getFromX(), move.getFromY(), move.getToX(), move.getToY());
		if(l.unused()) {
			moves.add(move);
			
			return true;
		}
		else return false;
	}
	
	/**
	 * @return the last Move of this Turn
	 */
	public Move lastMove() {
		return moves.get(moves.size() - 1);
	}
	
	/**
	 * @return the {@link Player} who made this Turn.
	 */
	public Player player() {
		return p;
	}
	
	/**
	 * @return the ArrayList with {@link Move}s making up this Turn.
	 */
	public ArrayList<Move> moves() {
		return moves;
	}

	
	
	@Override
	public boolean equals(Object o) {
		return (o instanceof Turn) && ((Turn)o).lastMove().getToX() == lastMove().getToX() && ((Turn)o).lastMove().getToY() == lastMove().getToY();
		
	}

//	@Override
//	public int compareTo(Turn t) {
//		return p.nr() == 1 ? lastMove().getToY() - t.lastMove().getToY() : t.lastMove().getToY() - lastMove().getToY();
//	}
	
	
}