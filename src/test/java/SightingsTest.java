import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

class SightingsTest {
    @ExtendWith(DatabaseRule.class)
    public DatabaseRule databaseRule=new DatabaseRule();

    @Test
    public void createInstanceOfSightingsClass_true() {

        Sightings sighting= setUpNewSighting();
        assertTrue(sighting instanceof Sightings);
    }

    @Test
    public void allInstancesAreSaved() {
        Sightings sightings=setUpNewSighting();
        Sightings otherSighting=new Sightings(-1,1,1);
        try {
            sightings.save();
            otherSighting.save();
            assertEquals(Sightings.find(sightings.getId()), sightings);
        }catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }
    //
    @Test
    public void findSightingByID() {
        Sightings sighting=setUpNewSighting();
        sighting.save();
        Sightings foundSighting=Sightings.find(sighting.getId());
        assertEquals(foundSighting, sighting);

    }
    @Test
    public void deleteSightingByID() {
        Sightings sighting=setUpNewSighting();
        sighting.save();
        sighting.delete();
        assertNull(Sightings.find(sighting.getId()));

    }
    @Test
    public void deleteAll() {
        Sightings sighting=setUpNewSighting();
        Sightings otherSightings=setUpNewSighting();
        sighting.save();
        otherSightings.save();
        Sightings.deleteAll();

        assertEquals(0,Sightings.all().size());

    }

    //helper
    private Sightings setUpNewSighting() {
        return new Sightings(1,1,1);
    }

}