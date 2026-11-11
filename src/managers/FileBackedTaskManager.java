package tracker.src.managers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import tracker.src.enums.*;
import tracker.src.exceptions.ManagerSaveException;
import tracker.src.tasks.*;

public class FileBackedTaskManager extends InMemoryTaskManager {

  public static void main(String[] args) {
    FileBackedTaskManager manager = new FileBackedTaskManager(Manager.getDefaultHistory());
    manager.addTask("676767", "swag");
    manager.addEpic("epic", "very");
    manager.addSubtask(1, "subtask", "sub");
    manager.loadFromFile(file);
    manager.printAll();
  }

  private final static String path = "/Users/sendogu/programming/java stuff/tracker/src/saved/filemanageroutput.txt";
  private final static File file = new File(path);


  public FileBackedTaskManager(HistoryManager historyImplementation) {
    super(historyImplementation);
  }

  public void loadFromFile(File file) {
    try {
      List<String> loadedFile = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8); 
      for (String line : loadedFile) {
        if (line.isBlank()) break;
        
        
        String[] parsedLine = line.split(",");

        if (parsedLine[0].equals("id")) continue;

        int id = Integer.parseInt(parsedLine[0]);
      
        String type = parsedLine[1];
        switch(type) { //type
          case "TASK" -> {
            getTasks().put(id, fromString(parsedLine));  
          }
          case "SUBTASK" -> {
            Subtask subtask = getSubtasks().get(id);
            Epic epic = getEpics().get(subtask.getId());
            if (epic != null) {
              epic.getSubtasks().add(subtask.getId());
            }
            getSubtasks().put(id, (Subtask) fromString(parsedLine));
          }
          case "EPIC" -> {
            getEpics().put(id, (Epic) fromString(parsedLine));
          }
        }
      }
      //id thing
      setNextId(Integer.parseInt(loadedFile.get(loadedFile.size()-1)));
      //history thing
      
    } catch (IOException e) {
      System.out.println("Ошибка при загрузке файла: " + e.getMessage());
    }
  }

  public void save() throws ManagerSaveException {

    if (file.getParentFile() != null && !file.getParentFile().exists()) {
        file.getParentFile().mkdirs();
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            
      writer.write("id,type,name,status,desc,epicId");
      writer.newLine();

      for (Task task : getTasks().values()) {
        writer.write(toString(task));
        writer.newLine();
      }

      for (Epic epic : getEpics().values()) {
        writer.write(toString(epic));
        writer.newLine();
      }

      for (Subtask subtask : getSubtasks().values()) {
        writer.write(toString(subtask));
        writer.newLine();
      }

      
            

      writer.newLine();
      writer.write("TODO: HISTORY");
      writer.newLine();
      writer.newLine();
      writer.write(String.valueOf(getNextId()));

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
  public void addTask(String name, String desc) {
    super.addTask(name, desc);
    
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

  public Task fromString(String[] parsedString) {
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
          task = new Epic(id, name, desc);
        }
        default -> throw new AssertionError();
    }

    return task;
  }

  static String toString(HistoryManager manager) {

  }
  String List<Integer> fromString(String value) {
    
  }

  
}
