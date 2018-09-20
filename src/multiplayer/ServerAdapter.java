package multiplayer;

import java.io.PrintWriter;

import player.Network;
import view.PaperFootball;

/**
 * Implementation of {@link ClientListener}. Simple System.out.println's and message delivery.
 * @author Michał Lipiński
 * @date 10.09.2018
 * @updated 10.09.2018 version 0.3
 */
public class ServerAdapter implements ServerListener {
	
	private final PaperFootball main;
	
	public ServerAdapter(PaperFootball main) {
		this.main = main;
	}

	@Override
	public void clientConnected(ClientInstance client, PrintWriter out) {
		// TODO Auto-generated method stub
		System.out.println("client connected");
		main.opponentConnected();
	}

	@Override
	public void clientDisconnected(ClientInstance client) {
		// TODO Auto-generated method stub
		System.out.println("client disconnected");
	}

	@Override
	public void receivedInput(ClientInstance client, String msg) {
		// TODO Auto-generated method stub
		System.out.println("Server received message from " + client.ip + " : " + msg);
		//since we are on the Server side, we know we are hosting an online game, but just to be sure...
		if(!main.player(main.player_turn()).isLocal()) ((Network) main.player(main.player_turn()) ).messageReceived(msg);
		
		//TODO look up this sender's opponent and send him message
	}

	@Override
	public void serverClosed() {
		// TODO Auto-generated method stub
		System.out.println("server closed");
	}

}
