package view;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.PointModel;

public class Point extends Circle {

	PointModel p;
	View v;
	
	FillTransition blink;
	
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
		
		if(pm.empty()) setFill(PaperFootball.POINT_EMPTY_COLOR);
		else setFill(PaperFootball.POINT_USED_COLOR);
		
		//the blinking animation on the active point
		blink = new FillTransition(Duration.millis(500), this, PaperFootball.POINT_USED_COLOR, PaperFootball.POINT_ACTIVE_COLOR);
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
		if(!p.active()) v.moveTo(this);
	}
	
	
	/**
	 * set this Point as used
	 */
	public void put() {
		p.put();
		setFill(PaperFootball.POINT_USED_COLOR);
		toFront();
	}
	
	/**
	 * set the <code> active</code> flag of this Point. Start or stop the blinking animation
	 * @param b
	 */
	public void setActive(boolean b) {
		if(b) {
			p.setActive(true);
			blink.play();
			toFront();
		}
		else {
			p.setActive(false);
			blink.stop();
			if(!p.empty()) setFill(PaperFootball.POINT_USED_COLOR);
			else setFill(PaperFootball.POINT_EMPTY_COLOR);
			toFront();
		}
	}
	
	/**
	 * @return <code>true</code> if this is the currently active Point (the starting point of the next move)
	 */
	public boolean active() {
		return p.active();
	}
	
	/**
	 * @return <code>true</code> if this is an empty/unused Point
	 */
	public boolean empty() {
		return p.empty();
	}
	
	/**
	 * @return the PointModel this Point is a graphical representation of
	 */
	public PointModel pointModel() {
		return p;
	}
	
	
	
	
}
