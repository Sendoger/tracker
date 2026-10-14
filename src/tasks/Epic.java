package tracker.src.tasks;

import java.util.ArrayList;

public class Epic extends Task {
  ArrayList<Integer> subtasks = new ArrayList<>();
  public Epic(String name, String desc) {
    super(name, desc);
  }

  public ArrayList<Integer> getSubtasks() { return subtasks; }

  @Override 
  public String toString() {
    return String.format("Эпик: %s, id: %d, описание: %s, статус: %s", getName(), getId(), getDesc(), getStatus());
  }
}
