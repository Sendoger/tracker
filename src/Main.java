package tracker.src;
import tracker.src.managers.Manager;
import tracker.src.managers.TaskManager;

public class Main {
  public static void main(String[] args) {

    TaskManager manager = Manager.getDefault();
    manager.addTask("676767", "swag");
    manager.addEpic("epic", "very");
    manager.addSubtask(1, "subtask", "sub");
    //manager.printAll();
    //с богом


    //manager.printAll();

  }
}
