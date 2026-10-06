package tracker.src;
import tracker.src.tasks.*;

public class Main {
  public static void main(String[] args) {
    Task task = new Task("Здоровье" , "че делать");
    Subtask subtask = new Subtask("отжимання", "вниз вверх");
    Epic epic = new Epic(null, null);

    System.out.println(task.getStatus());
    System.out.println(subtask.getStatus());
    epic.setStatus("IN_PROGRESS");
    System.out.println(epic.getStatus());
    System.out.println(epic.getId());
  }
}
