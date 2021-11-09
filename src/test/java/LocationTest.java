import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @ExtendWith(DatabaseRule.class)
    public DatabaseRule databaseRule=new DatabaseRule();

    @Test
    public void createInstanceOfLocationsClass() {
        Location location=setUpNewLocation();
        assertTrue(location instanceof Location);
    }

    @Test
    public void allEntriesAreSaved() {
        Location location=setUpNewLocation();
        Location newLocation=new Location("");
        try {
            location.save();
            assertEquals(Location.all().get(0), location);
            newLocation.save();

        }catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }

    @Test
    public void entryIsDeletedSuccessfully() {
        Location location=setUpNewLocation();
        Location newLocation=new Location("Zone B");
        location.save();
        newLocation.save();
        location.delete();
        assertNull(Location.find(location.getId()));
    }
    @Test
    public void allSightingsAreReturnedForRanger() {
        Location location = setUpNewLocation();
        try {

            location.save();
            Sightings sighting=new Sightings(location.getId(),1,1);
            Sightings otherSighting=new Sightings(location.getId(),1,1);
            sighting.save();
            otherSighting.save();
            assertEquals(location.getLocationSightings().get(0),sighting);
            assertEquals(location.getLocationSightings().get(1),otherSighting);
        }catch (IllegalArgumentException e){
            System.out.println(e);
        }

    }

    //helper function
    private Location setUpNewLocation() {
        return new Location("Zone A");
    }

}