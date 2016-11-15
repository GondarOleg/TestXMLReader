import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Created by O.Gondar on 14.11.2016.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory ;
    static {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void writeDataToDB(Entry entry) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(entry);
        session.getTransaction().commit();
        session.close();
        System.out.println("Done");
    }
}
