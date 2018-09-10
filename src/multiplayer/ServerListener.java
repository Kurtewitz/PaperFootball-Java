package multiplayer;

import java.io.PrintWriter;

/**
 * Basically a rip-off of https://github.com/TheDudeFromCI/WraithEngine/tree/5397e2cfd75c257e4d96d0fd6414e302ab22a69c/WraithEngine/src/wraith/library/Multiplayer
 * @author Michał Lipiński, TheDudeFromCI
 * @date 10.09.2018
 * @updated 10.09.2018 version 0.2.9a
 */
public interface ServerListener{
	public void clientConnected(ClientInstance client, PrintWriter out);
	public void clientDisconnected(ClientInstance client);
	public void recivedInput(ClientInstance client, String msg);
	public void serverClosed();
}