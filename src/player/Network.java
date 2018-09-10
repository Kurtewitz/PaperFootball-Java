package player;

import java.util.ArrayList;

import view.PaperFootball;

/**
 * This class is for sending and receiving messages (game state updates) to an opponent over Network.
 * At this point we don't really care, if there is a human or bot at the other end of the connection.
 * @author Michał Lipiński
 * @date 10.09.2018
 * @updated 10.09.2018 version 0.3
 */
public class Network extends Player {

	/** String for storing a received Message with instructions for a turn */
	private String receivedMessage = "";
	
	/** boolean used for interrupting a while loop */
	private boolean newMessage = false;
	
	
	public Network(int nr, PaperFootball main) {
		super(nr, true, main, false);
	}

	@Override
	public ArrayList<int[]> doTurn() {
		
/*		Task<Void> sleeper = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					
				}
				return null;
			}
		};
		sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				
			}
		});
		new Thread(sleeper).start();
*/
		
		while(!newMessage) {
			try {
				Thread.sleep(1000);
				newMessage = receivedMessage != null && receivedMessage != "";
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return processMessage();
		
	}

	/**
	 * A new message arrived and is ready to be processed.
	 * @param message with the network player's turn
	 */
	public void messageReceived(String message) {
		receivedMessage = message;
	}
	
	/**
	 * Turn a message like "N>NE>NW>E" into a list of coordinates of points to click.
	 * @return list of coordinates of points to click.
	 */
	public ArrayList<int[]> processMessage() {
		if(receivedMessage == null || receivedMessage == "") {
			System.out.println("Network Player tries to process a Message, but there is no message to be processed.");
			newMessage = false;
			return null;
		}
		
		ArrayList<int[]> toClick = new ArrayList<>();
		
		String[] directions = receivedMessage.split(">");
		
		int currentX = main.ball()[0];
		int currentY = main.ball()[1];
		
		for(String direction : directions) {
			
			if(direction.contains("W")) currentX--;
			else if(direction.contains("E")) currentX++;
			
			if(direction.contains("N")) currentY--;
			else if(direction.contains("S")) currentY++;
			
			toClick.add(new int[] {currentX, currentY});
			
		}
		
		receivedMessage = "";
		newMessage = false;
		
		return toClick;
	}
	
	
	
	
}
