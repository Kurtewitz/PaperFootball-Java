package view;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Model;
import player.Player;

public class PaperFootball extends Application {

	public static final double POINT_RADIUS = 5.0;
	public static final double POINT_STROKE_WIDTH = 1.5;
	
	
	public static final double LINE_LENGTH = 50;
	public static final double LINE_LENGTH_DIAGONAL = Math.sqrt(2 * LINE_LENGTH * LINE_LENGTH);
	public static final double LINE_STROKE_WIDTH = 5;
	
	
	public static final Color DEFAULT_COLOR = Color.BLACK;
	
	public static final Color LINE_EMPTY_COLOR = Color.TRANSPARENT;
	public static final Color PLAYER1_COLOR = Color.ORANGERED;
	public static final Color PLAYER2_COLOR = Color.DODGERBLUE;
	
	public static final Color POINT_EMPTY_COLOR = Color.LIGHTGREY;
	public static final Color POINT_USED_COLOR = Color.DIMGREY;
	public static final Color POINT_ACTIVE_COLOR = Color.GOLDENROD;
	
	public static final double BUFFER_AROUND_FIELD = POINT_RADIUS;// / 2 + POINT_STROKE_WIDTH + 2;
	
	public static int PLAYER_TURN = 0;
	
	private Model m;
	private View v;
	private Menu menu;
	
	private Player Player1;
	private Player Player2;
	
	Stage stage;
	
	
	public PaperFootball() {
		
		m = new Model();
		
		
	}
	
	
	
	public void startGame(Player player1, Player player2) {
		
		
		
		
		Player1 = player1;//new AI(1, this);
		Player2 = player2;//new AI(2, this);

		v = new View(m, this);
		v.start();
		nextTurn();
		

		Scene scene = new Scene(v);
		stage.setScene(scene);
		
	}
	
	
	/**
	 * calculate the next turn = update value of TURN_PLAYER, set the turn indicator
	 * and if it's the AI's turn, calculate and animate the move
	 */
	public void nextTurn() {
		
		PLAYER_TURN = PLAYER_TURN % 2 + 1;
		
		//show the turn indicator at current player's side (football icon)
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				v.setTurnIndicator(PLAYER_TURN);
			}
		});
		
		
		//create an empty turn, make it final so we can use it inside of an EventHandler
		final ArrayList<int[]> turn = new ArrayList<int[]>();
		
		//fill the turn with current player's moves
		if(!player(PLAYER_TURN).isHuman()) {
			for(int[] move : player(PLAYER_TURN).doTurn()) {
				turn.add(move);
			}
		}
		
		//check 
		if(!player(PLAYER_TURN).isHuman() && turn.size() == 0) {
			System.out.println("Player" + PLAYER_TURN + " chokes! Score so far " + player1().score() + " : " + player2().score());
			choke();
			System.out.println("New score " + player1().score() + " : " + player2().score());
		}
		
		//show AI move one step at a time instead of all at once
		final Timeline moveSteps = new Timeline(new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(turn.size() > 0) {
					Point toClick = v.getPoint(turn.get(0)[0], turn.get(0)[1]);
					toClick.click();
					
					turn.remove(0);
				}
			}
		}));
		moveSteps.setCycleCount(turn.size());
		moveSteps.play();
		
	}
	
	
	/**
	 * one player scores a goal = update the player's score and reset the field
	 */
	public void goal() {
		
		player(PLAYER_TURN).setScore(player(PLAYER_TURN).score() + 1);
		
		
		m = new Model();
		
		Player1.updateModel();
		Player2.updateModel();
		

		v = new View(m, this);
		v.start();
		nextTurn();
		
		stage.setScene(new Scene(v));
	}
	
	/**
	 * one player gets stuck and cannot perform a move = other player scores + reset the field + next turn
	 */
	public void choke() {
		
		//other player scores
		player(PLAYER_TURN % 2 + 1).setScore(player(PLAYER_TURN).score() + 1);
		
		
		//reset the field + next turn
		m = new Model();
		
		Player1.updateModel();
		Player2.updateModel();
		

		v = new View(m, this);
		v.start();
		nextTurn();
		
		stage.setScene(new Scene(v));
		
	}
	
	
	public Model model() {
		return m;
	}
	
	public Player player1() {return Player1;}
	public Player player2() {return Player2;}
	
	/**
	 * @param nr number of the player to be returned
	 * @return Player1 or Player2
	 */
	public Player player(int nr) {
		if (nr == 1) return Player1;
		else if (nr == 2) return Player2;
		return null;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		stage = primaryStage;
		
		
		menu = new Menu(this);
		stage.setScene(new Scene(menu));

		
		stage.show();
		
	}
	
	public static void main(String[] args) {
		PaperFootball.launch();
	}
	

}
