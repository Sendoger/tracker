package tracker.src.tasks;

public class Task {
  
  private static long nextId = 0L;
  private long id = 0;

  private String name = "";
  private String desc = "";
  private String status;

  public Task(String name, String desc) {
    id = nextId++;
    this.name = name;
    this.desc = desc;
    this.status = "NEW";
  }

  public long getId() { return id; }
  public String getName() { return name; }
  public String getDesc() { return desc; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }

  @Override 
  public String toString() {
    return String.format("Задача: %s, id: %d, описание: %s", getName(), getId(), getDesc());
  }
  
}
