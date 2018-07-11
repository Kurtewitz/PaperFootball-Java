package view;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.LineModel;
import model.Model;

public class View extends Group {

	private PaperFootball main;
	
	private Point[][] points;
	private ArrayList<Edge> lines;
	
	
	private Label player1_score;
	private Label player2_score;
	
	private Label player1_turn;
	private Label player2_turn;
	
	private ImageView ball;
	RotateTransition rotateBall;
	
	public View(Model m, PaperFootball pf) {
		super();
		
		main = pf;
		points = new Point[m.points().length][m.points()[0].length];
		
		ball = new ImageView(
				new Image(
						View.class.getResourceAsStream("/ball.png"), 8 * PaperFootball.LINE_LENGTH / 10, 8 * PaperFootball.LINE_LENGTH / 10, true, true
						)
				);
		
		rotateBall = new RotateTransition(Duration.millis(500), ball);
		rotateBall.setByAngle(360);
		rotateBall.setRate(0.5);
		rotateBall.setCycleCount(Animation.INDEFINITE);
		
		Label background = new Label();
		background.setStyle("-fx-background-color: forestgreen");
		background.setPrefWidth((m.points()[0].length - 1) * PaperFootball.LINE_LENGTH);
		background.setPrefHeight((m.points().length - 1) * PaperFootball.LINE_LENGTH);
		background.setTranslateX(PaperFootball.BUFFER_AROUND_FIELD);
		background.setTranslateY(PaperFootball.BUFFER_AROUND_FIELD);
		
		getChildren().add(background);
		
		
		lines = new ArrayList<Edge>();
		for(LineModel lm : m.lines()) {
			lines.add(new Edge(lm));
		}
		
		for(Edge e : lines) {
			getChildren().add(e);
		}
		
		
		
		Rectangle player1_goal = new Rectangle(PaperFootball.BUFFER_AROUND_FIELD + 3 * PaperFootball.LINE_LENGTH + PaperFootball.LINE_STROKE_WIDTH / 2,
				PaperFootball.BUFFER_AROUND_FIELD + PaperFootball.LINE_STROKE_WIDTH / 2,
				2 * PaperFootball.LINE_LENGTH - PaperFootball.LINE_STROKE_WIDTH,
				PaperFootball.LINE_LENGTH - PaperFootball.LINE_STROKE_WIDTH / 2);
		player1_goal.setFill(PaperFootball.PLAYER1_COLOR);
		player1_goal.setStroke(Color.TRANSPARENT);
		Rectangle player2_goal = new Rectangle(PaperFootball.BUFFER_AROUND_FIELD + 3 * PaperFootball.LINE_LENGTH + PaperFootball.LINE_STROKE_WIDTH / 2,
				PaperFootball.BUFFER_AROUND_FIELD + 11 * PaperFootball.LINE_LENGTH,
				2 * PaperFootball.LINE_LENGTH - PaperFootball.LINE_STROKE_WIDTH,
				PaperFootball.LINE_LENGTH - PaperFootball.LINE_STROKE_WIDTH / 2);
		player2_goal.setFill(PaperFootball.PLAYER2_COLOR);
		player2_goal.setStroke(Color.TRANSPARENT);
		getChildren().addAll(player1_goal, player2_goal);
		
		
		
		player1_score = new Label("Score: " + main.player1().score());
		player1_score.setOpacity(1.0);
		player1_score.setStyle("-fx-background-color: limegreen");
		
		player1_score.setPrefWidth(3 * PaperFootball.LINE_LENGTH - PaperFootball.LINE_STROKE_WIDTH);
		player1_score.setPrefHeight(PaperFootball.LINE_LENGTH - PaperFootball.LINE_STROKE_WIDTH);
		player1_score.setTranslateX(PaperFootball.BUFFER_AROUND_FIELD + PaperFootball.LINE_STROKE_WIDTH/2);
		player1_score.setTranslateY(PaperFootball.BUFFER_AROUND_FIELD + PaperFootball.LINE_STROKE_WIDTH/2);
		
		player1_score.setAlignment(Pos.CENTER);
		player1_score.setFont(new Font(20.0));
		
		

		player2_score = new Label("Score: " + main.player2().score());
		player2_score.setOpacity(1.0);
		player2_score.setStyle("-fx-background-color: limegreen");
		
		player2_score.setPrefWidth(3 * PaperFootball.LINE_LENGTH - PaperFootball.LINE_STROKE_WIDTH);
		player2_score.setPrefHeight(PaperFootball.LINE_LENGTH - PaperFootball.LINE_STROKE_WIDTH);
		player2_score.setTranslateX(PaperFootball.BUFFER_AROUND_FIELD + PaperFootball.LINE_STROKE_WIDTH/2);
		player2_score.setTranslateY(PaperFootball.BUFFER_AROUND_FIELD + PaperFootball.LINE_STROKE_WIDTH/2 + 11 * PaperFootball.LINE_LENGTH);
		
		player2_score.setAlignment(Pos.CENTER);
		player2_score.setFont(new Font(20.0));
		
		
		
		player1_turn = new Label();
		player1_turn.setOpacity(1.0);
		player1_turn.setStyle("-fx-background-color: limegreen");
		
		player1_turn.setPrefWidth(3 * PaperFootball.LINE_LENGTH - PaperFootball.LINE_STROKE_WIDTH);
		player1_turn.setPrefHeight(PaperFootball.LINE_LENGTH - PaperFootball.LINE_STROKE_WIDTH);
		player1_turn.setTranslateX(PaperFootball.BUFFER_AROUND_FIELD + PaperFootball.LINE_STROKE_WIDTH/2 + 5 * PaperFootball.LINE_LENGTH);
		player1_turn.setTranslateY(PaperFootball.BUFFER_AROUND_FIELD + PaperFootball.LINE_STROKE_WIDTH/2);
		
		player1_turn.setAlignment(Pos.CENTER);
		
		player2_turn = new Label();
		player2_turn.setOpacity(1.0);
		player2_turn.setStyle("-fx-background-color: limegreen");
		
		player2_turn.setPrefWidth(3 * PaperFootball.LINE_LENGTH - PaperFootball.LINE_STROKE_WIDTH);
		player2_turn.setPrefHeight(PaperFootball.LINE_LENGTH - PaperFootball.LINE_STROKE_WIDTH);
		player2_turn.setTranslateX(PaperFootball.BUFFER_AROUND_FIELD + PaperFootball.LINE_STROKE_WIDTH/2 + 5 * PaperFootball.LINE_LENGTH);
		player2_turn.setTranslateY(PaperFootball.BUFFER_AROUND_FIELD + PaperFootball.LINE_STROKE_WIDTH/2 + 11 * PaperFootball.LINE_LENGTH);
		
		player2_turn.setAlignment(Pos.CENTER);
		
		
		getChildren().addAll(player1_score, player1_turn, player2_score, player2_turn);
		
		
		for(int y = 0; y < points.length; y++) {
			for(int x = 0 ; x < points[y].length; x++) {
				points[y][x] = new Point(m.getPoint(x, y), this);
				getChildren().add(points[y][x]);
			}
		}
		
		
	}
	
	/**
	 * set the turn indicator (football icon) to show that it is <code>playerNr</code>'s turn
	 * @param playerNr
	 */
	public void setTurnIndicator(int playerNr) {
		
		if(playerNr == 1) {
			
			rotateBall.stop();
			player1_turn.setGraphic(ball);
			player2_turn.setGraphic(null);
			rotateBall.play();
			
		}
		else if(playerNr == 2) {
			
			rotateBall.stop();
			player1_turn.setGraphic(null);
			player2_turn.setGraphic(ball);
			rotateBall.play();
		}
		
	}
	
	/**
	 * @return the currently active Point (starting point for the next move)
	 */
	public Point activePoint() {
		for(int y = 0; y < points.length; y++) {
			for(int x = 0; x < points[y].length; x++) {
				if(points[y][x].active()) return points[y][x];
			}
		}
		return null;
	}

	/**
	 * Try to move from the currently active Point to the given Point. Check if:
	 * <li> there exists a direct connection
	 * <li> it's still empty
	 * <li> the target Point is empty (and thus it the next player's turn)
	 * <li> the target Point is the goal
	 * @param p Point
	 */
	public void moveTo(Point p) {
		
		Point active = activePoint();
		
		Edge e = new Edge(new LineModel(active.p, p.pointModel()));
		
		for(Edge edge : lines) {
			//there exists a direct connection between active and p
			if(edge.equals(e)) {
				//valid move
				if(edge.empty()) {
					
					edge.draw(PaperFootball.PLAYER_TURN);
					active.setActive(false);
					p.setActive(true);
					
					
					//check if turn over
					if(p.empty()) {
						
						p.put();
						
						//goal
						if(p.pointModel().isGoal()) {
							main.goal();
						}
						
						//next turn
						else {
							Thread t = new Thread(new Runnable() {
								
								@Override
								public void run() {
									main.nextTurn();
								}
							});
							t.start();
						}
					}
				}
				
				
			}
		}
		
	}
	
	/**
	 * get the Point at the given coordinates
	 * @param x
	 * @param y
	 * @return Point with the given coordinates or <code>null</code> if incorrect coordinates
	 */
	public Point getPoint(int x, int y) {
		return (y < points.length && y >= 0 && x < points[y].length && x >= 0) ? points[y][x] : null;
	}
	
	
	/**
	 * set the center point as active and the turn indicator to reflect current player's turn
	 */
	public void start() {
		
		Point center = getPoint(points[0].length / 2, points.length / 2);
		
		center.put();
		center.setActive(true);
		
		setTurnIndicator(PaperFootball.PLAYER_TURN);
	}
	
	
	
}
