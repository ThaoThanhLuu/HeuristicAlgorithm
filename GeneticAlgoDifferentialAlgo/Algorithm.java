import java.util.ArrayList;

/**
 * Abstact Algorithm class to be utilized by GA and DE
 *
 */
abstract class Algorithm {

    double bestFunction = 0;

    abstract ArrayList<Double> execute();

    /**
     *  randomize between the lower and higher range of the associated Function
     * @param lowerRange
     * @param higherRange
     * @return
     */
    double random(double lowerRange, double higherRange){
        MTRandom randomizer = new MTRandom();
        return lowerRange + (higherRange - lowerRange) * randomizer.nextDouble();
    }

}