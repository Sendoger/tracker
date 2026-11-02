package tracker.src.managers;

import java.util.ArrayList;

import tracker.src.tasks.Task;

public interface HistoryManager {
  public void add(Task task);
  public void remove(int id);
  public ArrayList<Task> getHistory();
}