import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import es.deusto.server.db.data.Product;
import es.deusto.server.db.data.User;
import junit.framework.JUnit4TestAdapter;
import es.deusto.server.Server;
import es.deusto.server.db.DB;
import es.deusto.server.db.dao.IDAO;

public class JUnit {
	
	User u;
	String name;
	Product p;
	Server server;
	DB dataBase;
	java.util.List<Product> products = new ArrayList<Product>();
	IDAO dao;
	
	Remote remote;
	
	final static org.slf4j.Logger logger = LoggerFactory.getLogger(JUnit.class);
	
	private static Thread rmiRegistryThread = null;
	private static Thread rmiServerThread = null;
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(JUnit.class);
	}
	
	@BeforeClass static public void setUp() {
		// Launch the RMI registry
		class RMIRegistryRunnable implements Runnable {

			public void run() {
				try {
					java.rmi.registry.LocateRegistry.createRegistry(1099);
					System.out.println("BeforeClass: RMI registry ready.");
				} catch (Exception e) {
					System.out.println("Exception starting RMI registry:");
					e.printStackTrace();
				}	
			}
		}
		
		rmiRegistryThread = new Thread(new RMIRegistryRunnable());
		rmiRegistryThread.start();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		
		class RMIServerRunnable implements Runnable {

			public void run() {
				System.setProperty("java.security.policy", "target\\test-classes\\security\\java.policy");

				if (System.getSecurityManager() == null) {
					System.setSecurityManager(new SecurityManager());
				}

				String name = "//127.0.0.1:1099/MessengerRMIDAO";
				System.out.println("BeforeClass - Setting the server ready TestServer name: " + name);

				try {
					
					Remote s = (Remote) new Server();
					Naming.rebind(name,s);
				} catch (RemoteException re) {
					System.err.println(" # Messenger RemoteException: " + re.getMessage());
					re.printStackTrace();
					System.exit(-1);
				} catch (MalformedURLException e) {
					System.err.println(" # Messenger MalformedURLException: " + e.getMessage());
					e.printStackTrace();
					System.exit(-1);
				}
			}
		}
		rmiServerThread = new Thread(new RMIServerRunnable());
		rmiServerThread.start();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	
	}
	
	@Before
	public void setUpUser(){
		try {
			System.setProperty("java.security.policy", "target\\test-classes\\security\\java.policy");

			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}

			String name = "//127.0.0.1:1099/MSRMIDAO";
			System.out.println("BeforeTest - Setting the client ready for calling TestServer name: " + name);
			remote = (Remote) java.rmi.Naming.lookup(name);
			}
			catch (Exception re) {
				System.err.println(" # Messenger RemoteException: " + re.getMessage());
				System.exit(-1);
			} 
	}
	
	@Test
	public void testStoreUser(){
		try {
			System.setProperty("java.security.policy", "target\\test-classes\\security\\java.policy");

			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}

			String name = "//127.0.0.1:1099/SMRMIDAO";
			System.out.println("BeforeTest - Setting the client ready for calling TestServer name: " + name);
			remote = (Remote) java.rmi.Naming.lookup(name);
			}
			catch (Exception re) {
				System.err.println(" # Messenger RemoteException: " + re.getMessage());
				re.printStackTrace();
				System.exit(-1);
			} 
	}
	
	@Test
	public void testRetrieveUser(){
			/*boolean t = false;
			String a = null;
			try {
				a = remote.getLogin();
				logger.info(a);
			} catch (RemoteException e) {
				logger.error(" # RemoteException: " + e.getMessage());
				logger.trace(e.getMessage());
			}
			if(a!=null) {
				t = true;
			}
			assertTrue(t);*/
	}
	
	@Test
	public void testUpdateUser(){
		try {
			System.setProperty("java.security.policy", "target\\test-classes\\security\\java.policy");

			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}

			String name = "//127.0.0.1:1099/SMRMIDAO";
			System.out.println("BeforeTest - Setting the client ready for calling TestServer name: " + name);
			remote = (Remote) java.rmi.Naming.lookup(name);
			}
			catch (Exception re) {
				System.err.println(" # Messenger RemoteException: " + re.getMessage());
				re.printStackTrace();
				System.exit(-1);
			} 
	}

	@Test
	public void testStoreProduct() {
		dao.storeProd(p);
		assertEquals(1, dao.getAllProd().size());
		assertEquals(p.getName(),dao.getAllProd().get(0).getName());
	}
	
	@Test
	public void testGetAllProd(){
		
	}
	
	@Test
	public void testUpdateProduct(){
		dao.updateProd(p);
		assertEquals(1, dao.getAllProd().size());
		assertEquals(p.getName(),dao.getAllProd().get(0).getName());
	}
	
	@AfterClass 
	static public void tearDown() {
		try	{
			rmiServerThread.join();
			rmiRegistryThread.join();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		

	}
	
	

}

