import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DailyTask extends Task {

    public DailyTask(String title, TaskType type, Date date, Frequency frequency, String description) {
        super(title, type, date, frequency, description);
    }


}
