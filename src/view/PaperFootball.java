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
/**
 * Main class. JavaFX {@link Application}
 * @author Michał Lipiński
 * @date 10.04.2017
 * @updated 14.07.2018 version 0.2.1
 */
public class PaperFootball extends Application {
	
	public static final String _VERSION = "version 0.2.1";

	//______________________Graphical Constants______________________
	public static final double POINT_RADIUS = 5.0;
	public static final double POINT_STROKE_WIDTH = 1.5;
	
	
	public static final double LINE_LENGTH = 50;
	public static final double LINE_STROKE_WIDTH = 5;
	
	public static final Color DEFAULT_COLOR = Color.BLACK;
	
	public static final Color LINE_EMPTY_COLOR = Color.TRANSPARENT;
	public static final Color PLAYER1_COLOR = Color.ORANGERED;
	public static final Color PLAYER2_COLOR = Color.DODGERBLUE;
	
	public static final Color POINT_UNUSED_COLOR = Color.LIGHTGREY;
	public static final Color POINT_USED_COLOR = Color.DIMGREY;
	public static final Color POINT_HIGHLIGHT_COLOR = Color.GOLDENROD;
	
	public static final double BUFFER_AROUND_FIELD = 6.5;//POINT_RADIUS;

	public static final double PLAYER_IMAGE_HEIGHT = 2 * LINE_LENGTH;
	//____________________Graphical Constants End____________________
	
	
	/** Integer storing which player's turn it is (1 or 2) */
	private int player_turn = 0;
	
	/** main Model for a game */
	private Model m;
	/** main View for a game */
	private View v;
	/** Menu to show at the start that let's the user chose the two player's for a game */
	private Menu menu;
	
	/** Player with the number 1 */
	private Player Player1;
	/** Player with the number 2 */
	private Player Player2;
	
	/** Stage to hold and display the graphical components of this application */
	Stage stage;
	
	
	
	
	public PaperFootball() {
		
		m = new Model();
		
		System.out.println(_VERSION);
		
	}
	
	
	/**
	 * Set the two given players as the players of this game,
	 * create a View for this game and statr the first turn.
	 * @brief Start the game with the two given players.
	 * @param player1
	 * @param player2
	 * 
	 */
	public void startGame(Player player1, Player player2) {
		
		
		Player1 = player1;//new AI(1, this);
		Player2 = player2;//new AI(2, this);

		v = new View(m, this);
		v.prepareField();
		nextTurn();
		

		Scene scene = new Scene(v);
		stage.setScene(scene);
		
	}
	
	
	/**
	 * calculate the next turn = update value of turn_player, set the turn indicator
	 * and if it's the AI's turn, calculate and animate the move
	 */
	public void nextTurn() {
		
		player_turn = player_turn % 2 + 1;
		
		//show the turn indicator at current player's side (football icon)
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				v.setTurnIndicator(player_turn);
			}
		});
		
		
		//create an empty turn, make it final so we can use it inside of an EventHandler
		final ArrayList<int[]> turn = new ArrayList<int[]>();
		
		//fill the turn with current player's moves
		if(!player(player_turn).isHuman()) {
			for(int[] move : player(player_turn).doTurn()) {
				turn.add(move);
			}
		}
		
		//check 
		if(!player(player_turn).isHuman() && turn.size() == 0) {
			System.out.println("Player" + player_turn + " chokes! Score so far " + player1().score() + " : " + player2().score());
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
	 * current player scores a goal = update current player's score and reset the field
	 */
	public void goal() {
		
		player(player_turn).setScore(player(player_turn).score() + 1);
		
		
		m = new Model();
		
		Player1.updateModel();
		Player2.updateModel();
		

		v = new View(m, this);
		v.prepareField();
		nextTurn();
		
		stage.setScene(new Scene(v));
	}
	
	/**
	 * current player gets stuck and cannot perform a move = other player scores + reset the field + next turn
	 */
	public void choke() {
		
		//other player scores
		player(player_turn % 2 + 1).setScore(player(player_turn).score() + 1);
		
		
		//reset the field + next turn
		m = new Model();
		
		Player1.updateModel();
		Player2.updateModel();
		

		v = new View(m, this);
		v.prepareField();
		nextTurn();
		
		stage.setScene(new Scene(v));
		
	}
	
	/**
	 * @return the main model of a game.
	 */
	public Model model() {
		return m;
	}
	
	/**
	 * @return Player 1
	 */
	public Player player1() {
		return Player1;
	}
	
	/** 
	 * @return Player 2
	 */
	public Player player2() {
		return Player2;
	}
	
	/**
	 * @return <code>1</code> if it's Player 1's turn <br>
	 * <code>2</code> if it's Player 2' turn.
	 */
	public int player_turn() {
		return player_turn;
	}
	
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
