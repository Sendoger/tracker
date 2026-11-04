package tracker.src.managers;

import java.util.List;
import tracker.src.tasks.Task;

public interface HistoryManager {
  public void add(Task task);
  public void remove(int id);
  public List<Task> getHistory();
}