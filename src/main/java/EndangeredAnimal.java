import org.sql2o.Connection;

public class EndangeredAnimal extends Animal implements DatabaseInterface{

    private String health;
    private String age;
    public static final String HEALTH_HEALTHY="healthy";
    public static final String HEALTH_ILL="ill";
    public static final String HEALTH_OKAY="okay";

    public static final String AGE_NEWBORN="newborn";
    public static final String AGE_YOUNG="young";
    public static final String AGE_ADULT="adult";

    public static final String ANIMAL_CATEGORY="endangered";

    public EndangeredAnimal(String name,String type,String health,String age) {
        super(name,type);
        this.category=type;
        this.health=health;
        this.age=age;
    }

    @Override
    public String getHealth() {
        return health;
    }

    @Override
    public String getAge() {
        return age;
    }

    @Override
    public void save() {
        if(this.name.equals("")||this.category.equals("")||this.health.equals("")||this.age.equals("")){
            throw new IllegalArgumentException("Fields cannot be empty");
        }
        try (Connection con=DB.sql2o.open()){


            String sql ="INSERT INTO animals (name,type,health,age) VALUES (:name,:type,:health,:age)";

            this.id=(int) con.createQuery(sql,true)
                    .addParameter("name",this.name)
                    .addParameter("type",this.category)
                    .addParameter("health",this.health)
                    .addParameter("age",this.age)
                    .executeUpdate()
                    .getKey();
        }

    }
}
