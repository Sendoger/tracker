package tracker.src;
import tracker.src.tasks.*;

public class Main {
  public static void main(String[] args) {

    TaskManager manager = Manager.getDefault();
    System.out.println("\n\n\n\n\n");
    manager.addTask("Здоровье" , "че делать");
    manager.addTask("Успех", "надо добиться");
    System.out.println(manager.getEpics());
    manager.getTaskById(0);
    System.out.println(manager.historyList());
    //manager.printAll();


    //manager.printAll();

  }
}
