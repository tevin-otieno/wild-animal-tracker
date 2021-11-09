import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

class RangerTest {

    @ExtendWith(DatabaseRule.class)
    public DatabaseRule databaseRule=new DatabaseRule();

    @Test
    public void createInstanceOfRangersClass_true(){
        Ranger ranger= setUpNewRanger();
        assertEquals(true,ranger instanceof Ranger);
    }

    @Test
    public void allEntriesAreSaved() {
        Ranger ranger= setUpNewRanger();
        ranger.save();
        assertEquals(Ranger.all().get(0), ranger);

    }

    @Test
    public void emptyFieldsAreNotSaved() {
        Ranger ranger=new Ranger("","","0713245678");
        try{
            ranger.save();
            assertEquals(Ranger.all().get(0), ranger);
        }catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }

    @Test
    public void findById() {
        Ranger ranger= setUpNewRanger();
        Ranger otherRanger=new Ranger("Sylvia","2","0726108898");
        ranger.save();
        otherRanger.save();
        Ranger foundRanger=Ranger.find(ranger.getId());
        assertEquals(foundRanger, ranger);

    }

    @Test
    public void entryIsUpdatedCorrectly() {
        Ranger ranger= setUpNewRanger();
        Ranger otherRanger=ranger;
        ranger.save();
        try {
            ranger.update(ranger.getId(),"Ruth Mwangi","0714735954");
            Ranger foundRanger=Ranger.find(ranger.getId());
            assertNotEquals(foundRanger,otherRanger);
            assertEquals(foundRanger.getId(),otherRanger.getId());

        }catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }

    @Test
    public void entriesAreDeleted() {
        Ranger ranger= setUpNewRanger();
        Ranger otherRanger=new Ranger("Sylvia","2","0726108898");
        ranger.save();
        otherRanger.save();
        ranger.delete();
        assertNull(Ranger.find(ranger.getId()));

    }

    @Test
    public void allSightingsAreReturnedForRanger() {
        Ranger ranger=setUpNewRanger();
        try {
            Location location=new Location("Zone A");
            ranger.save();
            location.save();
            Sightings sighting=new Sightings(location.getId(),ranger.getId(),1);
            Sightings otherSighting=new Sightings(1,ranger.getId(),1);
            sighting.save();
            otherSighting.save();
            assertEquals(ranger.getRangerSightings().get(0),sighting);
            assertEquals(ranger.getRangerSightings().get(1),otherSighting);
        }catch (IllegalArgumentException e){
            System.out.println(e);
        }

    }

    //helper class
    private Ranger setUpNewRanger() {
        return new Ranger("Tevin","007","07123456");
    }
}