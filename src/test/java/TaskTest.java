import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class TaskTest {

  @Test
  public void Task_instantiateCorrectly_true() {
    Task myTask = new Task("Mow the lawn");
    assertEquals(true, myTask instanceof Task);
  }

  @Test
  public void Task_instantiateWithDescription_String() {
    assertEquals("Mow the lawn", myTask.getDescription());
  }
  
}
