package view;

import javafx.scene.shape.Line;
import model.LineModel;

public class Edge extends Line {

	LineModel l;
	
	public Edge(LineModel lm) {
		super(PaperFootball.BUFFER_AROUND_FIELD + lm.from().x() * PaperFootball.LINE_LENGTH,
				PaperFootball.BUFFER_AROUND_FIELD + lm.from().y() * PaperFootball.LINE_LENGTH,
				PaperFootball.BUFFER_AROUND_FIELD + lm.to().x() * PaperFootball.LINE_LENGTH,
				PaperFootball.BUFFER_AROUND_FIELD + lm.to().y() * PaperFootball.LINE_LENGTH);
		
		l = lm;
		
		setStrokeWidth(PaperFootball.LINE_STROKE_WIDTH);
		
		
		update();
		
		
		
	}
	
	/**
	 * @return the LineModel this Edge is a graphical representation of
	 */
	public LineModel line() {
		return l;
	}
	
	/**
	 * @return <code>true</code> if this Edge is empty/unused
	 */
	public boolean empty() {
		return l.empty();
	}
	
	/**
	 * set this Edge as used
	 * @param player number of player that draws this Edge (for coloring purposes)
	 */
	public void draw(int player) {
		
		l.draw(player);
		update();
		toFront();
	}
	
	/**
	 * update the appearance of this Edge to reflect the state of the underlying LineModel
	 */
	public void update() {
		if(l.empty()) setStroke(PaperFootball.LINE_EMPTY_COLOR);
		else {
			if(l.drawnByPlayer() == 0) setStroke(PaperFootball.DEFAULT_COLOR);
			else if(l.drawnByPlayer() == 1) setStroke(PaperFootball.PLAYER1_COLOR);
			else if(l.drawnByPlayer() == 2) setStroke(PaperFootball.PLAYER2_COLOR);
		}
	}
	
	@Override
	public boolean equals(Object o) {
		return (o instanceof Edge) && (
				( ((Edge)o).line().from().equals(l.from()) && ((Edge)o).line().to().equals(l.to()) )
				||
				( ((Edge)o).line().from().equals(l.to()) && ((Edge)o).line().to().equals(l.from()) )
				);
	}
	
	
}
