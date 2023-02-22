import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AnnualTask extends Task {

    public AnnualTask(String title, TaskType type, Date date, Frequency frequency, String description) {
        super(title, type, date, frequency, description);
    }

    @Override
    public Boolean appearsIn(Date date) {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        Calendar calendarTask = new GregorianCalendar();
        calendarTask.setTime(super.getDate());

        return calendar.get(Calendar.DAY_OF_YEAR) == calendarTask.get(Calendar.DAY_OF_YEAR);

    }
}
