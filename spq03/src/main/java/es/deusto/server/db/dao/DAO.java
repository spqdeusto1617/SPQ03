package es.deusto.server.db.dao;
import javax.jdo.*;

import java.util.ArrayList;
import java.util.List;

import es.deusto.server.db.data.*;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class DAO implements IDAO {

    private PersistenceManagerFactory pmf;
    //	final Logger logger = LoggerFactory.getLogger(DAO.class);
    public DAO(){
        pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
    }

    @Override
    public boolean storeUser(User u) {

        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        boolean ret=true;
        try {
            tx.begin();

            pm.makePersistent(u);
            tx.commit();
        } catch (Exception ex) {
//		    	logger.error("   $ Error storing an object: " + ex.getMessage());
            ret=false;

        } finally {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            pm.close();
        }
        return ret;
    }

    @Override
    public User retrieveUser(String login) {
        User user = null;
        PersistenceManager pm = pmf.getPersistenceManager();
        pm.getFetchPlan().setMaxFetchDepth(2);
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            user = pm.getObjectById(User.class, login);
            tx.commit();
        } catch (javax.jdo.JDOObjectNotFoundException jonfe)
        {
//			logger.warn("User does not exist: " + jonfe.getMessage());
        }

        finally {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            pm.close();
        }

        return user;
    }

    @Override
    public boolean updateUser(User u) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        boolean r =true;
        try {
            tx.begin();
            pm.makePersistent(u);
            tx.commit();
        } catch (Exception ex) {
//	    	   	logger.error("Error updating a user: " + ex.getMessage());
            r=false;
        } finally {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            pm.close();
        }
        return r;
    }


    @Override
    public	boolean storeProd(Product p){
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        boolean r=true;
        try {
            tx.begin();

            pm.makePersistent(p);
            tx.commit();
        } catch (Exception ex) {
//		    	 	logger.error("   $ Error storing an object: " + ex.getMessage());
            r=false;
        } finally {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            pm.close();
        }
        return r;
    }

    @Override
    public List<Product> getAllProd() {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        pm.getFetchPlan().setMaxFetchDepth(3);

        List<Product> products=new ArrayList<>();
        try {

            tx.begin();
            Extent<Product> extentP = pm.getExtent(Product.class);
            for (Product p : extentP) {
                products.add(p);
            }
            tx.commit();

        } catch (Exception ex) {
//        	   logger.error("# Error getting Extent getAllGames: " + ex.getMessage());
        } finally {

            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();

        }
        return products;
    }

    @Override
    public Product retrieveProdSearch(String name){
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        pm.getFetchPlan().setMaxFetchDepth(3);
        Product p = null;
        try {
            tx.begin();
            Extent<Product> extentP = pm.getExtent(Product.class);

            for (Product c : extentP) {

                if (p.getName().equals(name)) {
                    p = c;
//                    logger.info("Retrieve by paparameter " + p.getName());
                }
            }
            tx.commit();
        } catch (Exception ex) {
//        	   logger.error("# Error getting Extent Game: " + ex.getMessage());
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        //      logger.error(u);
        return p;
    }

    @Override
    public	boolean updateProd(Product p){
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        boolean r=true;
        try {
            tx.begin();
            pm.makePersistent(p);
            tx.commit();
        } catch (Exception ex) {
//	    	   	logger.error("Error updating a game: " + ex.getMessage());
            r=false;
        } finally {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            pm.close();
        }
        return r;
    }

}