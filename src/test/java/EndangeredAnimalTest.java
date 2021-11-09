import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EndangeredAnimalTest {
    @ExtendWith(DatabaseRule.class)
    public DatabaseRule databaseRule=new DatabaseRule();

    @Test
    public void testInstanceOfEndangeredAnimalsClass_true(){
        EndangeredAnimal testAnimal= setUpNewAnimal();
        assertTrue(testAnimal instanceof EndangeredAnimal);
    }

    @Test
    public void allInstancesAreSaved(){
        EndangeredAnimal testAnimal=setUpNewAnimal();
        testAnimal.save();
        assertEquals(EndangeredAnimal.all().get(0).getHealth(), testAnimal.getHealth());
    }

    @Test
    public void findByIdReturnsCorrectInfo(){
        EndangeredAnimal testAnimal=setUpNewAnimal();
        testAnimal.save();
        Animal foundAnimal= Animal.find(testAnimal.getId());
        assertEquals(foundAnimal.getHealth(), testAnimal.getHealth());

    }
    @Test
    public void deleteByID(){
        EndangeredAnimal testAnimal=setUpNewAnimal();
        testAnimal.save();
        testAnimal.delete();
        assertNull(Animal.find(testAnimal.getId()));

    }
    @Test
    public void ensureNameFieldCannotBeEmpty(){
        EndangeredAnimal testAnimal=new EndangeredAnimal("","endangered","","");
        try {
            testAnimal.save();
        }catch (IllegalArgumentException e){
            System.out.println(e);

        }
    }

    @Test
    public void deleteAllEntries(){
        EndangeredAnimal testAnimal=setUpNewAnimal();
        EndangeredAnimal otherAnimal=setUpNewAnimal();
        testAnimal.save();
        otherAnimal.save();
        Animal.deleteAll();
        List<Animal> animals=Animal.all();
        assertEquals(0,animals.size());


    }

    @Test
    public void ensureEntryIsUpdatedCorrectly(){
        EndangeredAnimal testAnimal=setUpNewAnimal();
        EndangeredAnimal otherAnimal=testAnimal;
        testAnimal.save();
        try {
            testAnimal.update(testAnimal.getId(),"endangered","okay","newborn");
            Animal updatedAnimal=  Animal.find(testAnimal.getId());
            assertEquals(updatedAnimal.getId(),otherAnimal.getId());
            assertEquals(updatedAnimal.getHealth(),otherAnimal.getHealth());
        }catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }

    private EndangeredAnimal setUpNewAnimal() {
        return new EndangeredAnimal("Albino Giraffe","endangered","healthy","young");
    }

}