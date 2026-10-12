package tracker.src.tasks;

import java.util.*;
import tracker.src.tasks.Task.Status;

public class Manager {

  private HashMap<Integer, Task> tasks = new HashMap<>();

  public void addTask(Task task) { tasks.put(task.getId(), task); }

  //public void addTask(Epic epic) { tasks.put(epic.getId(), epic); }

  private void updateEpic(Epic epic) {
    int done_counter = 0;
    int new_counter = 0;
    for (Subtask subtask : getEpicSubtasks(epic)) {
      if (subtask.getStatus() == Status.DONE) {
        done_counter++;
      }
      else if (subtask.getStatus() == Status.NEW) {
        new_counter++;
      }
    }
    if (done_counter == epic.getSubtasks().size()) {
      epic.setStatus(Status.DONE);
      tasks.put(epic.getId(), epic);
    }
    else if (new_counter == epic.getSubtasks().size() || (epic.getSubtasks().isEmpty())) {
      epic.setStatus(Status.NEW);
      tasks.put(epic.getId(), epic);
    }
    else {
      epic.setStatus(Status.IN_PROGRESS);
      tasks.put(epic.getId(), epic);
    }
  }

  public void updateTask(int taskId, Status status) {
    Task task = tasks.get(taskId);
    task.setStatus(status);
    tasks.put(taskId, task);
  }

  public void addSubtask(int epicId, Subtask subtask) { 
    Epic epic = (Epic)tasks.get(epicId);
    if (epic == null) {
        throw new IllegalArgumentException("Эпик с id: " + epicId + " не обнаружен");
    } else {
      epic.getSubtasks().add(subtask.getId());
      updateEpic(epic);
      
    } 
  }
  //public void addEpic (Epic epic) { epics.put(epic.getId(), epic); }

  public HashMap<Integer, Task> getTasks() { return tasks; }
  
  public void clearTasks() {
    Scanner reader = new Scanner(System.in);
    System.out.println("Вы уверены, что хотите очистить задачи? (Y/n)");
    String answer = reader.next();
    reader.close();
    
    if (answer.equals("Y") || answer.equals("y")) {
      tasks.clear();
      System.out.println("Список задач очищен");
    } else {
      System.out.println("Отмена");
    }
  }

  public Task getTaskById(Integer id) { return tasks.get(id); }
  public ArrayList<Subtask> getEpicSubtasks(Epic epic) { 
    ArrayList<Subtask> subtasks = new ArrayList<>();
    for (Integer subtaskId : epic.getSubtasks()) {
      subtasks.add((Subtask)tasks.get(subtaskId));
    }
    return subtasks;
  }

  
}
