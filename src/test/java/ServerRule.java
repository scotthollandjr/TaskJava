import org.junit.rules.ExternalResource;
import spark.Spark;

public class ServerRule extends ExternalResource {

  protected void before() {
    String[] args = {};
<<<<<<< HEAD
    App.main(args);
  }
=======
    App.main(args); // for other apps, replace LeapYear with the name of the main app class (often App)
   }
>>>>>>> 8bb1f281a16e747f052b7942230f74d5bd9882d3

  protected void after() {
    Spark.stop();
  }
}
