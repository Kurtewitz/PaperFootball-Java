package multiplayer;

import java.net.InetAddress;

/**
 * Basically a rip-off of https://github.com/TheDudeFromCI/WraithEngine/tree/5397e2cfd75c257e4d96d0fd6414e302ab22a69c/WraithEngine/src/wraith/library/Multiplayer
 * @author Michał Lipiński, TheDudeFromCI
 * @date 10.09.2018
 * @updated 10.09.2018 version 0.2.9a
 */
public class ClientInstance{
	
	public final InetAddress ip;
	public final int port;
	
	public ClientInstance(InetAddress ip, int port){
		this.ip=ip;
		this.port=port;
	}
	
	@Override
	public String toString() {
		return ip.toString()+":"+port;
	}
}