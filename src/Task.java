import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Task {

    private String title;

    private String description;
    private UUID id;

    private TaskType type;

    private Date date;

    private Frequency frequency;


    public Task(String title, TaskType type, Date date, Frequency frequency, String description) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.type = type;
        this.date = date;
        this.frequency = frequency;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public UUID getId() {
        return id;
    }

    public TaskType getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", type=" + type +
                ", date=" + date +
                ", frequency=" + frequency +
                '}';
    }
}
