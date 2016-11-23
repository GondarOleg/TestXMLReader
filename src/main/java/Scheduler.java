import tasks.ScheduledTask;
import utils.HibernateUtil;
import utils.PropertyReaderUtil;

import java.util.Timer;

/**
 * Created by O.Gondar on 18.11.2016.
 */
public class Scheduler {
    public static void main(String[] args) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        Timer timer = new Timer();
        ScheduledTask scheduledTask = new ScheduledTask(hibernateUtil);
        timer.schedule(scheduledTask, 0, PropertyReaderUtil.getTimePeriod());
    }
}
