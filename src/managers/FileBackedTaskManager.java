package tracker.src.managers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import tracker.src.enums.*;
import tracker.src.exceptions.ManagerSaveException;
import tracker.src.tasks.*;

public class FileBackedTaskManager extends InMemoryTaskManager {

  public static void main(String[] args) {
    FileBackedTaskManager manager = new FileBackedTaskManager(Manager.getDefaultHistory());
    System.out.println("\n");
    manager.loadFromFile(file);
    //manager.addSubtask(9,"subtask2", "desc");
    //manager.addEpic("epic1", "desc");
    //manager.addSubtask(3, "subtask1", "desc");
    //manager.updateSubTask(6, Status.DONE);

    manager.printAll();
  }

  private final static String path = "/Users/sendogu/programming/java stuff/tracker/src/saved/filemanageroutput.csv";
  private final static File file = new File(path);


  public FileBackedTaskManager(HistoryManager historyImplementation) {
    super(historyImplementation);
  }

  public void loadFromFile(File file) {  
    try {
      List<String> loadedFile = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
      if (loadedFile.size() <= 1) return;
      for (String line : loadedFile) {
        if (line.isBlank()) break;
        
        
        String[] parsedLine = line.split(",");
        
        if (parsedLine[0].equals("id")) continue;

        int id = Integer.parseInt(parsedLine[0]);
        int epicId = Integer.parseInt(parsedLine[parsedLine.length-1]);
      
        String type = parsedLine[1];
        switch(type) { //type
          case "TASK" -> {
            getTasks().put(id, fromStringToTask(parsedLine)); 
          }
          case "SUBTASK" -> {
            Epic epic = getEpics().get(epicId);
            if (epic != null) {
              epic.getSubtasks().add(id);
            }
            getSubtasks().put(id, (Subtask) fromStringToTask(parsedLine));

          }
          case "EPIC" -> {
            getEpics().put(id, (Epic) fromStringToTask(parsedLine));
          }
        }
      }
      //id thing
      setNextId(Integer.parseInt(loadedFile.getLast()));

      String historyLine = "history is empty";
      
      for (int i = loadedFile.size()-1; i >= 0; i--) {
        if (loadedFile.get(i).startsWith("[")) {
           historyLine = loadedFile.get(i);
           break;
        }
      }

      //history thing
      if (!historyLine.equals("history is empty")) {
        
        List<Integer> history = fromStringToList(historyLine.trim());
        
        for (int taskId : history) {
          super.getTaskById(taskId);
        }
      }
      

    } catch (IOException e) {
      System.out.println("Ошибка при загрузке файла: " + e.getMessage());
    }
  }

  public void save() throws ManagerSaveException {

    if (file.getParentFile() != null && !file.getParentFile().exists()) {
        file.getParentFile().mkdirs();
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            
      writer.write("id,type,name,status,desc,epicId".trim());
      writer.newLine();

      for (Task task : getTasks().values()) {
        writer.write(toString(task).trim());
        writer.newLine();
      }

      for (Epic epic : getEpics().values()) {
        writer.write(toString(epic).trim());
        writer.newLine();
      }

      for (Subtask subtask : getSubtasks().values()) {
        writer.write(toString(subtask).trim());
        writer.newLine();
      }
            
      writer.newLine();
      writer.write(toString(getHistoryManager()).trim());
      writer.newLine();
      writer.newLine();
      writer.write(String.valueOf(getNextId()).trim());

    } catch (IOException e) {

      throw new ManagerSaveException("Не удалось сохранить файл: " + e.getMessage());
    }
  }

  @Override 
  public void addSubtask(int epicId, String name, String desc) {
    super.addSubtask(epicId, name, desc);
    try {
      save();
    
    } catch (ManagerSaveException e) {
      System.out.println("Ошибка при сохранении файла. " + e);
    }
  }

  @Override 
  public void updateSubTask(int subtaskId, Status status) {
    super.updateSubTask(subtaskId, status);
    try {
      save();
    
    } catch (ManagerSaveException e) {
      System.out.println("Ошибка при сохранении файла. " + e);
    }
  }
  @Override 
  public void updateTask(int taskId, Status status) {
    super.updateTask(taskId, status);
    try {
      save();
    
    } catch (ManagerSaveException e) {
      System.out.println("Ошибка при сохранении файла. " + e);
    }
  }

  @Override 
  public void updateEpic(int epicId) {
    super.updateEpic(epicId);
    try {
      save();
    
    } catch (ManagerSaveException e) {
      System.out.println("Ошибка при сохранении файла. " + e);
    }
  }

  @Override 
  public void addTask(String name, String desc) {
    super.addTask(name, desc);
    try {
      save();
    
    } catch (ManagerSaveException e) {
      System.out.println("Ошибка при сохранении файла. " + e);
    }
  }

  @Override 
  public void addEpic(String name, String desc) {
    super.addEpic(name, desc);
    try {
      save();
    
    } catch (ManagerSaveException e) {
      System.out.println("Ошибка при сохранении файла. " + e);
    }
  }

  @Override 
  public Task getTaskById(int id) {
    Task res = super.getTaskById(id);
    try {
      save();
    
    } catch (ManagerSaveException e) {
      System.out.println("Ошибка при сохранении файла. " + e);
    }
    return res;
  }

  

  public String toString(Task task) {
    
    return String.format("%d,%s,%s,%s,%s,-1",
                          task.getId(),
                          task.getType(),
                          task.getName(),
                          task.getStatus(),
                          task.getDesc()
                        );
                          
  }
  public String toString(Subtask subtask) {
    return String.format("%d,%s,%s,%s,%s,%s",
                          subtask.getId(),
                          subtask.getType(),
                          subtask.getName(),
                          subtask.getStatus(),
                          subtask.getDesc(),
                          subtask.getParentEpicId()
                        );
                          
  }

  public Task fromStringToTask(String[] parsedString) {
    int id;
    String name;
    String desc;
    Status status;
    String type;
    int epicId;

    Task task;

    id = Integer.parseInt(parsedString[0]);
    type = parsedString[1];
    name = parsedString[2];
    status = Status.valueOf(parsedString[3]);
    desc = parsedString[4];
    epicId = Integer.parseInt(parsedString[5]);

    switch (type) {
        case "SUBTASK" -> {
          task = new Subtask(id, epicId, name, desc, status);
        }
        case "TASK" -> {
          task = new Task(id, name, desc, status);
        }
        case "EPIC" -> {
          task = new Epic(id, name, desc, status);
        }
        default -> throw new AssertionError();
    }

    return task;
  }

  static String toString(HistoryManager manager) {
    String res = "[";
    List<Task> history = manager.getHistory();
    for (Task task : history) {
      res += String.valueOf(task.getId());
      if (task != history.get(history.size()-1)) res += ",";
    }
    
    res += "]";
    if (res.equals("[]")) return "history is empty";
    return res;
  }

  static List<Integer> fromStringToList(String value) {
    String[] parsedString = value.replace("[", "").replace("]","").strip().split(",");
    
    List<Integer> res = new ArrayList<>();
    if (!parsedString[0].isBlank()) {
      for (String num : parsedString) {
        res.add(Integer.parseInt(num));
      }
    }
    return res;
  }

  
}
