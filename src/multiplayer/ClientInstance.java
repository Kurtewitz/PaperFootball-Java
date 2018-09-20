package multiplayer;

import java.io.PrintWriter;
import java.net.InetAddress;

/**
 * Basically a rip-off of https://github.com/TheDudeFromCI/WraithEngine/tree/5397e2cfd75c257e4d96d0fd6414e302ab22a69c/WraithEngine/src/wraith/library/Multiplayer
 * @author Michał Lipiński, TheDudeFromCI
 * @date 10.09.2018
 * @updated 10.09.2018 version 0.3
 */
public class ClientInstance{
	
	public final InetAddress ip;
	public final int port;
	public final PrintWriter out;
	
	public ClientInstance(InetAddress ip, int port, PrintWriter out){
		this.ip=ip;
		this.port=port;
		this.out = out;
	}
	
	@Override
	public String toString() {
		return ip.toString()+":"+port;
	}
}