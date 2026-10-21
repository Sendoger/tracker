package tracker.src.tasks;

import java.util.*;
import tracker.src.tasks.Task.Status;

public interface Manager {

  void addTask(Task task);
  void addEpic(Epic epic);
  void addSubtask(int epicId, Subtask subtask);
  void updateEpic(int epicId);
  void updateTask(Task task, Status status);
  void updateTask(Subtask subtask, Status status);
  HashMap<Integer, Task> getTasks();
  HashMap<Integer, Epic> getEpics();
  HashMap<Integer, Subtask> getSubtasks();
  void printAll();
  void clearTasks(int option);
  Task getTaskById(int id);
  void removeTaskById(int id);
  ArrayList<Subtask> getEpicSubtasks(int epicId);
}