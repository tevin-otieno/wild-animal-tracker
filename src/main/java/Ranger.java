import org.sql2o.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ranger {

    private int id;
    private String name;
    private String badge_number;
    private String phone_number;

    public Ranger(String name, String badge_number, String phone_number) {
        this.name = name;
        this.badge_number = badge_number;
        this.phone_number = phone_number;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBadge_number() {
        return badge_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public static List<Ranger> all(){
        try (Connection con=DB.sql2o.open()){
            String sql="SELECT * FROM rangers";
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Ranger.class);

        }

    }

    public void save(){
        try (Connection con=DB.sql2o.open()){
            String sql="INSERT INTO rangers (name,badge_number,phone_number) VALUES (:name,:badge_number,:phone_number)";
            if(name.equals("")||badge_number.equals("")||phone_number.equals("")){
                throw new IllegalArgumentException("All fields must be filled");
            }
            this.id=(int) con.createQuery(sql,true)
                    .addParameter("name",this.name)
                    .addParameter("badge_number",this.badge_number)
                    .addParameter("phone_number",this.phone_number)
                    .executeUpdate()
                    .getKey();
        }
    }

    public static Ranger find(int id){
        try (Connection con=DB.sql2o.open()){
            String sql="SELECT * FROM rangers WHERE id=:id";
            return con.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(Ranger.class);
        }

    }

    public void update(int id,String name,String phone_number){
        try (Connection con=DB.sql2o.open()){
            String sql="UPDATE rangers SET name=:name,phone_number=:phone_number WHERE id=:id";
            if(name.equals("")||phone_number.equals("")){
                throw new IllegalArgumentException("All fields must be filled");
            }
            con.createQuery(sql)
                    .addParameter("id",this.id)
                    .addParameter("name",name)
                    .addParameter("phone_number",phone_number)
                    .executeUpdate();

        }

    }

    public void delete(){
        try (Connection con=DB.sql2o.open()){
            String sql="DELETE FROM rangers WHERE id=:id";

            con.createQuery(sql)
                    .addParameter("id",this.id)
                    .executeUpdate();
        }
    }

    public List<Sightings> getRangerSightings(){
        try (Connection con=DB.sql2o.open()){
            String sql="SELECT sighting_id FROM rangers_sightings WHERE ranger_id=:ranger_id";
            List<Integer> sightings_ids=con.createQuery(sql)
                    .addParameter("ranger_id",this.getId())
                    .executeAndFetch(Integer.class);
            List<Sightings> sightings=new ArrayList<Sightings>();

            for(Integer sighting_id:sightings_ids){
                String sightingsQuery="SELECT * FROM sightings WHERE id=:sighting_id";
                Sightings sighting=con.createQuery(sightingsQuery)
                        .addParameter("sighting_id",sighting_id)
                        .executeAndFetchFirst(Sightings.class);
                sightings.add(sighting);

            }
            if(sightings.size()==0){
                throw new IllegalArgumentException("Ranger has no sighting");
            }
            else {return sightings;}


        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ranger ranger = (Ranger) o;
        return id == ranger.id &&
                name.equals(ranger.name) &&
                badge_number.equals(ranger.badge_number) &&
                phone_number.equals(ranger.phone_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, badge_number, phone_number);
    }
}
