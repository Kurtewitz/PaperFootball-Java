package model;

import java.util.ArrayList;

public class Model {

	private PointModel[][] points;
	private ArrayList<LineModel> lines;
	
	
	public Model() {
		
		points = new PointModel[13][9];
		lines = new ArrayList<LineModel>();
		
		//initialize all points
		for(int y = 0; y < points.length; y++) {
			for(int x = 0; x < points[y].length; x++) {
				points[y][x] = new PointModel(x, y);
			}
		}
		
		//set goals
		points[0][4].setAsGoal();
		points[12][4].setAsGoal();
		
		//set edge-points
		//0th && 12th row - outside of field + back of goal
		//1st & 11th row - northern/southern border, x = 4 -> middle of goal
		for(int x = 0; x < points[0].length; x++) {
			if(x != 4) {
				points[0][x].put();
				points[12][x].put();
				
				points[1][x].put();
				points[11][x].put();
			}
		}
		//side lines
		for(int y = 0; y < points.length; y++) {
			points[y][0].put();
			points[y][8].put();
		}
		
		//prevent cut corners
		ArrayList<LineModel> exceptions = new ArrayList<LineModel>();
		exceptions.add(new LineModel(new PointModel(0, 2), new PointModel(1, 1)));
		exceptions.add(new LineModel(new PointModel(0, 10), new PointModel(1, 11)));
		exceptions.add(new LineModel(new PointModel(8, 2), new PointModel(7, 1)));
		exceptions.add(new LineModel(new PointModel(8, 10), new PointModel(7, 11)));
		exceptions.add(new LineModel(new PointModel(3, 1), new PointModel(4, 0)));
		exceptions.add(new LineModel(new PointModel(3, 11), new PointModel(4, 12)));
		exceptions.add(new LineModel(new PointModel(5, 1), new PointModel(4, 0)));
		exceptions.add(new LineModel(new PointModel(5, 11), new PointModel(4, 12)));
		
		//set all the lines
		for(int y = 0; y < points.length; y++) {
			for(int x = 0 ; x < points[y].length; x++) {
				
				LineModel left = null;
				LineModel leftDown = null;
				LineModel down = null;
				LineModel downRight = null;
				
				//check if left exists
				if(x > 0) {
					PointModel from = getPoint(x, y);
					PointModel to = getPoint(x - 1, y);
					
					left = new LineModel(from, to, !( from.empty() || to.empty() ) );
					if(exceptions.contains(left)) left.setEmpty(true);
				}
				//check if leftDown exists
				if(x > 0 && y < (points.length - 1)) {
					PointModel from = getPoint(x, y);
					PointModel to = getPoint(x - 1, y + 1);
					
					leftDown = new LineModel(from, to, !(from.empty() || to.empty()));
					if(exceptions.contains(leftDown)) leftDown.setEmpty(true);
				}
				//check if down exists
				if(y < (points.length - 1)) {
					PointModel from = getPoint(x, y);
					PointModel to = getPoint(x, y + 1);
					
					down = new LineModel(from, to, !(from.empty() || to.empty()));
					if(exceptions.contains(down)) down.setEmpty(true);
				}
				//check if downRight exists
				if(x < (points[0].length - 1) && y < (points.length - 1)) {
					PointModel from = getPoint(x, y);
					PointModel to = getPoint(x + 1, y + 1);
					
					downRight = new LineModel(from, to, !(from.empty() || to.empty()));
					if(exceptions.contains(downRight)) downRight.setEmpty(true);
				}
				
				if(left != null) lines.add(left);
				if(leftDown != null) lines.add(leftDown);
				if(down != null) lines.add(down);
				if(downRight != null) lines.add(downRight);
				
			}
		}
		
		
		//connect the dots
		for(LineModel l : lines) {
			
			PointModel from = l.from();
			PointModel to = l.to();
			
			//from west to east
			if(from.x() < to.x()) {
				
				//from north to south
				if(from.y() < to.y()) {
					from.setSouthEast(l);
					to.setNorthWest(l);
				}
				//from south to north
				else if (from.y() > to.y()) {
					from.setNorthEast(l);
					to.setSouthWest(l);
				}
				//horizontal
				else {
					from.setEast(l);
					to.setWest(l);
				}
			}
			//from east to west
			else if (from.x() > to.x()) {
				
				//from north to south
				if(from.y() < to.y()) {
					from.setSouthWest(l);
					to.setNorthEast(l);
				}
				//from south to north
				else if (from.y() > to.y()) {
					from.setNorthWest(l);
					to.setSouthEast(l);
				}
				//horizontal
				else {
					from.setWest(l);
					to.setEast(l);
				}
			}
			//vertikal
			else {
				
				//from north to south
				if(from.y() < to.y()) {
					from.setSouth(l);
					to.setNorth(l);
				}
				//from south to north
				else if (from.y() > to.y()) {
					from.setNorth(l);
					to.setSouth(l);
				}
			}
			
			//set the back of the goal to be edge
			getLine(3, 0, 4, 0).setEmpty(false);
			getLine(4, 0, 5, 0).setEmpty(false);
			getLine(3, 12, 4, 12).setEmpty(false);
			getLine(4, 12, 5, 12).setEmpty(false);
			
		}
		
		
		
	}
	
	/**
	 * get the PointModel at the given coordinates
	 * @param x
	 * @param y
	 * @return PointModel with the given coordinates or <code>null</code> if incorrect coordinates
	 */
	public PointModel getPoint(int x, int y) {
		return (y < points.length && y >= 0 && x < points[y].length && x >= 0) ? points[y][x] : null;
	}
	
	/**
	 * get the LineModel connecting the two PointModel in the direction specified by their <code>from</code> and <code>to</code> status
	 * @param from PointModel
	 * @param to PointModel
	 * @return LineModel going from <code>from<code> to <code>to</code> or
	 * <code>null</code> if the two PointModels are not connected or connected, but in the opposite direction
	 */
	public LineModel getLine(PointModel from, PointModel to) {
		
		for(LineModel l : lines) if(l.from().equals(from) && l.to().equals(to)) return l;
		return null;
	}
	
	/**
	 * get the LineModel connecting two PointModels with the specified coordinates
	 * @param xFrom x coordinate of one of the two PointModels
	 * @param yFrom y coordinate of one of the two PointModels
	 * @param xTo x coordinate of the other PointModel
	 * @param yTo y coordinate of the other PointModel
	 * @return LineModel connecting the two PointModels or <code>null</code> if the two PointModels are not directly connected
	 */
	public LineModel getLine(int xFrom, int yFrom, int xTo, int yTo) {
		
		PointModel from = new PointModel(xFrom, yFrom);
		PointModel to = new PointModel(xTo, yTo);
		return getLine(from, to) != null ? getLine(from, to) : getLine(to, from);
	}
	
	
	
	public PointModel[][] points() {
		return points;
	}
	
	public ArrayList<LineModel> lines() {
		return lines;
	}
	
	/**
	 * @return currently active point (starting point of the next move)
	 */
	public PointModel activePoint() {
		
		for(int y = 0; y < points.length; y++) {
			for(int x = 0; x < points[y].length; x++) {
				if(points[y][x].active()) return points[y][x];
			}
		}
		return null;
		
	}
	
	
	
	
}
