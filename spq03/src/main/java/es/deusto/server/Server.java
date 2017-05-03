package es.deusto.server;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;

import es.deusto.server.remote.*;

@SuppressWarnings("deprecation")
public class Server {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("How to invoke: java [policy] [codebase] Server.Server [host] [port] [server]");
			System.exit(0);
		}

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}

		String name = "//" + args[0] + ":" + args[1] + "/" + args[2];

		try {
			
			ITransferer objServer = new Transferer();
			Naming.rebind(name, objServer);
			System.out.println("Money Transfering Server '" + name + "' active and waiting...");
			java.io.InputStreamReader inputStreamReader = new java.io.InputStreamReader ( System.in );
			java.io.BufferedReader stdin = new java.io.BufferedReader ( inputStreamReader );
			String line  = stdin.readLine();
		} catch (Exception e) {
			System.err.println("Transferer exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}