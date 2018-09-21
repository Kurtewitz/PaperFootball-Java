package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import player.Human;
import player.Network;

/**
 * Screen allowing the player to enter an Invite Code and connect to an online game.
 * @author Michał Lipiński
 * @date 10.09.2018
 * @updated 10.09.2018 version 0.3
 */
public class JoinGame extends VBox {

	
	/** main class */
	private final PaperFootball main;
	
	/** button starting a game with the chosen players */
	private final Button start;
	
	
	private final Button search;
	
	
	/** Label for displaying the status of player. Whether he connected to your Game ({@link Server} or not */
	private final Label connectionStatus;

	
	//__________String Constants__________
//	private final String _WAITING = "Enter an Invite Code and press Connect";
	private final String _CONNECTING = "Connecting...";
	private final String _CONNECTED = "Connected to game. Click START";
	
	
	
	public JoinGame(PaperFootball main) {
		super();
		
		this.main = main;
		
		
		this.setPrefWidth(8 * PaperFootball.LINE_LENGTH + PaperFootball.POINT_RADIUS + PaperFootball.BUFFER_AROUND_FIELD);
		this.setPrefHeight(12 * PaperFootball.LINE_LENGTH + PaperFootball.POINT_RADIUS + PaperFootball.BUFFER_AROUND_FIELD);
		this.setMaxWidth(8 * PaperFootball.LINE_LENGTH + PaperFootball.POINT_RADIUS + PaperFootball.BUFFER_AROUND_FIELD);
		this.setMaxHeight(12 * PaperFootball.LINE_LENGTH + PaperFootball.POINT_RADIUS + PaperFootball.BUFFER_AROUND_FIELD);
		
		
		this.setAlignment(Pos.CENTER);
		
		
		
		search = new Button("Search for opponent");
		search.setPrefHeight(1 * PaperFootball.LINE_LENGTH);
		search.setPrefWidth(3 * PaperFootball.LINE_LENGTH);
		search.setFont(new Font(22));
		
		search.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				connect( inviteCodeToIP( "192.168.2.104:8000" ) );
			}
		});

		
		
		
		connectionStatus = new Label(_CONNECTING);
		connectionStatus.setPrefHeight(1 * PaperFootball.LINE_LENGTH);
		connectionStatus.setFont(new Font(20));
//		player2Status.setPrefWidth(3 * PaperFootball.LINE_LENGTH);
		
		
		start = new Button("Start");
		start.setPrefHeight(1 * PaperFootball.LINE_LENGTH);
		start.setPrefWidth(3 * PaperFootball.LINE_LENGTH);
		start.setFont(new Font(22));
		
		start.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				startGame();
			}
		});
		start.setDisable(true);
		
		
		
		HBox player2Box = new HBox(PaperFootball.LINE_LENGTH);
		player2Box.setPrefWidth(8 * PaperFootball.LINE_LENGTH);
		player2Box.setAlignment(Pos.CENTER);
		player2Box.setPadding(new Insets(0, 0, 0, 0));
		player2Box.getChildren().addAll(connectionStatus);
		
		
		
		Label filler1 = new Label();
		filler1.setPrefHeight(PaperFootball.LINE_LENGTH);
		Label filler2 = new Label();
		filler2.setPrefHeight(2 * PaperFootball.LINE_LENGTH);
		Label filler3 = new Label();
		filler3.setPrefHeight(PaperFootball.LINE_LENGTH);
		
		getChildren().addAll(search, filler1, player2Box, filler2, start, filler3);
		
	}
	
	/**
	 * start a new online match with {@link Network} as Player1 and {@link Human} as Player2.
	 */
	protected void startGame() {
		
		
		if (main.getMyPlayerNr() == 1) {
			main.startGame(new Human(1, main),  new Network(2, main));
		}
		else if (main.getMyPlayerNr() == 2){
			main.startGame(new Network(1, main),  new Human(2, main));
		}
		
	}

	/**
	 * Turn the Invite Code back into the IP address of the game server
	 * @param inviteCode
	 * @return IP address String
	 */
	private String inviteCodeToIP(String inviteCode) {
		return inviteCode;
	}
	
	/**
	 * Connect to the game server with the given ip address
	 * @param ip
	 */
	private void connect(String ip) {
		main.connectToServer(ip);
	}
	
	/**
	 * Connection to the game server was successful. Update status text and enable the statr button.
	 */
	public void connectionSuccessful() {
		connectionStatus.setText(_CONNECTED);
		start.setDisable(false);
	}
}