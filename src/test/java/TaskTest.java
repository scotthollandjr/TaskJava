import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class TaskTest {

  @Test
  public void Task_instantiateCorrectly_true() {
    Task myTask = new Task("Bring Appa");
    assertEquals(true, myTask instanceof Task);
  }

  @Test
  public void Task_instantiateWithDescription_String() {
    Task myTask = new Task("Bring Appa");
    assertEquals("Bring Appa", myTask.getDescription());
  }

  @Test
  public void isCompleted_isFalseAfterInstantiation_false() {
    Task myTask = new Task("Bring Appa");
    assertEquals(false, myTask.isCompleted());
  }

  @Test
  public void getCreatedAt_instantiatesWithCurrentTime_today() {
    Task myTask = new Task("Bring Appa");
    assertEquals(LocalDateTime.now().getDayOfWeek(), myTask.getCreatedAt().getDayOfWeek());
  }

  @Test
  public void all_returnsAllInstancesOfTask_true() {
    Task firstTask = new Task("Bring Appa");
    Task secondTask = new Task("Feed Breakfast");
    assertTrue(Task.all().contains(firstTask));
    assertTrue(Task.all().contains(secondTask));
  }

  @Test
  public void clear_emptiesAllTasksFromArrayList_0() {
    Task myTask = new Task("Bring Appa");
    Task.clear();
    assertEquals(Task.all().size(), 0);
  }

  @Test
  public void getId_tasksInstantiatesWithAnID_1() {
    Task.clear();
    Task myTask = new Task("Bring Appa");
    assertEquals(1, myTask.getId());
  }

  @Test
  public void find_returnsTaskWithSameId_secondTask() {
    Task firstTask = new Task("Bring Appa");
    Task secondTask = new Task("Feed Breakfast");
    assertEquals(Task.find(secondTask.getId()), secondTask);
  }

  @Test
  public void find_returnsNullWhenNoTasksFound_null() {
    assertTrue(Task.find(999) == null);
  }
}
