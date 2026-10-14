package tracker.src.tasks;

public class Subtask extends Task {
  private int parentEpicId = -1;
  public Subtask(String name, String desc) {
    super(name, desc);
  }
  protected void setParentEpicId(int id) { parentEpicId = id; }
  public int getParentEpicId() { return this.parentEpicId; }

  @Override 
  public String toString() {
    return String.format("Подзадача: %s, id: %d, id эпика: %d, описание: %s, статус: %s", getName(), getId(),getParentEpicId(), getDesc(), getStatus());
  }

}
