import java.util.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class TaskTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/to_do_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteTasksQuery = "DELETE FROM tasks *;";
      String deleteCategoriesQuery = "DELETE FROM categories *;";
      con.createQuery(deleteTasksQuery).executeUpdate();
      con.createQuery(deleteCategoriesQuery).executeUpdate();
    }
  }

  @Test
  public void task_instantiatesCorrectly_true() {
    Task myTask = new Task("Mow the lawn", 1, "2000-01-01");
    assertEquals(true, myTask instanceof Task);
  }

  @Test
  public void getDescription_instantiatesWithDescription_String() {
    Task myTask = new Task("Mow the lawn", 1, "2000-00-00");
    assertEquals("Mow the lawn", myTask.getDescription());
  }
  @Test
  public void all_emptyAtFirst() {
    assertEquals(Task.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Task firstTask = new Task("Mow the lawn", 1, "2000-00-00");
    Task secondTask = new Task("Mow the lawn", 1, "2000-00-00");
    assertTrue(firstTask.equals(secondTask));
  }

  @Test
  public void find_findsTaskInDatabase_true() {
    Task myTask = new Task("Mow the lawn", 1, "2000-00-00");
    myTask.save();
    Task savedTask = Task.find(myTask.getId());
    assertTrue(myTask.equals(savedTask));
  }

  @Test
  public void save_assignsIdToObject() {
    Task myTask = new Task("Mow the lawn", 1, "2000-00-00");
    myTask.save();
    Task savedTask = Task.all().get(0);
    assertEquals(myTask.getId(), savedTask.getId());
  }

  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    Task myTask = new Task("Mow the lawn", 1, "2000-00-00");
    myTask.save();
    assertTrue(Task.all().get(0).equals(myTask));
  }

  @Test
  public void save_savesCategoryIdIntoDB_true() {
    Category myCategory = new Category("Household chores");
    myCategory.save();
    Task myTask = new Task("Mow the lawn", myCategory.getId(), "2000-00-00");
    myTask.save();
    Task savedTask = Task.find(myTask.getId());
    assertEquals(savedTask.getCategoryId(), myCategory.getId());
  }

  @Test
  public void getId_tasksInstantiateWithAnID_1() {
    Task myTask = new Task("Mow the lawn", 1, "2000-00-00");
    myTask.save();
    assertEquals(1, myTask.getCategoryId());
  }

  @Test
  public void find_returnsNullWhenNoTaskFound_null() {
    assertTrue(Task.find(999) == null);
  }

}
