package tracker.src.tasks;

public class Task {

  public enum Status { NEW, IN_PROGRESS, DONE };
  
  private int id = -1;

  private final String name;
  private final String desc;
  private Status status;


  public Task(int id, String name, String desc, Status status) {
    this.name = name;
    this.desc = desc;
    this.status = status;
    this.id = id;
  }

  public Task(int id, String name, String desc) {
    this(id, name, desc, Status.NEW);
  }

  public int getId() { return id; }
  public String getName() { return name; }
  public String getDesc() { return desc; }
  public Status getStatus() { return status; }

  @Override 
  public String toString() {
    return String.format("Задача: %s, id: %d, описание: %s, статус: %s", getName(), getId(), getDesc(), getStatus());
  }
  
}
