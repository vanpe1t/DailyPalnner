import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Book {

    private HashMap<UUID, Task> book = new HashMap<>();
    private HashMap<UUID, Task> arhBook = new HashMap<>();
    public void getFullList() {
        for (Task task: book.values()) {
            System.out.println(task);
        }
    }

    public void addTask(Task task) {
        book.put(task.getId(), task);
    }

    public void removeTask(UUID id) {
        Task task = book.get(id);
        arhBook.put(task.getId(), task);
        book.remove(id);
    }

    public Task getTask(UUID id) {
        return book.get(id);
    }

    public void getArhList() {
        for (Task task: arhBook.values()) {
            System.out.println(task);
        }
    }

    public void getListOfTaskOnDate(Date date) {
        for (Task task: book.values()) {
            if (task.appearsIn(date)) {
                System.out.println(task);
            }
        }
    }

    public void getWordsCount() {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            Arrays.stream(str.split("\\s+"))
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(10)
                    .forEach(e -> System.out.println(e.getKey() + " " + e.getValue()));

        }
    }
}
