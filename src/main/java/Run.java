import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Run {
    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Properties prop = new Properties();
        prop.load(new FileInputStream(classLoader.getResource("config.properties").getFile()));
        String monitor_folder = prop.getProperty("monitor_folder");
        String processed_files_folder = prop.getProperty("processed_files_folder");
        String invalid_files_folder = prop.getProperty("invalid_files_folder");




    }





}
