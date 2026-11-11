package tracker.src.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import tracker.src.tasks.Task;

public class InMemoryHistoryManager implements HistoryManager {

  private static class Node {
    Task task;
    Node prev;
    Node next;

    Node(Task task, Node prev, Node next) {
      this.task = task;
      this.prev = prev;
      this.next = null;
    }
  }

  private final HashMap<Integer, Node> nodeMap = new HashMap<>();
  private Node head;
  private Node tail;

  private void linkLast(Task task) {
    if (nodeMap.size() == 10) remove(head.task.getId());
    Node newNode = new Node(task, tail, null);
    if (tail == null) {
      head = newNode;
    } else {
      tail.next = newNode;
    }
    tail = newNode;
  }

  private void removeNode(Node node) {
    if (node == null) { return; }
    Node prev = node.prev;
    Node next = node.next;
    if (prev != null) {
        prev.next = next;
    } else {
        head = next;
    }

    if (next != null) {
        next.prev = prev;
    } else {
        tail = prev;
    }

    node.prev = null;
    node.next = null;
    
  }

  @Override 
  public List<Task> getHistory() {
    List<Task> result = new ArrayList<>();
    Node current = head;
    while (current != null) {
      result.add(current.task);
      current = current.next;
    }
    return result;
  }

  @Override
  public void add(Task task) {
    if (task == null) return;
      
    Node existingNode = nodeMap.get(task.getId());
    if (existingNode != null) {
      removeNode(existingNode);
    }

    linkLast(task);
  
    nodeMap.put(task.getId(), tail);
  }

  @Override
  public void remove(int id) {
    Node node = nodeMap.remove(id); 
    removeNode(node);
  }
}