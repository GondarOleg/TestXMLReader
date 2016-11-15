import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Run {
    public static void main(String[] args) throws IOException, JAXBException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Properties prop = new Properties();
        prop.load(new FileInputStream(classLoader.getResource("config.properties").getFile()));
        String monitor_folder = prop.getProperty("monitor_folder");
        String processed_files_folder = prop.getProperty("processed_files_folder");
        String invalid_files_folder = prop.getProperty("invalid_files_folder");

        System.out.println(monitor_folder);
        HibernateUtil hibernateUtil = new HibernateUtil();
        File dir = new File(monitor_folder);
        File[] files = dir.listFiles();
        System.out.println(files.length);
        ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        if(files.length != 0){
            for (File file : files){
                exec.submit(new FileProcessTask(file, hibernateUtil.getSessionFactory()));

            }
        }




    }





}
