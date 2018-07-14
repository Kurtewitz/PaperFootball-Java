package view;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.FillTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.PointModel;

/**
 * Graphical representation of a simple clickable Point on the playing field or any of the clickable or nonclickable Points outlining the playing field.
 * @author Michał Lipiński
 * @date 10.04.2017
 * @updated 14.07.2018 version 0.2
 */
public class Point extends Circle {

	/** PointModel this Point represents */
	private final PointModel p;
	/** the parent / container */
	private final View v;
	
	/** FillTransition responsible for the blinking animation highlighting possible moves for human players */
	private FillTransition blink;
	
	/**
	 * standard constructor for creating a Point to graphically represent a PointModel
	 * @param pm PointModel
	 * @param v instance of the main GUI class so we can forward certain calls
	 */
	public Point(PointModel pm, View v) {
		super(PaperFootball.BUFFER_AROUND_FIELD + pm.x() * PaperFootball.LINE_LENGTH,
				PaperFootball.BUFFER_AROUND_FIELD + pm.y() * PaperFootball.LINE_LENGTH,
				PaperFootball.POINT_RADIUS);
		
		p = pm;
		this.v = v;

		setStrokeWidth(PaperFootball.POINT_STROKE_WIDTH);
		setStroke(PaperFootball.DEFAULT_COLOR);
		
		if(pm.unused()) setFill(PaperFootball.POINT_UNUSED_COLOR);
		else setFill(PaperFootball.POINT_USED_COLOR);
		
		//the blinking animation used to show that this point is reachable by the player.
		blink = new FillTransition(Duration.millis(500), this, p.unused() ? PaperFootball.POINT_UNUSED_COLOR : PaperFootball.POINT_USED_COLOR, PaperFootball.POINT_HIGHLIGHT_COLOR);
		blink.setCycleCount(Animation.INDEFINITE);
		blink.setAutoReverse(true);
		
		//set the action for this Point
		setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				click();
			}
		});
		
	}
	
	/**
	 * Someone or something clicked on this Point -> forward the call to the main GUI class
	 */
	public void click() {
		if(!p.isBall()) v.moveTo(this);
	}
	
	
	/**
	 * set this Point as used
	 */
	public void put() {
		p.put();
		setFill(PaperFootball.POINT_USED_COLOR);
//		toFront();
	}
	
	/**
	 * set the <code>isBall</code> flag of this Point's PointModel. Display or hide the ball icon.
	 * @param b
	 */
	public void setBall(boolean b) {
		if(b) {
			p.setIsBall(true);
//			blink.play();
//			showAvailableMoves();
			toFront();
		}
		else {
			p.setIsBall(false);
//			blink.stop();
			if(!p.unused()) setFill(PaperFootball.POINT_USED_COLOR);
			else setFill(PaperFootball.POINT_UNUSED_COLOR);
			toFront();
		}
	}
	
	
	
	
	
	/**
	 * start the blinking animation of all the reachable points (the points the player can click on next).
	 */
	public void showAvailableMoves() {
		
		PointModel[] reachablePoints = p.reachablePoints();
		
		for(PointModel point : reachablePoints) {
			v.getPoint(point.x(), point.y()).startBlinking();
		}
		
	}
	
	/**
	 * Stop the blinking of all neighboring Points
	 */
	public void hideAvailableMoves() {
		
		for(int x = Math.max( 0, p.x() - 1 ); x <= Math.min( 8, p.x() + 1 ); x++) {
			for(int y = Math.max( 0, p.y() - 1 ); y <= Math.min( 12, p.y() + 1 ) ; y++) {
				
				v.getPoint(x, y).stopBlinking();
				
			}
		}
		
	}
	
	/**
	 * start the blinking animation which is supposed to mean, that the player can currently play the ball to this Point.
	 */
	public void startBlinking() {
		if(blink.getStatus() != Status.RUNNING) blink.playFromStart();
	}
	
	/**
	 * stop the blinking animation which is supposed to mean, that the player can currently play the ball to this Point.
	 */
	public void stopBlinking() {
		if(blink.getStatus() == Status.RUNNING || blink.getStatus() == Status.PAUSED) {
			blink.stop();
			
			//the blinking animation used to show that this point is reachable by the player.
			blink = new FillTransition(Duration.millis(500), this, p.unused() ? PaperFootball.POINT_UNUSED_COLOR : PaperFootball.POINT_USED_COLOR, PaperFootball.POINT_HIGHLIGHT_COLOR);
			blink.setCycleCount(Animation.INDEFINITE);
			blink.setAutoReverse(true);
			
			if(unused()) setFill(PaperFootball.POINT_UNUSED_COLOR);
			else setFill(PaperFootball.POINT_USED_COLOR);
			
			
			
		}
	}
	
	/**
	 * @return <code>true</code> if this is the currently active Point (the starting point of the next move a.k.a. "where the ball is")
	 */
	public boolean isBall() {
		return p.isBall();
	}
	
	/**
	 * @return <code>true</code> if this is an empty/unused Point
	 */
	public boolean unused() {
		return p.unused();
	}
	
	/**
	 * @return the PointModel this Point is a graphical representation of
	 */
	public PointModel pointModel() {
		return p;
	}
	
	/**
	 * @return an array of PointModels representing points reachable from this Point.
	 */
	public PointModel[] reachablePointModels() {
		return p.reachablePoints();
	}
	
	/**
	 * @return An array of Points reachable from this Point.
	 */
	public Point[] reachablePoints() {
		Point[] points = new Point[reachablePointModels().length];
		
		int counter = 0;
		for(PointModel pm : reachablePointModels()) {
			points[counter] = v.getPoint(pm.x(), pm.y());
			
			counter++;
		}
		
		return points;
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof Point && ((Point) o).pointModel().equals(this.pointModel());
	}
	
	
}
