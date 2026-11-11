package tracker.src.tasks;
import tracker.src.enums.*;

public class Subtask extends Task {
  private int parentEpicId = -1;
  
  public Subtask(int id, int epicId, String name, String desc) {
    super(id, name, desc, Status.NEW);
    this.type = Type.SUBTASK;
    this.parentEpicId = epicId;
  }
  public Subtask(int id, int epicId, String name, String desc, Status status) {
    super(id, name, desc, status);
    this.parentEpicId = epicId;
    this.type = Type.SUBTASK;
  }

  
  public int getParentEpicId() { return this.parentEpicId; }

  @Override 
  public String toString() {
    return String.format("Подзадача: %s, id: %d, id эпика: %d, описание: %s, статус: %s", getName(), getId(),getParentEpicId(), getDesc(), getStatus());
  }


}
