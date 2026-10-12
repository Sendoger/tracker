package tracker.src;
import tracker.src.tasks.*;

public class Main {
  public static void main(String[] args) {
    Task task = new Task("Здоровье" , "че делать", Task.Status.NEW);
    Subtask subtask = new Subtask("отжимання", "вниз вверх", Task.Status.NEW);
    Subtask finishedSubtask = new Subtask("помогите", "какой ужас", Task.Status.DONE);
    Task finishedTask = new Task("Успех", "надо добиться", Task.Status.NEW);
    //finishedTask.setStatus("DONE");

    Epic epic = new Epic("вроде работает", "с божьей помощью");
    System.out.println(task.getStatus());
    System.out.println(subtask.getStatus());
    //epic.setStatus("IN_PROGRESS");
    System.out.println(epic.getStatus());
    System.out.println(epic.getId());

    Manager manager = new Manager();
    manager.addTask(task);
    manager.addTask(subtask);
    manager.addTask(finishedSubtask);
    manager.addTask(finishedTask);
    manager.addTask(epic);
    System.out.println("Статус эпика до добавления subtask с статусом " + subtask.getStatus() + ": " + epic.getStatus());
    manager.addSubtask(epic.getId(), subtask);
    System.out.println("Статус эпика после добавления subtask: " + epic.getStatus());
    System.out.println("Статус эпика до добавления subtask с статусом " + finishedSubtask.getStatus() + ": " + epic.getStatus());
    manager.addSubtask(epic.getId(), finishedSubtask);
    System.out.println("Статус эпика после добавления subtask: " + epic.getStatus());
    manager.clearTasks();

  }
}
