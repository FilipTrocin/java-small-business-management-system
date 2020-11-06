package csc1035.project3;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    /**
     * Try to build a SessionFactory
     *
     * @return the built SessionFactory
     */
    private static SessionFactory buildSessionFactory() {

        try {

            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex){
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * @return the SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Close the SessionFactory
     */
    public static void shutdown(){
        getSessionFactory().close();
    }
}
