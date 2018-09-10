package multiplayer;

import view.PaperFootball;

/**
 * Implementation of {@link ClientListener}
 * @author Michał Lipiński
 * @date 10.09.2018
 * @updated 10.09.2018 version 0.2.9a
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
	public void recivedInput(String msg) {
		// TODO Auto-generated method stub
		System.out.println("received input " + msg);
		
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
