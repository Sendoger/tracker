package tracker.src.tasks;

import java.util.*;
import java.util.concurrent.StructuredTaskScope.Subtask;

import tracker.src.tasks.Task.Status;

public class InMemoryTaskManager implements Manager{

  private final HashMap<Integer, Task> tasks = new HashMap<>();
  private final HashMap<Integer, Epic> epics = new HashMap<>();
  private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

  @Override
  public void addTask(Task task) {
    if (!tasks.containsKey(task.getId())) {
      tasks.put(task.getId(), task); 
    } else {
      System.out.println("Задача уже присутствует");
    }
  }

  @Override
  public void addEpic(Epic epic) {
    if (!epics.containsKey(epic.getId())) {
      epics.put(epic.getId(), epic); 
    } else {
      System.out.println("Эпик уже присутствует");
    }
  }

  @Override
  public void addSubtask(int epicId, Subtask subtask) {
    if (!subtasks.containsKey(subtask.getId())) {
      subtask.setParentEpicId(epicId);

      if (epics.get(epicId) == null) {
        throw new IllegalArgumentException("Эпик с id: " + epicId + " не обнаружен");
      } else {
        epics.get(epicId).getSubtasks().add(subtask.getId());
        subtasks.put(subtask.getId(), subtask);
      }
    } else {
      System.out.println("Подзадача уже присутствует");
    }
  }

  @Override
  public void updateEpic(int epicId) {
    Epic epic = epics.get(epicId);
    int done_counter = 0;
    int new_counter = 0;
    for (Subtask subtask : getEpicSubtasks(epicId)) {
      if (subtask.getStatus() == Status.DONE) {
        done_counter++;
      }
      else if (subtask.getStatus() == Status.NEW) {
        new_counter++;
      }
    }
    if (done_counter == epic.getSubtasks().size()) {
      epic.setStatus(Status.DONE);
      
    }
    else if (new_counter == epic.getSubtasks().size() || (epic.getSubtasks().isEmpty())) {
      epic.setStatus(Status.NEW);

    }
    else {
      epic.setStatus(Status.IN_PROGRESS);
    }
  }

  @Override
  public void updateTask(Task task, Status status) {
    task.setStatus(status);
    if (tasks.containsKey(task.getId())) tasks.put(task.getId(), task);
    else System.out.println("Задача не найдена");
  }

  @Override
  public void updateTask(Subtask subtask, Status status) {
    subtask.setStatus(status);
    if (subtasks.containsKey(subtask.getId())) {
      subtasks.put(subtask.getId(), subtask);
      updateEpic(subtask.getParentEpicId());
    }
    else System.out.println("Задача не найдена");
  }

  @Override
  public HashMap<Integer, Task> getTasks() { return tasks; }
  @Override
  public HashMap<Integer, Epic> getEpics() { return epics; }
  @Override
  public HashMap<Integer, Subtask> getSubtasks() { return subtasks; }
  @Override
  public void printAll() {
    System.out.println(getTasks());
    System.out.println(getEpics());
    System.out.println(getSubtasks());
  }
  @Override
  public void clearTasks(int option) {
    Scanner reader = new Scanner(System.in);
    System.out.println("Вы уверены, что хотите очистить задачи? (Y/n)");
    String answer = reader.next();

    if (answer.equals("Y") || answer.equals("y")) {
      switch (option) {
        case 0:
          tasks.clear();
          epics.clear();
          subtasks.clear();
          System.out.println("Список всех задач очищен");
          break;
        case 1:
          tasks.clear();
          System.out.println("Список задач очищен");
          break;
        case 2:
          epics.clear();
          System.out.println("Список эпиков очищен");
        case 3:
          subtasks.clear();
          if (!epics.isEmpty()) {
            for (Epic epic : epics.values()) {
              epic.getSubtasks().clear();
            }
          }
      }
      
      tasks.clear();
      epics.clear();
      subtasks.clear();
      System.out.println("Список задач очищен");
    } else {
      System.out.println("Отмена");
    }
  }
  @Override
  public Task getTaskById(int id) {
    if (tasks.containsKey(id)) return tasks.get(id);
    if (epics.containsKey(id)) return epics.get(id);
    return subtasks.get(id);
  }
  @Override
  public void removeTaskById(int id) {
    if (tasks.containsKey(id)) tasks.remove(id);
    else if (epics.containsKey(id)) {
      for (int subtaskId : epics.get(id).getSubtasks()) {
        subtasks.remove(subtaskId);
      }
      epics.remove(id);
    }
    else if (subtasks.containsKey(id)) subtasks.remove(id);
    else {
      System.out.println("Задача не найдена");
    }
  }
  @Override
  public ArrayList<Subtask> getEpicSubtasks(int epicId) {
    Epic epic = epics.get(epicId);
    ArrayList<Subtask> subtaskList = new ArrayList<>();
    for (Integer subtaskId : epic.getSubtasks()) {
      subtaskList.add(subtasks.get(subtaskId));
    }
    return subtaskList;
  }

}