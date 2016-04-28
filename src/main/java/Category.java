import java.util.ArrayList;

public class Category {
  private String mCategoryName;
  private static ArrayList<Category> categoryArray = new ArrayList<Category>();
  private int mId;
  private ArrayList<Task> mTasks;

  public Category(String name) {
    mCategoryName = name;
    categoryArray.add(this);
    mId = categoryArray.size();
    mTasks = new ArrayList<Task>();
  }

  public String getName(){
    return mCategoryName;
  }

  public static ArrayList<Category> getCategories(){
    return categoryArray;
  }

  public static void clear() {
    categoryArray.clear();
  }

  public int getId() {
    return mId;
  }

  public static Category find(int id) {
    try {
      return categoryArray.get(id - 1);
    } catch (IndexOutOfBoundsException e) {
      return null;
    }
  }
  public ArrayList<Task> getTasks() {
    return mTasks;
  }
  public void addTask(Task task) {
    mTasks.add(task);
  }
}
