import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

/**
 * Created by O.Gondar on 15.11.2016.
 */
public class FileProcessTask implements Runnable {

    private final File file;

    public FileProcessTask(File file) {
        this.file = file;
    }

    private void processFile(File file) throws IOException, JAXBException {
        if (XMLUtil.validateXML(file)) {
            Entry entry = XMLUtil.unmarshal(file);
            HibernateUtil.writeDataToDB(entry);
            file.renameTo(new File("d:/test/processed" + file.getName()));
        } else {
            file.renameTo(new File("d:/test/invalid" + file.getName()));
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
        }
    }
}
