package tracker.src.tasks;

import java.util.ArrayList;

public class Epic extends Task {
  ArrayList<Integer> subtasks = new ArrayList<>();
  public Epic(String name, String desc) {
    super(name, desc, Task.Status.NEW);
  }

  public ArrayList<Integer> getSubtasks() { return subtasks; }
}
