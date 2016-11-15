import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by O.Gondar on 15.11.2016.
 */
public class FileProcessTask implements Runnable {

    private final File file;
    private final SessionFactory sessionFactory;

    public FileProcessTask(File file, SessionFactory sessionFactory) {
        this.file = file;
        this.sessionFactory = sessionFactory;
    }

    private void processFile(File file) throws IOException, JAXBException, ParserConfigurationException, SAXException {
        if (XMLUtil.validateXML(file)) {

            if (XMLUtil.validateXML(file)) {
                EntryJAXB entryJAXB = unmarshal(file);
                Entry entry = new Entry();
                entry.setContent(entryJAXB.getContent());
                entry.setCreationDate(entryJAXB.getCreationDate());
                writeDataToDB(entry, sessionFactory);
                System.out.println("d:/test/processed/" + file.getName());
                file.renameTo(new File("d:/test/processed/" + file.getName()));
            } else {

            }
        }
        }

        @Override
        public void run() {
            try {
                processFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JAXBException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }

    public static EntryJAXB unmarshal(File file) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(EntryJAXB.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        EntryJAXB entry = (EntryJAXB) jaxbUnmarshaller.unmarshal(file);
        System.out.println("From unmarshal: " + entry.getContent());
        return entry;
    }

    public static void writeDataToDB(Entry entry, SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(entry);
        session.getTransaction().commit();
        session.close();
        System.out.println("Done");
    }

}
