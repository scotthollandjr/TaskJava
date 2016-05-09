import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Task {
  private int id;
  private String description;
  private String date;
  private Boolean completed;

  public Task(String description, String date) {
    this.description = description;
    this.date = date;
    this.completed = false;
  }

  public String getDescription() {
    return description;
  }

  public String getDate() {
    return date;
  }

  public int getId() {
    return id;
  }

  public static List<Task> all() {
    try(Connection con = DB.sql2o.open()) {
      String allQuery = "SELECT id, description, date FROM tasks WHERE completed= :false ORDER BY date ASC;";
      List<Task> all = con.createQuery(allQuery)
        .addParameter("false", false)
        .executeAndFetch(Task.class);
        return all;
    }
  }

  @Override
  public boolean equals(Object otherTask){
    if (!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getDescription().equals(newTask.getDescription()) &&
             this.getId() == newTask.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tasks(description, date, completed) VALUES (:description, :date, :completed);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", this.description)
        .addParameter("date", this.date)
        .addParameter("completed", this.completed)
        .executeUpdate()
        .getKey();
    }
  }

  public static Task find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks where id=:id;";
      Task task = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Task.class);
      return task;
    }
  }

  public void update(String newDescription) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tasks SET description = :description WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("description", newDescription)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void addCategory(Category category) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO categories_tasks (category_id, task_id) VALUES (:category_id, :task_id);";
      con.createQuery(sql)
        .addParameter("category_id", category.getId())
        .addParameter("task_id", this.getId())
        .executeUpdate();
    }
  }

  public List<Category> getCategories() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT category_id FROM categories_tasks WHERE task_id = :task_id;";
      List<Integer> categoryIds = con.createQuery(joinQuery)
        .addParameter("task_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Category> categories = new ArrayList<Category>();

      for (Integer categoryId : categoryIds) {
        String taskQuery = "SELECT * FROM categories WHERE id = :categoryId;";
        Category category = con.createQuery(taskQuery)
          .addParameter("categoryId", categoryId)
          .executeAndFetchFirst(Category.class);
        categories.add(category);
      }
      return categories;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM tasks WHERE id = :id;";
        con.createQuery(deleteQuery)
          .addParameter("id", this.getId())
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM categories_tasks WHERE task_id = :taskId;";
        con.createQuery(joinDeleteQuery)
          .addParameter("taskId", this.getId())
          .executeUpdate();
    }
  }

  public void complete() {
    try(Connection con = DB.sql2o.open()) {
      String completeQuery = "UPDATE tasks SET completed = true WHERE id = :id;";
      con.createQuery(completeQuery)
        .addParameter("id", this.getId())
        .executeUpdate();

    }
  }

  public static List<Task> allCompleted() {
    try(Connection con = DB.sql2o.open()) {
      String completeQuery = "SELECT id, description, date FROM tasks WHERE completed = :true ORDER BY date ASC;";
      List<Task> allCompleted = con.createQuery(completeQuery)
        .addParameter("true", true)
        .executeAndFetch(Task.class);
        return allCompleted;
      }
    }

}
