package utils;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by O.Gondar on 15.11.2016.
 */
public class PropertyReaderUtil {

    static final Logger logger = Logger.getLogger(PropertyReaderUtil.class);

    private static String MONITOR_DIR;
    private static String PROCESSED_DIR;
    private static String INVALID_DIR;
    private static long TIME_PERIOD;

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(classLoader.getResource("config.properties").getFile()));
            MONITOR_DIR = properties.getProperty("monitor_folder");
            PROCESSED_DIR = properties.getProperty("processed_files_folder");
            INVALID_DIR = properties.getProperty("invalid_files_folder");
            TIME_PERIOD = Long.parseLong(properties.getProperty("time_period"));

        } catch (IOException e) {
            logger.error("Property file not found");
        }
    }

    public static String getMonitorDir() {
        return MONITOR_DIR;
    }

    public static String getProcessedDir() {
        return PROCESSED_DIR;
    }

    public static String getInvalidDir() {
        return INVALID_DIR;
    }

    public static long getTimePeriod() {
        return TIME_PERIOD;
    }
}
