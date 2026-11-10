package tracker.src.managers;

import java.util.*;
import tracker.src.enums.*;
import tracker.src.tasks.Epic;
import tracker.src.tasks.Subtask;
import tracker.src.tasks.Task;

public interface TaskManager {
  void addTask(String name, String desc);
  void addEpic(String name, String desc);
  void addSubtask(int epicId, String name, String desc);
  void updateEpic(int epicId);
  void updateTask(int taskId, Status status);
  void updateSubTask(int subtaskId, Status status);
  int getNextId();
  List<Task> historyList();
  HashMap<Integer, Task> getTasks();
  HashMap<Integer, Epic> getEpics();
  HashMap<Integer, Subtask> getSubtasks();
  void printAll();
  void clearTasks(int option);
  Task getTaskById(int id);
  void removeTaskById(int id);
  ArrayList<Subtask> getEpicSubtasks(int epicId);
}