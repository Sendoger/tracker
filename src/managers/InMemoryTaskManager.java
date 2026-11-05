package tracker.src.managers;

import java.util.*;
import tracker.src.tasks.Epic;
import tracker.src.tasks.Subtask;
import tracker.src.tasks.Task;
import tracker.src.tasks.Task.Status;

public class InMemoryTaskManager implements TaskManager{

  private static int nextId = 0;
  private final HashMap<Integer, Task> tasks = new HashMap<>();
  private final HashMap<Integer, Epic> epics = new HashMap<>();
  private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
  private final HistoryManager history;

  public InMemoryTaskManager(HistoryManager historyImplementation) {
    this.history = historyImplementation;
  }

  @Override
  public List<Task> historyList() {
    return history.getHistory();
  
  }

  @Override
  public void addTask(String name, String desc) {
    Task task = new Task(nextId++, name,desc);
    
    if (!tasks.containsValue(task)) {
      tasks.put(task.getId(), task); 
    } else {
      throw new IllegalArgumentException("Задача уже присутствует");
    }
  }

  @Override
  public void addEpic(String name, String desc) {
    Epic epic = new Epic(nextId++, name, desc);
    if (!epics.containsValue(epic)) {
      epics.put(epic.getId(), epic); 
    } else {
      throw new IllegalArgumentException("Эпик уже присутствует");
    }
  }

  @Override
  public void addSubtask(int epicId, String name, String desc) {
    Subtask subtask = new Subtask(nextId++, epicId, name, desc);
    Epic epic = epics.get(epicId);
    if (!subtasks.containsValue(subtask)) {

      if (epic == null) {
        throw new IllegalArgumentException("Эпик с id: " + epicId + " не обнаружен");
      } else {
        epic.getSubtasks().add(subtask.getId());
        System.out.println("wow");
        System.out.println(epic.getSubtasks());
        subtasks.put(subtask.getId(), subtask);
        updateEpic(epicId);
      }
    } else {
      throw new IllegalArgumentException("Подзадача уже присутствует");
    }
  }

  @Override
  public void updateEpic(int epicId) {
    Epic epic = epics.get(epicId);
    int done_counter = 0;
    int new_counter = 0;
    Status status;
    for (Subtask subtask : getEpicSubtasks(epicId)) {
      
      if (subtask.getStatus() == Status.DONE) {
        done_counter++;
      }
      else if (subtask.getStatus() == Status.NEW) {
        new_counter++;
      }
    }
    if (done_counter == epic.getSubtasks().size()) {
      status = Status.DONE;
      
    }
    else if (new_counter == epic.getSubtasks().size() || (epic.getSubtasks().isEmpty())) {
      status = Status.NEW;

    }
    else {
      status = Status.IN_PROGRESS;
    }
    Epic newEpic = new Epic(epic.getId(), epic.getName(), epic.getDesc(), status);
    for (int subtaskId : epic.getSubtasks()) {
      newEpic.getSubtasks().add(subtaskId);
    }
    
    epics.put(epic.getId(), newEpic);
  }

  @Override
  public void updateTask(int taskId, Status status) {
    Task task = tasks.get(taskId);
    Task newtask = new Task(task.getId(), task.getName(), task.getDesc(), status);
    
    if (tasks.containsKey(task.getId())) tasks.put(task.getId(), newtask);
    else throw new IllegalArgumentException("Задача не найдена");
  }

  @Override
  public void updateSubTask(int subtaskId, Status status) {
    Subtask subtask = subtasks.get(subtaskId);
    Subtask newSubtask = new Subtask(subtask.getId(), subtask.getParentEpicId(), subtask.getName(), subtask.getDesc(), status);
    
    if (subtasks.containsKey(subtask.getId())) {
      subtasks.put(subtask.getId(), newSubtask);
      updateEpic(subtask.getParentEpicId());
    }
    else throw new IllegalArgumentException("Задача не найдена");
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
    Task res = null;
    if (tasks.containsKey(id)) { res = tasks.get(id); }
    if (epics.containsKey(id)) { res = epics.get(id); }
    if (subtasks.containsKey(id)) { res = subtasks.get(id); }
    if (res != null) { history.add(res); }
    return res;

  }
  @Override
  public void removeTaskById(int id) {
    Boolean exists = false;
    if (tasks.containsKey(id)) tasks.remove(id);
    else if (epics.containsKey(id)) {
      exists = true;
      for (int subtaskId : epics.get(id).getSubtasks()) {
        subtasks.remove(subtaskId);
        history.remove(subtaskId);
      }
      epics.remove(id);
    }
    else if (subtasks.containsKey(id)) {
      Epic subtaskEpic = epics.get(subtasks.get(id).getParentEpicId());
      for (int i = 0; i < subtaskEpic.getSubtasks().size(); i++) {
        if(i == id) subtaskEpic.getSubtasks().remove(i);
      }
      
      subtasks.remove(id);
      exists = true;
    } 
    else {
      throw new IllegalArgumentException("Задача не найдена");
    }

    if (exists) { history.remove(id); }
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