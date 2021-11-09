import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @ExtendWith(DatabaseRule.class)
    public DatabaseRule databaseRule=new DatabaseRule();

    @Test
    public void testInstanceOfAnimalsClass_true(){
        Animal testAnimal= setUpNewAnimal();
        assertTrue(testAnimal instanceof Animal);
    }

    @Test
    public void allInstancesAreSaved() {
        Animal testAnimal=setUpNewAnimal();
        testAnimal.save();
        assertEquals(Animal.all().get(0), testAnimal);
    }

    @Test
    public void ensureEntryIsUpdatedCorrectly() {
        Animal testAnimal=setUpNewAnimal();
        Animal otherAnimal=testAnimal;
        testAnimal.save();
        try {
            testAnimal.update(testAnimal.getId(),"endangered","ill","newborn");
            Animal updatedAnimal=  Animal.find(testAnimal.getId());
            assertEquals(updatedAnimal.getId(),otherAnimal.getId());
            assertNotEquals(updatedAnimal.getHealth(),otherAnimal.getHealth());
        }catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }

    @Test
    public void findByIdReturnsCorrectInfo() {
        Animal testAnimal=setUpNewAnimal();
        testAnimal.save();
        Animal foundAnimal= Animal.find(testAnimal.getId());
        assertEquals(foundAnimal, testAnimal);
    }

    @Test
    public void deleteById() {
        Animal testAnimal=setUpNewAnimal();
        testAnimal.save();
        testAnimal.delete();
        assertNull(Animal.find(testAnimal.getId()));
    }

    @Test
    public void deleteAllEntries() {
        Animal testAnimal=setUpNewAnimal();
        Animal otherAnimal=setUpNewAnimal();
        testAnimal.save();
        otherAnimal.save();
        Animal.deleteAll();
        List<Animal> animals=Animal.all();
        assertEquals(0,animals.size());
    }

    @Test
    public void ensureNameFieldCannotBeEmpty(){
        Animal testAnimal=new Animal("","normal");
        try {
            testAnimal.save();
        }catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }

    private Animal setUpNewAnimal() {
        return new Animal("Antelope","normal");
    }

}