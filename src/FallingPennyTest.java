import static org.junit.jupiter.api.Assertions.*;

class FallingPennyTest {

    @org.junit.jupiter.api.Test
    void fallingTime() {
        assertEquals(FallingPenny.fallingTime(0), 0);
        System.out.println("Falling time for two seconds is " + FallingPenny.fallingTime(2));
        FallingPenny.fallingTime(10);
    }

    @org.junit.jupiter.api.Test
    void spaceFallingTime() {
        FallingPenny.spaceFallingTime(2, 9.8);

    }

    @org.junit.jupiter.api.Test
    void getAcceleration() {
        System.out.println("Earth acceleration: " + FallingPenny.getAcceleration("earth"));
        System.out.println("Titan acceleration: " + FallingPenny.getAcceleration("titan"));
        System.out.println("Pluto acceleration: " + FallingPenny.getAcceleration("pluto"));
    }



}