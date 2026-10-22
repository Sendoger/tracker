package tracker.src;
import tracker.src.tasks.*;

public class Main {
  public static void main(String[] args) {
    Task task1 = new Task("Здоровье" , "че делать");
    Subtask subtask1 = new Subtask("отжимання", "вниз вверх");
    Subtask subtask2 = new Subtask("помогите", "какой ужас");
    Subtask subtask3 = new Subtask("пип", "яу");
    Task task2 = new Task("Успех", "надо добиться");

    Epic epic1 = new Epic("вроде работает", "с божьей помощью");
    Epic epic2 = new Epic("второй эпик", "text");
    

    Manager manager = new Manager();
    System.out.println("\n\n\n\n\n");
    manager.addTask(task1);
    manager.addTask(task2);
    manager.addEpic(epic1);
    manager.addEpic(epic2);
    manager.addSubtask(epic1.getId(), subtask1);
    manager.addSubtask(epic1.getId(), subtask2);
    manager.addSubtask(epic2.getId(), subtask3);
    manager.printAll();
    manager.updateTask(subtask2, Task.Status.DONE);
    manager.updateTask(subtask1, Task.Status.IN_PROGRESS);
    manager.updateTask(subtask3, Task.Status.DONE);
    System.err.println(manager.getEpicSubtasks(epic2.getId()));
    System.err.println("\n");
    manager.updateTask(task2, Task.Status.IN_PROGRESS);
    System.out.println(manager.getEpics());
    manager.removeTaskById(task1.getId());
    manager.removeTaskById(epic1.getId());
    System.out.println("\n");
    manager.printAll();
    System.out.println("\n");
    manager.clearTasks(0);
    System.err.println("\n");
    manager.printAll();

  }
}
