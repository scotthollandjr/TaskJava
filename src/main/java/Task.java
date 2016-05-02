import java.util.Date;
import java.sql.Timestamp;
import java.util.List;
import org.sql2o.*;

public class Task {
  private int id;
  private String description;
  private int categoryId;
  private String due_date;

  public Task(String description, int categoryId, String due_date) {
    this.description = description;
    this.categoryId = categoryId;
    this.due_date = due_date;
  }

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public static List<Task> all() {
    String sql = "SELECT id, description, categoryId FROM tasks";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }

  @Override
  public boolean equals(Object otherTask){
    if (!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getDescription().equals(newTask.getDescription()) &&
             this.getId() == newTask.getId() &&
             this.getCategoryId() == newTask.getCategoryId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tasks(description, categoryId, due_date) VALUES (:description, :categoryId, :due_date)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", this.description)
        .addParameter("categoryId", this.categoryId)
        .addParameter("due_date", this.due_date)
        .executeUpdate()
        .getKey();
    }
  }

  public static Task find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks where id=:id";
      Task task = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Task.class);
      return task;
    }
  }


}
