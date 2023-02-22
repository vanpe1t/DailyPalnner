import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Book {

    private HashMap<UUID, Task> book = new HashMap<>();
    private HashMap<UUID, Task> arhBook = new HashMap<>();
    public HashMap<UUID, Task> getFullList() {
        return book;
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

    public HashMap<UUID, Task> getArhList() {
        return arhBook;
    }

    public HashMap<UUID, Task> getListOfTaskOnDate(Date date) {
        HashMap<UUID, Task> listOfTaskOnDate = new HashMap<>();
        for (Task task: book.values()) {
            if (task.appearsIn(date)) {
                listOfTaskOnDate.put(task.getId(), task);
            }
        }
        return listOfTaskOnDate;
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
