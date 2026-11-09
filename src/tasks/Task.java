package tracker.src.tasks;
import tracker.src.enums.Status;
import tracker.src.enums.Type;

public class Task {
  
  private int id = -1;

  private final String name;
  private final String desc;
  private Status status;
  private Type type;

  public Task(int id, String name, String desc, Status status, Type type) {
    this.name = name;
    this.desc = desc;
    this.status = status;
    this.id = id;
    this.type = type;
  }

  public Task(int id, String name, String desc, Status status) {
    this.name = name;
    this.desc = desc;
    this.status = status;
    this.id = id;
  }

  public Task(int id, String name, String desc) {
    this(id, name, desc, Status.NEW, Type.TASK);
  }

  public int getId() { return id; }
  public String getName() { return name; }
  public String getDesc() { return desc; }
  public Status getStatus() { return status; }
  public String getType() { return type.toString(); }

  @Override 
  public String toString() {
    return String.format("Задача: %s, id: %d, описание: %s, статус: %s", getName(), getId(), getDesc(), getStatus());
  }
  
}
