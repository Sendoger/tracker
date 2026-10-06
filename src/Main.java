package tracker.src;
import tracker.src.manager.*;
import tracker.src.tasks.*;

public class Main {
  public static void main(String[] args) {
    Task task = new Task("Здоровье" , "че делать");
    Subtask subtask = new Subtask("отжимання", "вниз вверх");
    Task finishedTask = new Task("Успех", "надо добиться");
    finishedTask.setStatus("DONE");
    Epic epic = new Epic(null, null);

    System.out.println(task.getStatus());
    System.out.println(subtask.getStatus());
    epic.setStatus("IN_PROGRESS");
    System.out.println(epic.getStatus());
    System.out.println(epic.getId());

    Manager manager = new Manager();
    manager.addTask(task);
    manager.addTask(subtask);
    manager.addTask(finishedTask);
    manager.addTask(epic);
    manager.clearTasks();

    manager.printTasksByStatus(0);
  }
}
