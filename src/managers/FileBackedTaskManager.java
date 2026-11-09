package tracker.src.managers;

import java.io.IOException;

import tracker.src.tasks.*;

public class FileBackedTaskManager extends InMemoryTaskManager {

  String path = ".//saved/filemanageroutput.txt";

  public FileBackedTaskManager(HistoryManager historyImplementation) {
    super(historyImplementation);
  }

  public void save(Task task) throws IOException {
    
    
  }
  
  @Override 
  public Subtask addSubtask(int epicId, String name, String desc) {
    Subtask res = super.addSubtask(epicId, name, desc);
    save(res);
    return res;
  }

  @Override 
  public Task addTask(String name, String desc) {
    Task res = super.addTask(name, desc);
    save(res);
    return res;
  }

  @Override 
  public Epic addEpic(String name, String desc) {
    Epic res = super.addEpic(name, desc);
    save(res);
    return res;
  }

  public String toString(Task task) {
    
    return String.format("%d,%s,%s,%s,%s",task.getId(),task.getType(), task.getName(), task.getStatus(), task.getDesc());
  }
  
}
