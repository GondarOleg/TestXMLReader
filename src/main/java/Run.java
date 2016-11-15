import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Run {
    final static Logger logger = Logger.getLogger(Run.class);

    public static void main(String[] args) throws IOException, JAXBException {
        HibernateUtil hibernateUtil = new HibernateUtil();
        File dir = new File(PropertyReader.getMonitorDir());
        File[] files = dir.listFiles();
        logger.info(files.length + " files found");
        ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        if (files.length != 0) {
            for (File file : files) {
                exec.submit(new FileProcessTask(file, hibernateUtil.getSessionFactory()));
            }
        }
    }
}
