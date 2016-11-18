package tasks;

import org.apache.log4j.Logger;
import utils.HibernateUtil;
import utils.PropertyReaderUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScheduledTask extends TimerTask{
    final static Logger logger = Logger.getLogger(ScheduledTask.class);

    @Override
    public void run() {
        processFiles();
        moveInvalidFiles();
    }

    private void processFiles(){
        HibernateUtil hibernateUtil = new HibernateUtil();
        logger.info(getFiles().length + " files found");
        ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        if (getFiles().length != 0) {
            for (File file : getFiles()) {
                exec.submit(new FileProcessTask(file, hibernateUtil.getSessionFactory()));
            }
        }
    }

    private void moveInvalidFiles(){
        logger.info(getFiles().length + " invalid files found");
        for (File file : getFiles()) {
            try {
                Files.move(file.toPath(), new File(PropertyReaderUtil.getInvalidDir() + file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    private File[] getFiles(){
        File dir = new File(PropertyReaderUtil.getMonitorDir());
        return dir.listFiles();
    }
}
