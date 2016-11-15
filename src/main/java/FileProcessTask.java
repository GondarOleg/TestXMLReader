import org.apache.log4j.Logger;
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

    final static Logger logger = Logger.getLogger(FileProcessTask.class);

    private final File file;
    private final SessionFactory sessionFactory;

    public FileProcessTask(File file, SessionFactory sessionFactory) {
        this.file = file;
        this.sessionFactory = sessionFactory;
    }

    private void processFile(File file) throws IOException, JAXBException, ParserConfigurationException {
        try {
            if (XMLUtil.validateXML(file)) {
                EntryJAXB entryJAXB = unmarshal(file);
                Entry entry = new Entry();
                entry.setContent(entryJAXB.getContent());
                entry.setCreationDate(entryJAXB.getCreationDate());
                writeDataToDB(entry, sessionFactory);
                file.renameTo(new File(PropertyReader.getProcessedDir() + file.getName()));
            }
        } catch (SAXException e) {
            file.renameTo(new File(PropertyReader.getInvalidDir() + file.getName()));
            logger.error(e.getMessage());
        }

    }

    @Override
    public void run() {
        try {
            logger.info("Processing " + file.getName());
            processFile(file);
        } catch (IOException | JAXBException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static EntryJAXB unmarshal(File file) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(EntryJAXB.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        EntryJAXB entry = (EntryJAXB) jaxbUnmarshaller.unmarshal(file);
        logger.info("From unmarshal: " + entry.getContent());
        return entry;
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
