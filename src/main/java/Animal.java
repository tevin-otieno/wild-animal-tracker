import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;
import java.util.Objects;

public class Animal implements DatabaseInterface{

    public int id;
    public String name;
    public String category;
    private String health;
    private String age;
    public static final String ANIMAL_CATEGORY="normal";

    public Animal(String name,String category) {
        this.name = name;
        this.category=ANIMAL_CATEGORY;
        this.health="";
        this.age="";
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getHealth() {
        return health;
    }

    public String getAge() {
        return age;
    }



    public String getName() {
        return name;
    }

    public void save(){
        if(this.name.equals("")||this.category.equals("")||this.name.equals(null)||this.category.equals(null)){
            throw new IllegalArgumentException("Fields cannot be empty");
        }
        try (Connection con=DB.sql2o.open()){


            String sql ="INSERT INTO animals (name,category) VALUES (:name,:category)";

            this.id=(int) con.createQuery(sql,true)
                    .addParameter("name",this.name)
                    .addParameter("category",this.category)
                    .executeUpdate()
                    .getKey();
        }

    }

    public void update(int id,String type,String health,String age) {
        try (Connection con = DB.sql2o.open()) {
            if (type.equals("")) {
                throw new IllegalArgumentException("All fields must be filled");
            }
            if (type == "endangered") {
                if (health.equals("") || age.equals("")) {
                    throw new IllegalArgumentException("All fields must be filled");
                }
                String sql = "UPDATE animals SET category=:category,health=:health,age=:age WHERE id=:id";
                con.createQuery(sql)
                        .addParameter("type", type)
                        .addParameter("health", health)
                        .addParameter("age", age)
                        .addParameter("id", this.id)
                        .executeUpdate();
            } else {
                String sql = "UPDATE animals SET category=:category,health=:health,age=:age WHERE id=:id";
                con.createQuery(sql)
                        .addParameter("type", type)
                        .addParameter("health", "")
                        .addParameter("age", "")
                        .addParameter("id", this.id)
                        .executeUpdate();
            }

        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
    public static Animal find(int id){
        try (Connection con=DB.sql2o.open()){
            String sql= "SELECT * FROM animals WHERE id=:id";
            Animal animal=  con.createQuery(sql)
                    .addParameter("id",id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Animal.class);
            return animal;

        }

    }
    public static void deleteAll() {
        try (Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM animals";
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    public void delete(){
        try (Connection con=DB.sql2o.open()){
            String sql = "DELETE FROM animals WHERE id=:id";
            con.createQuery(sql)
                    .addParameter("id",this.id)
                    .executeUpdate();

        }
    }

    public static List<Animal> all(){
        try (Connection con=DB.sql2o.open()) {
            String sql ="SELECT * FROM animals";
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Animal.class);

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animals = (Animal) o;
        return name.equals(animals.name) &&
                category.equals(animals.category);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name,category);
    }
}
