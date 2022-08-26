/**
 * Chris Brooks - Lab 1
 */

import java.lang.management.GarbageCollectorMXBean;
import java.math.*;
import java.lang.Math;
import java.util.Scanner;

public class FallingPenny {
    // simulating the falling velocity of a penny.
       // add acceleration here.
             // add ESBHeight here.

    public static void main(String[] args) {
           // add falling time here.
        String result;
        double velocity = 0;
        double timeToTerminalVelocity = 0;
        double timeAtTerminalVelocity = 0;
        double accelDistance = 0;
        Scanner in = new Scanner(System.in);

        // Part 1.


        // Part 2. So how fast is the penny moving?


        // Part 3. but what about terminal velocity? This is the point at which air resistance == gravity.
        // 18 m/s is terminal velocity. How long will it take to get there?


        // Part 4.  So how far will the penny fall during the pre-terminal velocity time? Let's store this in a variable
        // called accelDistance.


        // Part 5. And how long will the penny fall at terminal velocity?

       // part 10: put your while loop here.

    }

    // part 6.  Make a static method called fallingTime. It should take one parameter,
    // called fallingDistance, as input, and return the time needed to fall a
    // given distance, according to the expression above. Try it out with different distances.

    public static double fallingTime(double fallingDistance) {
        return 0;
    }

    // part 7. Step 7: Make a static method called spaceFallingTime that takes two parameters:
    // a distance and a local acceleration, and returns the time elapsed.

    public static double spaceFallingTime(int fallingDistance, double localAcceleration) {
        return 0;
    }

    // part 8, 9: Let's make a helper function called getAcceleration. It will take a string,
    //###  representing our location, and return the appropriate value. We'll do this using a
    //  conditional or if statement.

    public static double getAcceleration(String currentLocation) {
        return 0;
    }


}
