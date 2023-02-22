import com.sun.source.tree.NewArrayTree;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SocketHandler;

public class Main {
    public static Book book = new Book();
    public static void main(String[] args) {

        System.out.println("Введите команду (new, remove, list, edit, help):");

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String val = scanner.next();

            switch (val) {
                case "exit":
                    break;
                case "new":
                    addTask();
                    break;
                case "remove":
                    removeTaskByID();
                    break;
                case "list":
                    getListOfTasks();
                    break;
                case "list_date":
                    getListOfTasksOnDate();
                    break;
                case "list_arh":
                    getListOfArhTasks();
                    break;
                case "edit":
                    editTask();
                    break;
                case "count":
                    book.getWordsCount();
                    break;
                case "help":
                    showHelpList();
                    break;
                default:
                    System.out.println("Введена неизвестная команда.");
                    break;
            }
        }
    }

    public static void addTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Вводим новую задачу:");
        System.out.println("-------------------------------------------");

        String title = "";
        TaskType taskType = TaskType.Personal;

        System.out.println("Введите имя задачи:");
        if (scanner.hasNext()) {
            title = scanner.next();
        }

        System.out.println("Введите тип задачи (personal(личная), working(рабочая):");

        if (scanner.hasNext()) {
            String srtTaskType = scanner.next();
            if (srtTaskType.equals("personal")) {
                taskType = TaskType.Working.Personal;
            } else if (srtTaskType.equals("working")) {
                taskType = TaskType.Working.Working;
            }
        }

        Date taskDate = getDate(true);
        Frequency frequency = getFrequency();
        String description = getDescription();

        if (frequency == null) {
            System.out.println("Задача не созданна!");
            return;
        }

        Task task = null;

        switch (frequency) {
            case Single :
                task = new OneTimeTask(title, taskType, taskDate, frequency, description);
                break;
            case Daily :
                task = new DailyTask(title, taskType, taskDate, frequency, description);
                break;
            case Weekly :
                task = new WeeklyTask(title, taskType, taskDate, frequency, description);
                break;
            case Monthly :
                task = new MonthlyTask(title, taskType, taskDate, frequency, description);
                break;
            case Annual :
                task = new AnnualTask(title, taskType, taskDate, frequency, description);
                break;
            default :
                task = null;
                System.out.println("Задача не созданна!");
        };

        book.addTask(task);
    }

    public static void getListOfTasks() {
        HashMap<UUID, Task> list = book.getFullList();
        for (Task task: list.values()) {
            System.out.println(task);
        }
    }
    public static void getListOfArhTasks() {
        HashMap<UUID, Task> list = book.getArhList();
        for (Task task: list.values()) {
            System.out.println(task);
        }
    }

    public static void removeTaskByID() {
        UUID id;
        System.out.println("Введите ID:");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            try {
                id = UUID.fromString(scanner.next());
            } catch (IllegalArgumentException e) {
                System.out.println("Вы ввели некорректный id");
                return;
            }
            book.removeTask(id);
        }
    }

    public static void editTask() {
        System.out.println("Введите ID:");
        Scanner scanner = new Scanner(System.in);
        UUID id;
        if (scanner.hasNext()) {
            try {
                id = UUID.fromString(scanner.next());
            } catch (IllegalArgumentException e) {
                System.out.println("Вы ввели некорректный id");
                return;
            }

            Task task = book.getTask(id);

            if (task != null) {
                System.out.println("Выберете действие (set_desc (установить описание), set_date(установить время), " +
                        "set_freq(установить переодичность)");
                if (scanner.hasNext()) {
                    String action = scanner.next();
                    if (action.equals("set_desc")) {
                        String description = getDescription();
                        task.setDescription(description);
                    } else if (action.equals("set_date")) {
                        Date date = getDate(true);
                        task.setDate(date);
                    } else if (action.equals("set_freq")) {
                        Frequency frequency = getFrequency();
                        task.setFrequency(frequency);
                    }
                }
            }
        }
    }

    public static void showHelpList() {
        System.out.println("Список команд:");
        System.out.println("Новая задача: new");
        System.out.println("Список задач: list");
        System.out.println("Список задач на дату: list_date");
        System.out.println("Список архивных задач: list_arh");
        System.out.println("Редактировать задачу: edit");
        System.out.println("Посчитать слова в предложении: count");
        System.out.println("Помощь: help");
        System.out.println("Выход: exit");
    }

    public static Date getDate(Boolean withTime) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите дату задачи в формате(yyyy-MM-dd):");
        Date taskDate = null;
        String strDate = "";
        String strTime = "";

        if (scanner.hasNext()) {
            strDate = scanner.next();
        }

        if (withTime) {
            System.out.println("Введите время в формате(HH:mm:ss)");
            if (scanner.hasNext()) {
                strTime = scanner.next();
            }
        } else {
            strTime = "00:00:00";
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            taskDate = formatter.parse(strDate + " " + strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return taskDate;
    }

    public static Frequency getFrequency() {

        Scanner scanner = new Scanner(System.in);
        Frequency frequency = null;

        System.out.println("Введите переодичность (single, daily, weekly, monthly, annual):");
        if (scanner.hasNext()) {
            String strFrequency = scanner.next();

            switch (strFrequency) {
                case "single":
                    frequency = Frequency.Single;
                    break;
                case "daily":
                    frequency = Frequency.Daily;
                    break;
                case "weekly":
                    frequency = Frequency.Weekly;
                    break;
                case "monthly":
                    frequency = Frequency.Monthly;
                    break;
                case "annual":
                    frequency = Frequency.Annual;
                    break;
                default:
                    frequency = null;
                    System.out.println("Переодичность введена некорректно.");
            }
        }

        return  frequency;
    }

    public static String getDescription() {

        String description = "";

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите описание:");

        if (scanner.hasNextLine()) {
            description = scanner.nextLine();
        }
        return description;
    }

    public static void getListOfTasksOnDate() {
        Date dateOfTask = getDate(false);
        HashMap<UUID, Task> list = book.getListOfTaskOnDate(dateOfTask);
        for (Task task: list.values()) {
            System.out.println(task);
        }
    }
}