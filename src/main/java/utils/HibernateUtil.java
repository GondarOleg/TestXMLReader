package utils;

import entries.Entry;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Created by O.Gondar on 14.11.2016.
 */
public class HibernateUtil {

    final static Logger logger = Logger.getLogger(HibernateUtil.class);

    private SessionFactory sessionFactory;

    public HibernateUtil() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void writeDataToDB(Entry entry, SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(entry);
        session.getTransaction().commit();
        session.close();
        logger.info("Done writing data to DB");
    }
}
