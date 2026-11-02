package tracker.src.tasks;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
  private final ArrayList<Task> history = new ArrayList<>();

  @Override 
  public ArrayList<Task> getHistory() { return history; }
  
  @Override 
  public void add(Task task) {
    if (task != null) {
      if (history.contains(task)) { remove(task.getId()); }
      history.add(task);
      
    } 
  }

  @Override 
  public void remove(int id) {
    for (Task task : history) {
      if (task.getId() == id) { history.remove(task); }
    }
  }
}