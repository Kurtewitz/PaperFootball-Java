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
import java.util.HashMap;

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
	private HashMap<InetAddress, Socket> sockets = new HashMap<>();
	private HashMap<InetAddress, ClientInstance> clients;
	
	
	public Server(int port, ServerListener listener){
		
		serverListener=listener;
		sockets = new HashMap<>();
		open = true;
		
		try {
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
										sockets.put(s.getInetAddress(), s);
										BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
										PrintWriter out = new PrintWriter(s.getOutputStream(), true);
										ClientInstance client = new ClientInstance(s.getInetAddress(), s.getPort(), out);
										clients.put(s.getInetAddress(), client);
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
												
												sockets.remove(s.getInetAddress());
												clients.remove(s.getInetAddress());
												return;
											}
										}
									} catch(Exception exception) { exception.printStackTrace(); }
									
									try { s.close();
									} catch(Exception exception) { exception.printStackTrace(); }
									
									sockets.remove(s.getInetAddress());
									clients.remove(s.getInetAddress());
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
		
		for(Socket s : sockets.values()){
			try{
				s.close();
			}
			catch(Exception exception){
				exception.printStackTrace();
			}
		}
		
		sockets.clear();
		sockets=null;
		clients.clear();
		clients = null;
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
	
	
	public void kickClient(InetAddress ip){
		Socket s = sockets.get(ip);
		if(s != null) {
			try {
				s.shutdownOutput();
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void sendMessage(ClientInstance recipient, String message) {
		if(recipient != null && message != null && message.length() > 0) {
			
			recipient.out.println(message);
			
		}
		
	}
	
	
}