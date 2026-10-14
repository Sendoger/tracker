package tracker.src.tasks;

public class Task {

  public enum Status { NEW, IN_PROGRESS, DONE };
  
  private static int nextId = 0;
  private final int id;

  private final String name;
  private final String desc;
  private Status status;

  public Task(String name, String desc) {
    id = nextId++;
    this.name = name;
    this.desc = desc;
    this.status = Status.NEW;
  }

  public int getId() { return id; }
  public String getName() { return name; }
  public String getDesc() { return desc; }
  public Status getStatus() { return status; }
  protected void setStatus(Status status) { this.status = status; }

  @Override 
  public String toString() {
    return String.format("Задача: %s, id: %d, описание: %s, статус: %s", getName(), getId(), getDesc(), getStatus());
  }
  
}
