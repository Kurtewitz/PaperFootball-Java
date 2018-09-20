package view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import multiplayer.Server;
import player.Human;
import player.Network;

/**
 * Screen allowing the player to open a game lobby ({@link Server}) and wait for opponent to connect.
 * @author Michał Lipiński
 * @date 10.09.2018
 * @updated 10.09.2018 version 0.3
 */
public class HostGame extends VBox {

	
	/** main class */
	private final PaperFootball main;
	
	/** button starting a game with the chosen players */
	private final Button start;
	
	/** For displaying an invite code for people to join your game */
	private final TextField inviteCode;
	/** TextArea for displaying the status of player. Whether he connected to your Game ({@link Server} or not */
	private final Label player2Status;

	
	//__________String Constants__________
	private final String _WAITING = "Waiting for opponent to connect...";
	private final String _CONNECTED = "Opponent has connected. Click START";
	
	
	
	public HostGame(PaperFootball main) {
		super();
		
		this.main = main;
		
		
		this.setPrefWidth(8 * PaperFootball.LINE_LENGTH + PaperFootball.POINT_RADIUS + PaperFootball.BUFFER_AROUND_FIELD);
		this.setPrefHeight(12 * PaperFootball.LINE_LENGTH + PaperFootball.POINT_RADIUS + PaperFootball.BUFFER_AROUND_FIELD);
		this.setMaxWidth(8 * PaperFootball.LINE_LENGTH + PaperFootball.POINT_RADIUS + PaperFootball.BUFFER_AROUND_FIELD);
		this.setMaxHeight(12 * PaperFootball.LINE_LENGTH + PaperFootball.POINT_RADIUS + PaperFootball.BUFFER_AROUND_FIELD);
		
		
		this.setAlignment(Pos.CENTER);
		
		
		
		
		Label player1Label = new Label("Invite Code");
		player1Label.setPrefHeight(1 * PaperFootball.LINE_LENGTH);
		player1Label.setFont(new Font(20));
		
		
		inviteCode = new TextField(generateInviteCode());
		inviteCode.setPrefHeight(1 * PaperFootball.LINE_LENGTH);
//		inviteCode.setPrefWidth(3 * PaperFootball.LINE_LENGTH);
		inviteCode.setEditable(false);
		inviteCode.setFont(new Font(20));
//		inviteCode.setDisable(true);
		

		
		player2Status = new Label(_WAITING);
		player2Status.setPrefHeight(1 * PaperFootball.LINE_LENGTH);
		player2Status.setFont(new Font(20));
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
		
		
		HBox player1Box = new HBox(PaperFootball.LINE_LENGTH);
		player1Box.setPrefWidth(8 * PaperFootball.LINE_LENGTH);
		player1Box.setAlignment(Pos.CENTER);
		player1Box.setPadding(new Insets(0, 0, 0, 0));
		player1Box.getChildren().addAll(inviteCode);
		
		HBox player2Box = new HBox(PaperFootball.LINE_LENGTH);
		player2Box.setPrefWidth(8 * PaperFootball.LINE_LENGTH);
		player2Box.setAlignment(Pos.CENTER);
		player2Box.setPadding(new Insets(0, 0, 0, 0));
		player2Box.getChildren().addAll(player2Status);
		
		
		
		Label filler1 = new Label();
		filler1.setPrefHeight(PaperFootball.LINE_LENGTH);
		Label filler2 = new Label();
		filler2.setPrefHeight(2 * PaperFootball.LINE_LENGTH);
		Label filler3 = new Label();
		filler3.setPrefHeight(PaperFootball.LINE_LENGTH);
		
		getChildren().addAll(player1Label, player1Box, filler1, player2Box, filler2, start, filler3);
		
	}
	
	/**
	 * Opponent connected to our server. Update status text and enable the start button.
	 */
	public void opponentConnected() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				player2Status.setText(_CONNECTED);
				start.setDisable(false);
			}			
		});
	}
	
	/**
	 * Generate an Invite Code based on the server's IP
	 * @return Invite Code
	 */
	public String generateInviteCode() {
		return "87.149.111.1";//main.server().getIp();
	}
	
	/**
	 * Start an online match with {@link Human} as Player1 and {@link Network} as Player2.
	 */
	public void startGame() {
		main.startGame(new Human(1, main), new Network(2, main));
	}
	
}
