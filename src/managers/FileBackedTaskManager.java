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

  String path = "/Users/sendogu/programming/java stuff/tracker/src/saved/filemanageroutput.txt";
  File file = new File(path);


  public FileBackedTaskManager(HistoryManager historyImplementation) {
    super(historyImplementation);
  }

  public void loadFromFile(File file) {
    try {
      List<String> loadedFile = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);

    } catch (IOException e) {
      System.out.println("Ошибка при загрузке файла: " + e.getMessage());
    }
  }

  public void save() throws ManagerSaveException {

        // 1. Гарантированно создаем все родительские папки (например, "saved"), если их нет
    if (file.getParentFile() != null && !file.getParentFile().exists()) {
        file.getParentFile().mkdirs();
    }

        // 2. Открываем файл ОДИН раз для записи. 
        // try-with-resources автоматически закроет файл после завершения блока.
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            
            // Записываем заголовок один раз в начале файла
      writer.write("id,type,name,status,desc");
      writer.newLine();

            // Записываем все задачи
      for (Task task : getTasks().values()) {
        writer.write(toString(task));
        writer.newLine();
      }

            // Записываем все подзадачи
      for (Subtask subtask : getSubtasks().values()) {
        writer.write(toString(subtask));
        writer.newLine();
      }

            // Записываем все эпики
      for (Epic epic : getEpics().values()) {
        writer.write(toString(epic));
        writer.newLine();
      }
      
            // Примечание: если по заданию требуется сохранять историю просмотров, 
            // это обычно делается здесь отдельной строкой после пустой строки.

      writer.write(getNextId());

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

  public Task fromTask(String string) {
    int id;
    String name;
    String desc;
    Status status;
    String type;
    int epicId;
    String[] parsedString = string.split(",");

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
  
}
