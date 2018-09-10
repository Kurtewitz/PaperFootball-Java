package multiplayer;

import java.io.PrintWriter;

import view.PaperFootball;

/**
 * Implementation of {@link ClientListener}
 * @author Michał Lipiński
 * @date 10.09.2018
 * @updated 10.09.2018 version 0.2.9a
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
	public void recivedInput(ClientInstance client, String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serverClosed() {
		// TODO Auto-generated method stub
		System.out.println("server closed");
	}

}
