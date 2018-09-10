package multiplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Basically a rip-off of https://github.com/TheDudeFromCI/WraithEngine/tree/5397e2cfd75c257e4d96d0fd6414e302ab22a69c/WraithEngine/src/wraith/library/Multiplayer
 * @author Michał Lipiński, TheDudeFromCI
 * @date 10.09.2018
 * @updated 10.09.2018 version 0.3
 */
public class Server{
	
	private int port;
	private boolean open = true;
	private ServerSocket ss;
	private ServerListener serverListener;
	private ArrayList<Socket> clients = new ArrayList<>();
	/** Since a Server currently only needs to support one Client connection, we want to be able to send messages */
	private PrintWriter out;
	
	public Server(int port, ServerListener listener){
		
		serverListener=listener;
		clients = new ArrayList<>();
		open = true;
		try{
			ss = new ServerSocket(port);
			
			if(this.port==0) {
				this.port = ss.getLocalPort();
			}
			else {
				this.port=port;
			}
			
			Thread serverThread = new Thread(new Runnable(){
				
				public void run() {
					while(open) {
						try {
							
							final Socket s = ss.accept();
							
							Thread clientThread = new Thread(new Runnable(){
								
								public void run(){
									try{
										clients.add(s);
										BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
										out = new PrintWriter(s.getOutputStream(), true);
										ClientInstance client = new ClientInstance(s.getInetAddress(), s.getPort());
										serverListener.clientConnected(client, out);
										
										while(open){
											try {
												serverListener.receivedInput(client, in.readLine());
											}
											catch(IOException e) {
												serverListener.clientDisconnected(client);
												try {
													if(!s.isClosed()) {
														s.shutdownOutput();
														s.close();
													}
												} catch(Exception exception) { exception.printStackTrace(); }
												
												clients.remove(s);
												return;
											}
										}
									} catch(Exception exception) { exception.printStackTrace(); }
									
									try { s.close();
									} catch(Exception exception) { exception.printStackTrace(); }
									
									clients.remove(s);
								}
							});
							
							clientThread.setDaemon(true);
							clientThread.setName("Client "+s.getInetAddress().toString());
							clientThread.start();
							
						}catch(SocketException e){  //Do nothing
						}catch(IOException e){ e.printStackTrace(); }
					}
				}
			});
			
			serverThread.setDaemon(true);
			serverThread.setName("Server");
			serverThread.start();
			
		} catch(IOException e) { e.printStackTrace(); }
	}
	
	
	public void dispose(){
		open=false;
		
		try {
			ss.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		for(Socket s : clients){
			try{
				s.close();
			}
			catch(Exception exception){
				exception.printStackTrace();
			}
		}
		
		clients.clear();
		clients=null;
		ss=null;
		serverListener.serverClosed();
		serverListener=null;
	}
	
	
	public String getIp(){
		try{
			ss.getInetAddress();
			return InetAddress.getLocalHost().getHostAddress();
		}
		catch(UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void kickClient(ClientInstance client){
		Socket s;
		for(int i = 0; i<clients.size(); i++){
			s=clients.get(i);
			if(client.ip==s.getInetAddress()&&s.getPort()==client.port){
				try{
					s.shutdownOutput();
					s.close();
				}catch(IOException e){ e.printStackTrace(); }
				return;
			}
		}
	}
	
	/**
	 * Since our Server currently only connects with Client,
	 * this method can be used to send messages directly to that one Client.
	 * @param msg
	 */
	public void send(String msg) {
		if(open) {
			out.println(msg);
		}
	}
}