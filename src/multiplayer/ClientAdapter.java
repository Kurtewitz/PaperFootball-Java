package multiplayer;

import player.Network;
import view.PaperFootball;

/**
 * Implementation of {@link ClientListener}
 * @author Michał Lipiński
 * @date 10.09.2018
 * @updated 10.09.2018 version 0.3
 */
public class ClientAdapter implements ClientListener {
	
	private PaperFootball main;
	
	public ClientAdapter(PaperFootball main) {
		this.main = main;
	}

	@Override
	public void unknownHost() {
		// TODO Auto-generated method stub
		System.out.println("unknown host");
	}

	@Override
	public void couldNotConnect() {
		// TODO Auto-generated method stub
		System.out.println("could not connect");
	}

	@Override
	public void receivedInput(String msg) {
		// TODO Auto-generated method stub
		System.out.println("Client received input " + msg);
		
		if(main.player_turn() == 0) {
			
			//GAMEREADY <111.222.333.444>    xxx.xxx.xxx.xxx = opponent IP
			
			if(msg.length() > 8 && msg.substring(0, 9).equals("GAMEREADY")) {
				main.opponentConnected();
				
				System.out.println("Playing against " + msg.substring(msg.indexOf('<') + 1, msg.indexOf('>')));
			}
			else {
				System.out.println("Ich bin Player nr " + msg);
				
				int playerNr = Integer.parseInt(msg);
				main.setMyPlayerNr(playerNr);
			}
		}
		
		//since we are on the Client side, we know we joined an online game, but just to be sure...
		
		else if(!main.player(main.player_turn()).isLocal()) ((Network) main.player(main.player_turn()) ).messageReceived(msg);
	}

	@Override
	public void serverClosed() {
		// TODO Auto-generated method stub
		System.out.println("server closed");
	}

	@Override
	public void disconnected() {
		// TODO Auto-generated method stub
		System.out.println("disconnected");
	}

	@Override
	public void connectedToServer() {
		// TODO Auto-generated method stub
		System.out.println("connected to server");
		main.connectedToServer();
	}

}
