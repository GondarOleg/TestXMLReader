package tasks;

import entries.Entry;
import entries.EntryJAXB;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.PropertyReaderUtil;
import utils.XMLUtil;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileProcessTask implements Runnable {

    final static Logger logger = Logger.getLogger(FileProcessTask.class);

    private final File file;
    private final SessionFactory sessionFactory;

    public FileProcessTask(File file, SessionFactory sessionFactory) {
        this.file = file;
        this.sessionFactory = sessionFactory;
    }

    private void processFile(File file) throws IOException, JAXBException, ParserConfigurationException {
        if (XMLUtil.validateXML(file)) {
            logger.info("Processing valid file.");
            EntryJAXB entryJAXB = unmarshall(file);
            Entry entry = new Entry();
            entry.setContent(entryJAXB.getContent());
            entry.setCreationDate(entryJAXB.getCreationDate());
            writeDataToDB(entry, sessionFactory);
            Files.move(file.toPath(), new File(PropertyReaderUtil.getProcessedDir() + file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } else {
            logger.info("Not processing invalid file.");
            Files.move(file.toPath(), new File(PropertyReaderUtil.getInvalidDir() + file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
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

    public EntryJAXB unmarshall(File file) throws JAXBException {
        EntryJAXB entry = JAXB.unmarshal(file, EntryJAXB.class);
        logger.info("Unmarshalling " + file.getName());
        return entry;
    }

    public void writeDataToDB(Entry entry, SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(entry);
        session.getTransaction().commit();
        session.close();
        logger.info("Done writing data to DB");
    }
}
