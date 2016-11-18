package tasks;

import org.apache.log4j.Logger;
import utils.HibernateUtil;
import utils.PropertyReaderUtil;

import java.io.File;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScheduledTask extends TimerTask{
    final static Logger logger = Logger.getLogger(ScheduledTask.class);

    @Override
    public void run() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        File dir = new File(PropertyReaderUtil.getMonitorDir());
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
