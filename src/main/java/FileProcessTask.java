import org.hibernate.SessionFactory;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
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
            Entry entry = new Entry();
            entry = XMLUtil.unmarshal(file);
            HibernateUtil.writeDataToDB(entry);
            file.renameTo(new File("d:/test/processed/" + file.getName()));
        } else {
            file.renameTo(new File("d:/test/invalid/" + file.getName()));
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
}
