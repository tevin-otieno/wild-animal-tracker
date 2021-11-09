import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class DatabaseRule implements AfterEachCallback, BeforeEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DB.sql2o=new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test", "moringa", "pass123");

    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {

        try (Connection con = DB.sql2o.open()){

            String deleteAnimalQuery="DELETE FROM animals ";
            String deleteSightingsQuery="DELETE FROM sightings *";
            String deleteRangerQuery="DELETE FROM rangers";
            con.createQuery(deleteAnimalQuery).executeUpdate();
            con.createQuery(deleteSightingsQuery).executeUpdate();
            con.createQuery(deleteRangerQuery).executeUpdate();
            String deleteLocationQuery="DELETE FROM locations *";
            con.createQuery(deleteLocationQuery).executeUpdate();
        }
    }
}
