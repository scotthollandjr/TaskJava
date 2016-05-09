import org.fluentlenium.adapter.FluentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import org.junit.*;
import java.util.List;
import org.sql2o.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();
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

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("To-do List");
  }
  // 
  // @Test
  // public void categoryIsCreatedTest() {
  //   goTo("http://localhost:4567/");
  //   click("a", withText("Add a New Category"));
  //   fill("#name").with("Household chores");
  //   submit(".btn");
  //   assertThat(pageSource()).contains("Your category has been saved.");
  // }
  //
  // @Test
  // public void categoryIsDisplayedTest() {
  //   goTo("http://localhost:4567/categories/new");
  //   fill("#name").with("Household chores");
  //   submit(".btn");
  //   click("a", withText("View categories"));
  //   assertThat(pageSource()).contains("Household chores");
  // }
  //
  //   @Test
  // public void categoryTasksFormIsDisplayed() {
  //   goTo("http://localhost:4567/categories/new");
  //   fill("#name").with("Shopping");
  //   submit(".btn");
  //   click("a", withText("View categories"));
  //   click("a", withText("Shopping"));
  //   click("a", withText("Add a new task"));
  //   assertThat(pageSource()).contains("Add a Task to Shopping");
  // }
  //
  // @Test
  // public void tasksIsAddedAndDisplayed() {
  //   goTo("http://localhost:4567/categories/new");
  //   fill("#name").with("Banking");
  //   submit(".btn");
  //   click("a", withText("View categories"));
  //   click("a", withText("Banking"));
  //   click("a", withText("Add a new task"));
  //   fill("#description").with("Deposit paycheck");
  //   submit(".btn");
  //   assertThat(pageSource()).contains("Deposit paycheck");
  // }
}
