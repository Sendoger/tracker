package tracker.src;
import tracker.src.managers.Manager;
import tracker.src.managers.TaskManager;

public class Main {
  public static void main(String[] args) {

    TaskManager manager = Manager.getDefault();
    System.out.println("\n\n\n\n\n");
    manager.addTask("Здоровье" , "че делать");
    manager.addTask("Успех", "надо добиться");
    manager.addEpic("Великая задача 1", "С двумя подзадачами");
    manager.addSubtask(2, "Подзадача 1", "бип");
    manager.addSubtask(2, "Подзадача 2", "буп");
    manager.addEpic("Великая задача 2", "Без подзадач");
    manager.getTaskById(0);
    System.out.println("\n"+manager.historyList());
    System.out.println("\n"+manager.historyList());
    System.out.println("\n"+manager.historyList());
    manager.getTaskById(0);
    System.out.println("\n"+manager.historyList());
    manager.getTaskById(4);
    System.out.println("\n"+manager.historyList());
    manager.getTaskById(3);
    System.out.println("\n"+manager.historyList());
    manager.getTaskById(5);
    System.out.println("\n"+manager.historyList());
    manager.removeTaskById(3);
    System.out.println("\n"+manager.historyList());
    manager.removeTaskById(2);
    System.out.println("\n"+manager.historyList());
    //manager.printAll();


    //manager.printAll();

  }
}
