/**
 * Pulkit Hooda - Lab 1
 */

import java.math.*;
import java.util.Scanner;
import static java.lang.Math.sqrt;

public class FallingPenny {
    // simulating the falling velocity of a penny.
    // add acceleration here.
    public static double acceleration=9.8;
             // add ESBHeight here.
    public static long ESBHeight = 431;

    public static void main(String[] args)
    {
           // add falling time here.
        double fallingTimeVar = sqrt(2 * ESBHeight / acceleration);
        String result;
        double fallingDistance=ESBHeight;
        long buildingHeight;
        String planet;
        double localAcceleration;
        double time = 0;
        double velocity = 0;
        double timeToTerminalVelocity = 0;
        double timeAtTerminalVelocity = 0;
        double accelDistance = 0;
        Scanner in = new Scanner(System.in);

        System.out.println("Test falling time: "+fallingTimeVar);
        // Part 1.
        fallingTimeVar=fallingTime(fallingDistance);

        // Part 2. So how fast is the penny moving?
        velocity = time*acceleration;
        System.out.println("The velocity is "+velocity);

        // Part 3. but what about terminal velocity? This is the point at which air resistance == gravity.
        // 18 m/s is terminal velocity. How long will it take to get there?
        timeToTerminalVelocity= 18/acceleration;
        System.out.println("The time to reach terminal velocity is "+timeToTerminalVelocity+" seconds.");

        // Part 4.  So how far will the penny fall during the pre-terminal velocity time? Let's store this in a variable
        // called accelDistance.
        accelDistance=acceleration*(timeToTerminalVelocity*timeToTerminalVelocity)/2;
        System.out.println("Before reaching terminal velocity the penny will fall "+accelDistance+"meters");

        // Part 5. And how long will the penny fall at terminal velocity?
        timeAtTerminalVelocity=sqrt(2*(ESBHeight-accelDistance)/acceleration);
        System.out.println("The penny will fall for "+timeAtTerminalVelocity+"after reaching terminal velocity");

       // part 10: put your while loop here.

        System.out.print("Enter the height of the building: ");
        buildingHeight=in.nextInt();
        System.out.print("Enter the planet/moon: ");
        planet=in.nextLine();
        localAcceleration=getAcceleration(planet);
        fallingTimeVar=spaceFallingTime(buildingHeight,localAcceleration);
        System.out.println("It will take "+ fallingTimeVar+ " seconds for the penny to fall a distance of "+buildingHeight+" on "+planet);

    }

    // part 6.  Make a static method called fallingTime. It should take one parameter,
    // called fallingDistance, as input, and return the time needed to fall a
    // given distance, according to the expression above. Try it out with different distances.

    public static double fallingTime(double fallingDistance)
    {
        return sqrt(2*fallingDistance/acceleration);
    }

    // part 7. Step 7: Make a static method called spaceFallingTime that takes two parameters:
    // a distance and a local acceleration, and returns the time elapsed.

    public static double spaceFallingTime(long fallingDistance, double localAcceleration)
    {
        return sqrt(2*fallingDistance/localAcceleration);
    }

    // part 8, 9: Let's make a helper function called getAcceleration. It will take a string,
    //###  representing our location, and return the appropriate value. We'll do this using a
    //  conditional or if statement.

    public static double getAcceleration(String currentLocation)
    {
        if(currentLocation.equalsIgnoreCase("moon"))
        {
            return 1.6;
        }
        else if (currentLocation.equalsIgnoreCase("mars"))
        {
            return 3.69;
        }
        else if(currentLocation.equalsIgnoreCase("jupiter"))
        {
            return 24.79;
        }
        else if (currentLocation.equalsIgnoreCase("saturn"))
        {
            return 10.44;
        }
        else if (currentLocation.equalsIgnoreCase("titan"))
        {
            return 1.4;
        }
        else if (currentLocation.equalsIgnoreCase("venus"))
        {
            return 8.87;
        }
        else
        {
            System.out.println("I don't know that planet, Here's Earth");
            return 9.8;
        }
    }


}
