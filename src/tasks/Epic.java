package tracker.src.tasks;

import java.util.ArrayList;

public class Epic extends Task {
  ArrayList<Integer> subtasks = new ArrayList<>();
  public Epic(int id, String name, String desc) {
    super(id, name, desc);
  }
  public Epic(int id, String name, String desc, Status status) {
    super(id, name, desc, status);
  }

  public ArrayList<Integer> getSubtasks() { return subtasks; }

  @Override 
  public String toString() {
    return String.format("Эпик: %s, id: %d, описание: %s, статус: %s", getName(), getId(), getDesc(), getStatus());
  }
}
