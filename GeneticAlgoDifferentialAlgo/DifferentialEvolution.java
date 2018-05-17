import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Differential Evolution Algorithm that is a subclass of Algorithm
 * Contains different ways of creating outputs of DE
 *      All possible DE are number 1, 7 and 9
 * Within this class there are 2 distinct methods
 * Selection and radomizationPopulations Method both of which are void
 */
public class DifferentialEvolution extends Algorithm {

    private Functions deFunctions;
    private double functions;
    private double crossOverRate;
    private double[] range;
    private int option;
    private int dimensions,gMax,numberOfSolutions;

    /**
     * DE Constructor
     * @param dimensions
     * @param gMax
     * @param numberOfSolutions
     * @param deFunctions
     * @param functions
     * @param crossOverRate
     * @param range
     * @param option
     */
    DifferentialEvolution(int dimensions, int gMax,int numberOfSolutions, Functions deFunctions, double functions, double crossOverRate, double[] range, int option){
        this.dimensions = dimensions;
        this.gMax = gMax;
        this.numberOfSolutions = numberOfSolutions;
        this.deFunctions = deFunctions;
        this.functions = functions;
        this.crossOverRate = crossOverRate;
        this.range = range;
        this.option = option;
        double lambda = functions / 2;
    }

    /**
     * Execute DE
     * @return ArrayList<Double></Double>
     */
    ArrayList<Double> execute() {
        Population pop = new Population(numberOfSolutions, dimensions);
        randomizePopulations(pop, range);
        int g = 0;
        pop.evaluate(deFunctions);
        if (g < gMax) {
            do {
                for (int i = 0; i < numberOfSolutions; i++) {
                    ArrayList<Double> randomRow = new ArrayList<>();
                    int rowOne, rowTwo, rowThree, rowFour;
                    do {
                        rowOne = (int) random(0, numberOfSolutions);
                    } while (rowOne == i);
                    do {
                        rowTwo = (int) random(0, numberOfSolutions);
                    } while (rowTwo == rowOne || rowTwo == i);
                    do {
                        rowThree = (int) random(0, numberOfSolutions);
                    } while (rowThree == rowTwo || rowThree == rowOne || rowThree == i);
                    do {
                        rowFour = (int) random(0, numberOfSolutions);
                    } while (rowFour == rowThree || rowFour == rowTwo || rowFour == rowOne || rowFour == i);

                    if (option == 1) {
                        randomRow = best_1_exp(pop, pop.populations.get(i), rowTwo, rowThree);

                    } else if (option == 7) {
                        randomRow = rand_1_bin(pop, pop.populations.get(i), rowOne, rowTwo, rowThree);

                    } else if (option == 9) {
                        randomRow = best_2_bin(pop, pop.populations.get(i), rowOne, rowTwo, rowThree, rowFour);

                    }else{
                        System.out.println("Invalid input");
                        System.exit(0);
                    }
                    selection(pop, i, randomRow);

                    pop.functions.clear();
                    pop.evaluate(deFunctions);
                }
                g++;
            } while (g < gMax);
        }
        pop.setBestSolFit();
        bestFunction = pop.getBestFitness;
        return pop.bestSolutionByFit;
    }

    /**
     *  Cross Exp using two Array<Double></Double>
     * @param first
     * @param second
     * @return ArrayList<Double></Double> randomRow
     */
    private ArrayList<Double> crossExp(ArrayList<Double> first, ArrayList<Double> second){
        ArrayList<Double> randomRow = new ArrayList<>(first);

        if (!(random(0, 1) < crossOverRate)) {
            return randomRow;
        }
        int temp = (int)random(0,dimensions);
        int randTemp = (int)random(0,dimensions);
        int counter = 0;

        while(counter < randTemp){
            randomRow.set((temp+counter)%dimensions, second.get((temp+counter)%dimensions));
            counter++;
        }

        return randomRow;
    }

    /**
     * Cross Bin using two Array<Double></Double>
     * @param first
     * @param second
     * @return randomRow
     */
    private ArrayList<Double> crossBin(ArrayList<Double> first, ArrayList<Double> second){
        ArrayList<Double> randomRow = new ArrayList<>(first);

        IntStream.range(0, dimensions).filter(j -> random(0, 1) < crossOverRate).forEach(j -> randomRow.set(j, second.get(j)));

        return randomRow;
    }

    /**
     *
     * @param pop
     * @param first
     * @param rowTwo
     * @param rowThree
     * @return arrayList of selected popluation
     */
    private ArrayList<Double> best_1_exp(Population pop, ArrayList<Double> first, int rowTwo, int rowThree){
        ArrayList<Double> second = new ArrayList<>();
        pop.setBestSolFit();

        int j = 0;
        while (j < dimensions) {
            second.add(pop.bestSolutionByFit.get(j)+functions*(pop.populations.get(rowTwo).get(j)-pop.populations.get(rowThree).get(j)));
            j++;
        }
        return crossExp(first, second);
    }

    /**
     *
     * @param pop
     * @param first
     * @param rowOne
     * @param rowTwo
     * @param rowThree
     * @return Arraylist of selected population
     */
    private ArrayList<Double> rand_1_bin(Population pop, ArrayList<Double> first, int rowOne, int rowTwo, int rowThree){
        ArrayList<Double> second = new ArrayList<>();

        int j = 0;
        while (j < dimensions) {
            second.add(pop.populations.get(rowOne).get(j)+functions*(pop.populations.get(rowTwo).get(j)-pop.populations.get(rowThree).get(j)));
            j++;
        }
        return crossBin(first, second);
    }

    /**
     *
     * @param pop
     * @param first
     * @param rowOne
     * @param rowTwo
     * @param rowThree
     * @param rowFour
     * @return ArrayList of selected population using best
     */
    private ArrayList<Double> best_2_bin(Population pop, ArrayList<Double> first, int rowOne, int rowTwo, int rowThree, int rowFour){
        ArrayList<Double> second = new ArrayList<>();
        pop.setBestSolFit();

        int j = 0;
        while (j < dimensions) {
            second.add(pop.bestSolutionByFit.get(j)+functions*(pop.populations.get(rowOne).get(j)-pop.populations.get(rowTwo).get(j)-pop.populations.get(rowThree).get(j)-pop.populations.get(rowFour).get(j)));
            j++;
        }

        return crossBin(first, second);
    }

    /**
     *
     * @param pop
     * @param i
     * @param randomRow
     */
    private void selection(Population pop, int i, ArrayList<Double> randomRow){
        if (!(deFunctions.callFunction(randomRow, dimensions) <= pop.functions.get(i))) {
            return;
        }
        pop.populations.set(i, randomRow);
    }

    /**
     *
     * @param population
     * @param range
     */
    private void randomizePopulations(Population population, double[] range) {

        int i = 0;
        while (i < numberOfSolutions) {
            int j = dimensions - 1;
            while (j >= 0) {
                double r = random(range[0], range[1]);
                population.populations.get(i).add(r);
                j--;
            }
            i++;
        }
    }
}