package tracker.src.tasks;

public class Subtask extends Task {
  private int parentEpicId = -1;
  
  public Subtask(int id, int epicId, String name, String desc) {
    super(id, name, desc);
    this.parentEpicId = epicId;
  }
  public Subtask(int id, int epicId, String name, String desc, Status status) {
    super(id, name, desc, status);
    this.parentEpicId = epicId;
  }
  
  public int getParentEpicId() { return this.parentEpicId; }

  @Override 
  public String toString() {
    return String.format("Подзадача: %s, id: %d, id эпика: %d, описание: %s, статус: %s", getName(), getId(),getParentEpicId(), getDesc(), getStatus());
  }

}
