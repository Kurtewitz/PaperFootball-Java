package player;

import java.util.ArrayList;
import java.util.Random;

import model.LineModel;
import model.PointModel;
import model.PointModel.Dir;
import view.PaperFootball;

/**
 * Computer player.
 * For implementing a bot, check the doTurn() method
 * @author Michał Lipiński
 * @date 14.04.2017
 * @updated 10.09.2018 version 0.3
 */
public class AI extends Player {
	

	/** ArrayList of Turns for holding all the possible Turns the AI player has available to him at a given time */
	private ArrayList<Turn> possibleTurns;
	
	/** difficulty rating of this bot */
	private int difficulty;
	
	
	public AI(int nr, int difficulty, PaperFootball main) {
		
		super(nr, false, main, true);
		this.difficulty = difficulty;
		
		possibleTurns = new ArrayList<Turn>();
	}
	
	
	
	
	/**
	 * Fill up the ArrayList <code>possibleMoves</code> by recursively checking for possible moves.
	 * @param startOfMove The point at which calculation of moves starts.
	 * Passed down the recursion-chain so we can eventually construct the entire turn starting from this point
	 * @param recursionStart the point starting from which we are currently looking for possible turns
	 * @param previousMoves ArrayList of lines we took from <code>startOfMove</code> to reach <code>recursionStart</code>
	 */
	public void createPossibleTurns(PointModel startOfMove, PointModel recursionStart, ArrayList<LineModel> previousMoves) {
		
		for(Dir dir : Dir.values()) {
			
			LineModel l = recursionStart.getLine(dir);
			
			//if l exists, is empty and not used in this turn so far
			if(l != null && l.unused() && !previousMoves.contains(l)) {
				
				//copy the lines we took so far, so we can pass them on
				ArrayList<LineModel> moves = new ArrayList<LineModel>();
				for(LineModel prevMove : previousMoves) {
					LineModel prevMoveCopy = new LineModel(prevMove);
					prevMoveCopy.from().connectLinesAfterCopying(prevMove.from());
					prevMoveCopy.to().connectLinesAfterCopying(prevMove.to());
					moves.add(prevMoveCopy);
				}
				// but we also need to add l to the list.
				LineModel lCopy = new LineModel(l);
				lCopy.from().connectLinesAfterCopying(l.from());
				lCopy.to().connectLinesAfterCopying(l.to());
				moves.add(lCopy);
				
				//if l goes from recursionStart...
				if(l.from().equals(recursionStart)) {
					
					//... to an empty point, we found a valid, complete turn...
					if(l.to().unused()) {
						//... and "moves" contains all the lines for this turn, so we convert it into a Turn
						possibleTurns.add(convertToTurn(startOfMove, moves));
					}
					//... otherwise we keep digging, starting from l's to() point
					else {
						createPossibleTurns(startOfMove, l.to(), moves);
					}
					
				}
				
				//if l goes to recursionStart...
				else if(l.to().equals(recursionStart)) {
					
					//... from an empty point, we found a valid, complete turn...
					if(l.from().unused()) {
						//... and "moves" contains all the lines for this turn, so we convert it into a Turn
						possibleTurns.add(convertToTurn(startOfMove, moves));
					}
					//... otherwise we keep digging, starting from l's from() point
					else {
						createPossibleTurns(startOfMove, l.from(), moves);
					}
				}
				
			}
			
		}
		
		
	}
	
	@Deprecated
	public void findPossibleTurns() {
		
		PointModel[][] points = model.points();
		
		
		//look for southern most reachable empty point
		if(nr() == 1) {
			
			for(int y = points.length - 1; y >= 0; y--) {
				for(int x = 0; x < points[y].length; x++) {
					
					if(points[y][x].unused() && reachable(points[y][x], new ArrayList<LineModel>())) {
						
						possibleTurns.add(findWay(points[y][x], new ArrayList<LineModel>()));
					}
				}
			}
		}
		//look for northern most reachable empty point
		else {
			
			for(int y = 0; y < points.length; y++) {
				for(int x = 0; x < points[y].length; x++) {
					
					if(points[y][x].unused() && reachable(points[y][x], new ArrayList<LineModel>())) {
						
						possibleTurns.add(findWay(points[y][x], new ArrayList<LineModel>()));
					}
					
				}
			}
		}
		
	}
	
	@Deprecated
	public boolean reachable(PointModel goal, ArrayList<LineModel> exclude) {

		if(goal.equals(model.activePoint())) return false;
		int options = goal.possibleMovesAmount();
		if (options > 0) {
			
			boolean foundWay = false;
			
			
			for(Dir dir : Dir.values()) {
				LineModel l = goal.getLine(dir);
				
				
				if(l == null || !l.unused() || exclude.contains(l)) continue;
				
				//check if l connects goal with a non-empty point
				if( ( goal.equals(l.from()) && !l.to().unused() ) || ( goal.equals(l.to()) && !l.from().unused() ) ) {
				
					//check if the non-empty point is the active point (direct connection between goal and active point)
					if(l.to().equals(model.activePoint()) || l.from().equals(model.activePoint())) {
						
						return true;
					}
					
					//if connection between goal a non-empty point, recursively check if non-empty point has connection to active
					else {
						
						//copy exclude list and add current LineModel to it
						ArrayList<LineModel> newExclude = new ArrayList<LineModel>();
						for(LineModel exc : exclude) {
							LineModel excCopy = new LineModel(exc);
							excCopy.from().connectLinesAfterCopying(exc.from());
							excCopy.to().connectLinesAfterCopying(exc.to());
							newExclude.add(excCopy);
						}
						LineModel lCopy = new LineModel(l);
						lCopy.from().connectLinesAfterCopying(l.from());
						lCopy.to().connectLinesAfterCopying(l.to());
						newExclude.add(lCopy);
						
						if(l.from().equals(goal)){
							
							foundWay = reachable(l.to(), newExclude);
							if (foundWay) return true;
						}
						else if(l.to().equals(goal)) {
						
							foundWay = reachable(l.from(), newExclude);
							if (foundWay) return true;
						}
					}
				}
				
				if(foundWay) return true;
			}
		}
		return false;
	}
	
	
	@Deprecated
	public Turn findWay(PointModel goal, ArrayList<LineModel> exclude) {
		
		if(goal.equals(model.activePoint())) return null;
		
		Turn way;
		for(Dir dir : Dir.values()) {
			
			LineModel l = goal.getLine(dir);
			
			
			
			if(l == null || !l.unused() || exclude.contains(l)) continue;
			
			//check if l connects goal with a non-empty point
			if( ( l.from().equals(goal) && !l.to().unused() ) || ( l.to().equals(goal) && !l.from().unused() ) ) {
				
				//direct connection from active point to goal
				if(l.from().equals(model.activePoint())) {
					Turn t = new Turn(this, model);
					t.addMove(new Move(model.activePoint().x(), model.activePoint().y(), l.to().x(), l.to().y()));
					return t;
				}
				
				//direct connection from goal to active point
				else if(l.to().equals(model.activePoint())) {
					Turn t = new Turn(this, model);
					t.addMove(new Move(model.activePoint().x(), model.activePoint().y(), l.from().x(), l.from().y()));
					return t;
				}
				
				//connection between goal and a non-empty not active point
				else {
					
					//copy exclude list and add current LineModel to it
					ArrayList<LineModel> newExclude = new ArrayList<LineModel>();
					for(LineModel exc : exclude) {
						LineModel excCopy = new LineModel(exc);
						excCopy.from().connectLinesAfterCopying(exc.from());
						excCopy.to().connectLinesAfterCopying(exc.to());
						newExclude.add(excCopy);
					}
					LineModel lCopy = new LineModel(l);
					lCopy.from().connectLinesAfterCopying(l.from());
					lCopy.to().connectLinesAfterCopying(l.to());
					newExclude.add(lCopy);
					
					
					//recursively look for connection between non-empty and active point
					if(l.to().equals(goal)){
						
						way = findWay(l.from(), newExclude);
						if(way != null) {
							way.addMove(new Move(l.from().x(), l.from().y(), l.to().x(), l.to().y()));
							return way;
						}
					}
					else if(l.from().equals(goal)) {
						
						way = findWay(l.to(), newExclude);
						if(way != null) {
							way.addMove(new Move(l.to().x(), l.to().y(), l.from().x(), l.from().y()));
							return way;
						}
					}
				}
				
				
			}
			
			
		}
		
		return null;
		
	}
	
	/**
	 * calculate possible moves, select the most adequate ones for the difficulty setting and finally pick one of them
	 * @return ArrayList of coordinates of points to click to perform this AI's turn
	 */
	public ArrayList<int[]> doTurn() {
		
		possibleTurns.clear();
		
		
		createPossibleTurns(model.activePoint(), model.activePoint(), new ArrayList<LineModel>());
		
		
		//check if there is at least one possible turn
		if(possibleTurns == null || possibleTurns.size() < 1) return new ArrayList<int[]>();
		

		//look for equally good turns, so we can without sacrificing the quality
		ArrayList<Turn> bestTurns = findBestTurns();
		
		Turn bestTurn = bestTurns.get(0);
		
		//...randomize AI's turns
		if(bestTurns.size() > 1) {
			Random r = new Random();
			bestTurn = bestTurns.get(r.nextInt(bestTurns.size()));
			
			
		}
		
		//convert the chosen turn into a list of coordinates to click
		ArrayList<int[]> pointsToClick = new ArrayList<int[]>();
		
		for(int i = 0 ; i < bestTurn.moves().size(); i++) {
			pointsToClick.add(new int[]{bestTurn.moves().get(i).getToX(), bestTurn.moves().get(i).getToY()});
		}
		
		return pointsToClick;
		
	}
	
	/**
	 * Depending on the difficulty setting, select adequate moves
	 * @return ArrayList of best turns for the selected difficulty
	 */
	public ArrayList<Turn> findBestTurns() {
		
		ArrayList<Turn> bestTurns = new ArrayList<Turn>();
		
		Turn bestTurn = possibleTurns.get(0);
		
		
		if(difficulty == 1) {
			
			// look for the best turn
			for(int i = 0; i < possibleTurns.size(); i++) {
				
				Turn t = possibleTurns.get(i);
				
				int yDelta = bestTurn.player().nr() == 1 ? t.lastMove().getToY() - bestTurn.lastMove().getToY() : bestTurn.lastMove().getToY() - t.lastMove().getToY();
				
				//difficulty 1 -> best move = northern/southern most ...
				if (yDelta > 0){
					bestTurn = t;
				}
				//... and with the shortest path
				else if(yDelta == 0 && t.moves().size() < bestTurn.moves().size()) {
					bestTurn = t;
				}
				
			}
			
			//look for equally good turns
			for(Turn t : possibleTurns) {
				
				int yDelta = bestTurn.player().nr() == 1 ? t.lastMove().getToY() - bestTurn.lastMove().getToY() : bestTurn.lastMove().getToY() - t.lastMove().getToY();
				
				if(yDelta == 0 && t.moves().size() == bestTurn.moves().size()) {
					bestTurns.add(t);
				}
			}
			
		}
		
		
		
		
		if(difficulty == 2) {
			
			// look for the best turn
			for(int i = 0; i < possibleTurns.size(); i++) {
							
				Turn t = possibleTurns.get(i);
				
				int tXGoal = Math.abs(4 - t.lastMove().getToX());
				int tYGoal = bestTurn.player().nr() == 1 ? 12 - t.lastMove().getToY() : t.lastMove().getToY();
				
				int tGoalDelta = Math.max(tXGoal, tYGoal);
				
				int bestXGoal = Math.abs(4 - bestTurn.lastMove().getToX());
				int bestYGoal = bestTurn.player().nr() == 1 ? 12 - bestTurn.lastMove().getToY() : bestTurn.lastMove().getToY();
				
				int bestGoalDelta = Math.max(bestXGoal, bestYGoal);
				
				
				//difficulty 2 -> best move = ending closest to goal
				if (tGoalDelta < bestGoalDelta) {
					bestTurn = t;
				}
				//... and with the longest path
				else if(tGoalDelta == bestGoalDelta && t.moves().size() >= bestTurn.moves().size() && 
						( (tXGoal == bestXGoal && tYGoal < bestYGoal) || (tYGoal == bestYGoal && tXGoal < bestXGoal)) ) {
					bestTurn = t;
				}
				
			}
			
			//look for equally good turns
			for(Turn t : possibleTurns) {
				
				int tXGoal = Math.abs(4 - t.lastMove().getToX());
				int tYGoal = bestTurn.player().nr() == 1 ? 12 - t.lastMove().getToY() : t.lastMove().getToY();
				
				int tGoalDelta = Math.max(tXGoal, tYGoal);
				
				int bestXGoal = Math.abs(4 - bestTurn.lastMove().getToX());
				int bestYGoal = bestTurn.player().nr() == 1 ? 12 - bestTurn.lastMove().getToY() : bestTurn.lastMove().getToY();
				
				int bestGoalDelta = Math.max(bestXGoal, bestYGoal);
			
				if(tGoalDelta == bestGoalDelta && t.moves().size() == bestTurn.moves().size() && 
						( ( tXGoal == bestXGoal && tYGoal <= bestYGoal) || ( tYGoal == bestYGoal && tXGoal <= bestXGoal) ) ) {
					bestTurns.add(t);
				}
			}
			
			
			
		}
		
		
		
		
		return bestTurns;
				
		
	}
	
	
	
}
