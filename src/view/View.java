package view;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
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
/**
 * The main view class.
 * @author Michał Lipiński
 * @date 10.04.2017
 * @updated 10.09.2018 version 0.2.9a
 */
public class View extends Group {

	/** the main class */
	private final PaperFootball main;
	
	/** two-dimensional array of Points representing the playing field (the entire 8 * 12 field) */
	private final Point[][] points;
	/** ArrayList of all Edges - all the connections between the Points on the field. */
	private final ArrayList<Edge> lines;
	
	/** amount of goals player 1 has scored */
	private final Label player1_score;
	/** amount of goals player 2 has scored */
	private final Label player2_score;
	
	/** Label next to player 1's goal for displaying the Football icon to show it's player 1's turn */
	private final Label player1_turn;
	/** Label next to player 2's goal for displaying the Football icon to show it's player 2's turn */
	private final Label player2_turn;
	
	/** Image of a football used for indicating whose turn it is */
	private final ImageView ball_turn;
	/** Image of a football used to show to position of the ball in the game */
	private final ImageView ball_game;
	
	/** Image of a red football player that appears whenever Player 1 (currently "red") makes a move */
	private final ImageView red_player;
	/** Image of a blue football player that appears whenever Player 2 (currently "blue") makes a move */
	private final ImageView blue_player;
	
	/** RotateTransition responsible for rotation of the turn-indicating football image */
	private final RotateTransition rotateBall;
	
	
	public View(Model m, PaperFootball pf) {
		super();
		
		/*this code comes from a short time when View extended Region instead of Group
		 * which made addin Padding easier, back when I wanted padding around the playing field.
		this.setPrefWidth(8 * PaperFootball.LINE_LENGTH + 2 * PaperFootball.POINT_RADIUS + 2 * PaperFootball.BUFFER_AROUND_FIELD);
		this.setPrefHeight(12 * PaperFootball.LINE_LENGTH + 2 * PaperFootball.POINT_RADIUS + 2 * PaperFootball.BUFFER_AROUND_FIELD);
		
		this.setPadding(new Insets(PaperFootball.BUFFER_AROUND_FIELD, PaperFootball.BUFFER_AROUND_FIELD, PaperFootball.BUFFER_AROUND_FIELD, PaperFootball.BUFFER_AROUND_FIELD));
		*/
		main = pf;
		points = new Point[m.points().length][m.points()[0].length];
		
		ball_turn = new ImageView(
				new Image(
						View.class.getResourceAsStream("/ball.png"), 8 * PaperFootball.LINE_LENGTH / 10, 8 * PaperFootball.LINE_LENGTH / 10, true, true
						)
				);
		ball_game = new ImageView(
				new Image(
						View.class.getResourceAsStream("/ball.png"), 5 * PaperFootball.POINT_RADIUS, 5 * PaperFootball.POINT_RADIUS, true, true
						)
				);
		
		//the 0.56 factor for the width comes from the fact, that the PNG has a width/height ratio of 0.56 and we want to preserve that, while making it as tall as a Line.
		red_player = new ImageView(
				new Image(
						View.class.getResourceAsStream("/redplayer.png"), 0.56 * PaperFootball.PLAYER_IMAGE_HEIGHT, PaperFootball.PLAYER_IMAGE_HEIGHT, true, true
						)
				);
		//the 0.82 factor for the width comes from the fact, that the PNG has a width/height ratio of 0.82 and we want to preserve that, while making it as tall as a Line.
		blue_player = new ImageView(
				new Image(
						View.class.getResourceAsStream("/blueplayer.png"), 0.82 * PaperFootball.PLAYER_IMAGE_HEIGHT, PaperFootball.PLAYER_IMAGE_HEIGHT, true, true
						)
				);
		
		red_player.setVisible(false);
		blue_player.setVisible(false);
		
		rotateBall = new RotateTransition(Duration.millis(500), ball_turn);
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
		
		
		//Mark each Player's goal with their color.
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
		
		
		//score display for player 1
		player1_score = new Label("Score: " + main.player1().score());
		player1_score.setOpacity(1.0);
		player1_score.setStyle("-fx-background-color: limegreen");
		
		player1_score.setPrefWidth(3 * PaperFootball.LINE_LENGTH - PaperFootball.LINE_STROKE_WIDTH);
		player1_score.setPrefHeight(PaperFootball.LINE_LENGTH - PaperFootball.LINE_STROKE_WIDTH);
		player1_score.setTranslateX(PaperFootball.BUFFER_AROUND_FIELD + PaperFootball.LINE_STROKE_WIDTH/2);
		player1_score.setTranslateY(PaperFootball.BUFFER_AROUND_FIELD + PaperFootball.LINE_STROKE_WIDTH/2);
		
		player1_score.setAlignment(Pos.CENTER);
		player1_score.setFont(new Font(20.0));
		
		
		//score display for player 2
		player2_score = new Label("Score: " + main.player2().score());
		player2_score.setOpacity(1.0);
		player2_score.setStyle("-fx-background-color: limegreen");
		
		player2_score.setPrefWidth(3 * PaperFootball.LINE_LENGTH - PaperFootball.LINE_STROKE_WIDTH);
		player2_score.setPrefHeight(PaperFootball.LINE_LENGTH - PaperFootball.LINE_STROKE_WIDTH);
		player2_score.setTranslateX(PaperFootball.BUFFER_AROUND_FIELD + PaperFootball.LINE_STROKE_WIDTH/2);
		player2_score.setTranslateY(PaperFootball.BUFFER_AROUND_FIELD + PaperFootball.LINE_STROKE_WIDTH/2 + 11 * PaperFootball.LINE_LENGTH);
		
		player2_score.setAlignment(Pos.CENTER);
		player2_score.setFont(new Font(20.0));
		
		
		//space for the player1-turn-indicator (rotating football) 
		player1_turn = new Label();
		player1_turn.setOpacity(1.0);
		player1_turn.setStyle("-fx-background-color: limegreen");
		
		player1_turn.setPrefWidth(3 * PaperFootball.LINE_LENGTH - PaperFootball.LINE_STROKE_WIDTH);
		player1_turn.setPrefHeight(PaperFootball.LINE_LENGTH - PaperFootball.LINE_STROKE_WIDTH);
		player1_turn.setTranslateX(PaperFootball.BUFFER_AROUND_FIELD + PaperFootball.LINE_STROKE_WIDTH/2 + 5 * PaperFootball.LINE_LENGTH);
		player1_turn.setTranslateY(PaperFootball.BUFFER_AROUND_FIELD + PaperFootball.LINE_STROKE_WIDTH/2);
		
		player1_turn.setAlignment(Pos.CENTER);

		
		//space for the player2-turn-indicator (rotating football) 
		player2_turn = new Label();
		player2_turn.setOpacity(1.0);
		player2_turn.setStyle("-fx-background-color: limegreen");
		
		player2_turn.setPrefWidth(3 * PaperFootball.LINE_LENGTH - PaperFootball.LINE_STROKE_WIDTH);
		player2_turn.setPrefHeight(PaperFootball.LINE_LENGTH - PaperFootball.LINE_STROKE_WIDTH);
		player2_turn.setTranslateX(PaperFootball.BUFFER_AROUND_FIELD + PaperFootball.LINE_STROKE_WIDTH/2 + 5 * PaperFootball.LINE_LENGTH);
		player2_turn.setTranslateY(PaperFootball.BUFFER_AROUND_FIELD + PaperFootball.LINE_STROKE_WIDTH/2 + 11 * PaperFootball.LINE_LENGTH);
		
		player2_turn.setAlignment(Pos.CENTER);
		
		
		getChildren().addAll(player1_score, player1_turn, player2_score, player2_turn, ball_game, red_player, blue_player);
		
		
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
			player1_turn.setGraphic(ball_turn);
			player2_turn.setGraphic(null);
			rotateBall.play();
			
		}
		else if(playerNr == 2) {
			
			rotateBall.stop();
			player1_turn.setGraphic(null);
			player2_turn.setGraphic(ball_turn);
			rotateBall.play();
		}
		
	}
	
	/**
	 * @return the current location of the ball (starting Point for the next move)
	 */
	public Point ball() {
		for(int y = 0; y < points.length; y++) {
			for(int x = 0; x < points[y].length; x++) {
				if(points[y][x].isBall()) return points[y][x];
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
	public boolean moveTo(Point p) {
		
		Point ball = ball();
		
		//if one of the Points reachable from the current location of the ball equals the point we want to move to.
		for(Point reachablePoint : ball.reachablePoints()) {
			if(reachablePoint.equals(p)) {
				

				main.addMoveToPlayersTurn(ball, reachablePoint);
				
				//show image of red player kicking ball
				if(main.player_turn() == 1) {
					
					//make sure the image lands inside the playing field since we want to avoid padding
					//the value 0.28 comes from the width/height ratio of the original PNG of 0.56
					//so 0.28 * height means "half of width"
					red_player.setX(
							Math.min(
									getPoint(8, 0).getCenterX() - 0.56 * PaperFootball.PLAYER_IMAGE_HEIGHT,
									Math.max(
											PaperFootball.BUFFER_AROUND_FIELD,
											ball.getCenterX() - 0.28 * PaperFootball.PLAYER_IMAGE_HEIGHT //<- this is the value we possibly have to adjust, hence min max
									)
							)
					);
					red_player.setY(
							Math.min(
									getPoint(4, 11).getCenterY() - PaperFootball.PLAYER_IMAGE_HEIGHT,
									Math.max(
											PaperFootball.BUFFER_AROUND_FIELD,
											ball.getCenterY() - PaperFootball.PLAYER_IMAGE_HEIGHT //<- this is the value we possibly have to adjust, hence min max
									)
							)
					);
					red_player.toFront();
					red_player.setVisible(true);
					
				}
				
				//show image of blue player kicking ball
				if(main.player_turn() == 2) {

					//make sure the image lands inside the playing field since we want to avoid padding
					blue_player.setX(
							Math.min(
									getPoint(8, 0).getCenterX() - 0.82 * PaperFootball.PLAYER_IMAGE_HEIGHT,
									Math.max(
											PaperFootball.BUFFER_AROUND_FIELD,
											ball.getCenterX() - 0.82 * PaperFootball.PLAYER_IMAGE_HEIGHT / 2 //<- this is the value we possibly have to adjust, hence min max
									)
							)
					);
					blue_player.setY(
							Math.min(
									getPoint(4, 11).getCenterY() - PaperFootball.PLAYER_IMAGE_HEIGHT,
									Math.max(
											PaperFootball.BUFFER_AROUND_FIELD,
											ball.getCenterY() - PaperFootball.PLAYER_IMAGE_HEIGHT //<- this is the value we possibly have to adjust, hence min max
									)
							)
					);
					blue_player.toFront();
					blue_player.setVisible(true);
					
				}
				
				
				
				Edge edge = getEdge(ball, reachablePoint);
				
				
				// the Duration of this Transition cannot be equal to the duration of Timeline moveSteps in PaperFootball
				// because that leads to the computer being unable to perform multi-move turns - stops action but not turn after first move
				TranslateTransition ball_transl = new TranslateTransition(new Duration(400), ball_game); 
				ball_transl.setFromX(ball.getCenterX() - 2.5 * PaperFootball.POINT_RADIUS);
				ball_transl.setFromY(ball.getCenterY() - 2.5 * PaperFootball.POINT_RADIUS);
				ball_transl.setToX(p.getCenterX() - 2.5 * PaperFootball.POINT_RADIUS);
				ball_transl.setToY(p.getCenterY() - 2.5 * PaperFootball.POINT_RADIUS);
				
				ball_transl.setOnFinished(evt -> {
					
					//draw the Edge first, so the Points stay on top.
					edge.draw(main.player_turn());
					
					//disable current Point highlights
					ball.hideAvailableMoves();
					
					//change the location of the ball
					ball.setBall(false);
					p.setBall(true);
					
					//make sure ball icon stays on top
					ball_game.toFront();
					
					//hide both player images
					red_player.setVisible(false);
					blue_player.setVisible(false);
					
					//move ends at an unused point -> turn ends.
					if(p.unused()) {
						
						p.put();
						p.toFront();
						
						
						ball_game.toFront();
						
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
						
						//if the next player is human, display available moves by letting the Points blink.
						boolean nextPlayerIsHuman = main.player(main.player_turn() % 2 + 1).isHuman(); 
						if(nextPlayerIsHuman) p.showAvailableMoves();
						
						
					}
					//move ends on a used Point -> turn continues or choke.
					else {
						//if the current player is human, show available moves by letting the Points blink.
						boolean currentPlayerIsHuman = main.player(main.player_turn()).isHuman(); 
						if(currentPlayerIsHuman) p.showAvailableMoves();
						
					}
					//check if human player chocked (is unable to finish his turn)
					if(p.reachablePointModels().length == 0) {
						main.choke();
					}

					
					
				});
				
				ball_transl.playFromStart();
				
				return true;
				
			}
		}
		return false;
		
	}
	
	public void continueToNextMove() {
		
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
	public void prepareField() {
		
		Point center = getPoint(points[0].length / 2, points.length / 2);
		
		center.put();
		center.setBall(true);
		
		ball_game.setX(0);
		ball_game.setY(0);
		
		ball_game.setTranslateX(center.getCenterX() - 2.5 * PaperFootball.POINT_RADIUS);
		ball_game.setTranslateY(center.getCenterY() - 2.5 * PaperFootball.POINT_RADIUS);
		
		ball_game.setVisible(true);
		ball_game.toFront();
		
		//if the starting player is human show available Moves.
		boolean startingPlayerIsHuman = (main.player(1).isHuman()); 
		if(startingPlayerIsHuman) center.showAvailableMoves();
		
	}
	
	/**
	 * @return PaperFootball. Main Class.
	 */
	public PaperFootball main() {
		return main;
	}
	
	/**
	 * Get the Edge between two given Points.
	 * It returns the Edge created at initiation, which may have inverted "from" and "to" ends.
	 * @param from first ending Point of the searched Edge.
	 * @param to second ending Point of the searched Edge.
	 * @return Edge
	 */
	public Edge getEdge(Point from, Point to) {
		
		Edge edgeCmp = new Edge(new LineModel(from.pointModel(), to.pointModel()));
		
		for(Edge edge : lines) {
			if(edge.equals(edgeCmp)) return edge;
		}
		return null;
	}
	
	
	
}
