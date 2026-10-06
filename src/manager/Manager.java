package tracker.src.manager;

import java.util.*;
import tracker.src.tasks.*;

public class Manager {

  private HashMap<Long, Task> tasksMap = new HashMap<>();
  private HashMap<Long, Epic> epics = new HashMap<>();
  private HashMap<Epic, Subtask> subtasks = new HashMap<>();

  public void addTask(Task task) { tasksMap.put(task.getId(), task); }
  
  /**
     * 
     *
     *Print out a task list by status number:
     *
     *0 : (default), prints all tasks
     *
     *1 : prints all new tasks
     *
     *2 : prints all ongoing tasks
     *
     *3 : prints all finished tasks
     *
     * @param status int status number to use
  */
  public void printTasksByStatus(int status) {
    
    int counter = 0;
    String filter = "";
    String message;
    switch (status) {
      case 1 -> {
        message = "Список новых задач: ";
        filter = "NEW";
      }
      case 2 -> {
        message = "Список начатых задач: ";
        filter = "IN_PROGRESS";
      }
      case 3 -> {
        message = "Список завершенных задач: ";
        filter = "DONE";
      }
      default -> {
        message = "Список всех задач: ";
        
      }
      
        
    }
    System.out.println(message);
    for (Task task : tasksMap.values()) {
      if (task.getStatus().equals(filter) || filter.equals("")) {
        System.out.println(task.toString());
        counter++;
      }
    }
  }

  public HashMap<Long, Task> getTaskMap() { return tasksMap; }
  
  public void clearTasks() {
    Scanner reader = new Scanner(System.in);
    System.out.println("Вы уверены, что хотите очистить задачи? (Y/n)");
    String answer = reader.next();
    reader.close();
    
    if (answer.equals("Y") || answer.equals("y")) {
      tasksMap.clear();
      System.out.println("Список задач очищен");
    } else {
      System.out.println("Отмена");
    }
  }

  public Task getTaskById(Long id) { return tasksMap.get(id); }
  
}
