package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import player.AI;
import player.Human;
import player.Player;

public class Menu extends VBox {

	
	PaperFootball main;
	
	Button start;
	
	ComboBox<String> player1;
	ComboBox<String> player2;
	
	ComboBox<String> player1Diff;
	ComboBox<String> player2Diff;
	
	String human = "Human";
	String ai = "Computer";
	
	String diff1 = "Easy";
	String diff2 = "Medium";
	String diff3 = "Hard";
	
	
	
	public Menu(PaperFootball main) {
		super();
		
		this.main = main;
		
		
		this.setPrefWidth(8 * PaperFootball.LINE_LENGTH + PaperFootball.POINT_RADIUS + PaperFootball.BUFFER_AROUND_FIELD);
		this.setPrefHeight(12 * PaperFootball.LINE_LENGTH + PaperFootball.POINT_RADIUS + PaperFootball.BUFFER_AROUND_FIELD);
		this.setMaxWidth(8 * PaperFootball.LINE_LENGTH + PaperFootball.POINT_RADIUS + PaperFootball.BUFFER_AROUND_FIELD);
		this.setMaxHeight(12 * PaperFootball.LINE_LENGTH + PaperFootball.POINT_RADIUS + PaperFootball.BUFFER_AROUND_FIELD);
		
		
		this.setAlignment(Pos.CENTER);
		
		
		
		//we need this to change the font size in the comboboxes
		Callback<ListView<String>, ListCell<String>> cellFactory = new Callback<ListView<String>, ListCell<String>>() {
			
			@Override
			public ListCell<String> call(ListView<String> param) {
				
				final ListCell<String> cell = new ListCell<String>() {
					
					@Override
					public void updateItem(String item, boolean empty) {
						
						super.updateItem(item, empty);
						
						if (item != null) {
							setText(item);
							setFont(Font.font(this.getFont().getName(), 20.0));
						} else {
							setText(null);
						}
					}
				};
				return cell;
			}
		};
		
		Label player1Label = new Label("Choose settings for player 1");
		player1Label.setPrefHeight(1 * PaperFootball.LINE_LENGTH);
		player1Label.setFont(new Font(20));
		
		
		player1 = new ComboBox<String>(FXCollections.observableArrayList(human, ai));
		player1.setPrefHeight(1 * PaperFootball.LINE_LENGTH);
		player1.setPrefWidth(3 * PaperFootball.LINE_LENGTH);
		player1.setButtonCell(cellFactory.call(null));
		player1.setCellFactory(cellFactory);
		player1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				if(newValue.equals(human)) player1Diff.setDisable(true);
				else player1Diff.setDisable(false);
			}
		});
		
		
		
		player1Diff = new ComboBox<String>(FXCollections.observableArrayList(diff1, diff2, diff3));
		player1Diff.setPrefHeight(1 * PaperFootball.LINE_LENGTH);
		player1Diff.setPrefWidth(2 * PaperFootball.LINE_LENGTH);
		player1Diff.setButtonCell(cellFactory.call(null));
		player1Diff.setCellFactory(cellFactory);
		player1Diff.getSelectionModel().select(diff1);
		
		
		Label player2Label = new Label("Choose settings for player 2");
		player2Label.setPrefHeight(1 * PaperFootball.LINE_LENGTH);
		player2Label.setFont(new Font(20));

		
		player2 = new ComboBox<String>(FXCollections.observableArrayList(human, ai));
		player2.setPrefHeight(1 * PaperFootball.LINE_LENGTH);
		player2.setPrefWidth(3 * PaperFootball.LINE_LENGTH);
		player2.setButtonCell(cellFactory.call(null));
		player2.setCellFactory(cellFactory);
		player2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				if(newValue.equals(human)) player2Diff.setDisable(true);
				else player2Diff.setDisable(false);
			}
		});
		
		player2Diff = new ComboBox<String>(FXCollections.observableArrayList(diff1, diff2, diff3));
		player2Diff.setPrefHeight(1 * PaperFootball.LINE_LENGTH);
		player2Diff.setPrefWidth(2 * PaperFootball.LINE_LENGTH);
		player2Diff.setButtonCell(cellFactory.call(null));
		player2Diff.setCellFactory(cellFactory);
		player2Diff.getSelectionModel().select(diff1);
		
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
		
		
		HBox player1Box = new HBox(PaperFootball.LINE_LENGTH);
		player1Box.setPrefWidth(8 * PaperFootball.LINE_LENGTH);
		player1Box.setAlignment(Pos.CENTER);
		player1Box.setPadding(new Insets(0, 0, 0, 0));
		player1Box.getChildren().addAll(player1, player1Diff);
		
		HBox player2Box = new HBox(PaperFootball.LINE_LENGTH);
		player2Box.setPrefWidth(8 * PaperFootball.LINE_LENGTH);
		player2Box.setAlignment(Pos.CENTER);
		player2Box.setPadding(new Insets(0, 0, 0, 0));
		player2Box.getChildren().addAll(player2, player2Diff);
		
		
		
		Label filler1 = new Label();
		filler1.setPrefHeight(PaperFootball.LINE_LENGTH);
		Label filler2 = new Label();
		filler2.setPrefHeight(2 * PaperFootball.LINE_LENGTH);
		Label filler3 = new Label();
		filler3.setPrefHeight(PaperFootball.LINE_LENGTH);
		
		getChildren().addAll(player1Label, player1Box, filler1, player2Label, player2Box, filler2, start, filler3);
		

		player1.getSelectionModel().select(human);
		player2.getSelectionModel().select(ai);
		
		
	}
	
	/**
	 * read the values selected in the combo boxes and start a game with the selected settings
	 */
	public void startGame() {
		
		Player p1;
		Player p2;
		
		if(player1.getSelectionModel().getSelectedItem().equals(human)) {
			p1 = new Human(1, main);
		}
		else {
			String difficulty = player1Diff.getSelectionModel().getSelectedItem();
			
			int diff = difficulty.equals(diff1) ? 1 : difficulty.equals(diff2) ? 2 : 3;
			
			p1 = new AI(1, diff, main);
		}
		
		
		if(player2.getSelectionModel().getSelectedItem().equals(human)) {
			p2 = new Human(2, main);
		}
		else {
			String difficulty = player2Diff.getSelectionModel().getSelectedItem();
			
			int diff = difficulty.equals(diff1) ? 1 : difficulty.equals(diff2) ? 2 : 3;
			
			p2 = new AI(2, diff, main);
		}
		
		
		main.startGame(p1, p2);
		
	}
	
	
}
