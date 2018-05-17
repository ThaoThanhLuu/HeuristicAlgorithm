import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

class GeneticAlgorithm extends Algorithm {

    private double crossOverRate;
    private Mutates mutationRate;
    private double elitismRate;
    public Functions functions;
    private int numberOfSolutions, dimensions;
    private double[] range;
    private int tMax;

    /**
     * Gentic Algorithm Constructor
     * @param functions
     * @param numberOfSolutions
     * @param dimensions
     * @param range
     * @param tMax
     * @param crossOverRate
     * @param mutationRate
     * @param mutationRange
     * @param mutationPrecision
     * @param elitismRate
     */

    public GeneticAlgorithm(Functions functions, int numberOfSolutions, int dimensions, double[] range, int tMax, double crossOverRate, double mutationRate, double mutationRange, double mutationPrecision, double elitismRate){
        this.functions = functions;
        this.numberOfSolutions = numberOfSolutions;
        this.dimensions = dimensions;
        this.range = range;
        this.tMax = tMax;
        this.crossOverRate = crossOverRate;
        this.mutationRate = new Mutates(mutationRate,mutationRange,mutationPrecision);
        this.elitismRate = elitismRate;
    }

    /**
     * Execute GA
     * @return
     */
    ArrayList<Double> execute() {

        int elitism = (int)elitismRate * numberOfSolutions;
        Population pop = new Population(numberOfSolutions, dimensions);
        randomPopulations(pop, range);
        pop.evaluate(functions);
        int t = 1;

        while (t <= tMax) {
            pop.crossOver.clear();
            Population newP = new Population();
            for (int tempPopulation = 0; tempPopulation < numberOfSolutions; tempPopulation += 2) {

                ArrayList<ArrayList<Double>> parents = selection(pop);
                ArrayList<ArrayList<Double>> offspring = crossOver(parents.get(0), parents.get(1), crossOverRate);

                mutateOffSpring(offspring.get(0), mutationRate, range);
                mutateOffSpring(offspring.get(1), mutationRate, range);

                newP.populations.add(offspring.get(0));
                newP.populations.add(offspring.get(1));
                newP.numberOfSolutions += 2;
            }
            newP.dimensions = newP.populations.get(0).size();
            newP.evaluate(functions);
            eliminate(pop, newP, elitism);

            getCost(pop);
            pop.setBestSolCost();
            t++;
        }
        bestFunction = pop.getTotalCost;
        return pop.bestSolutionByCost;
    }

    /**
     *  Curate a randomize population that is dependent on the numberOfSolution
     * @param population
     * @param range
     */
    private void randomPopulations(Population population, double[] range) {

        int i = 0;
        while (i < numberOfSolutions) {
            int j = 0;
            while (j < dimensions) {
                double randomized = random(range[0], range[1]);
                population.populations.get(i).add(randomized);
                j++;
            }
            i++;
        }
    }

    /**
     *  Select randomised parents
     * @param pop
     * @return
     */
    private ArrayList<ArrayList<Double>> selection(Population pop){
        ArrayList<ArrayList<Double>> randomized = new ArrayList<>();
        ArrayList<Double> parent1 = parenSelection(pop);
        randomized.add(parent1);
        ArrayList<Double> parent2 = parenSelection(pop);
        randomized.add(parent2);
        return randomized;
    }

    /**
     * Select the best of the parents
     * @param pop
     * @return
     */
    private ArrayList<Double> parenSelection(Population pop){
        double randomized = random(1,pop.getTotalFitness);
        int tempPopulation = 0;
        while(tempPopulation < numberOfSolutions-1 && randomized > 0){
            randomized -= pop.functions.get(tempPopulation);
            tempPopulation += 1;
        }
        return pop.populations.get(tempPopulation);
    }

    /**
     * Create an offspring with the genetics of two parents
     * @param parent1
     * @param parent2
     * @param crossOverRate
     * @return
     */
    private ArrayList<ArrayList<Double>> crossOver(ArrayList<Double> parent1, ArrayList<Double> parent2, double crossOverRate) {
        ArrayList<ArrayList<Double>> o = new ArrayList<>();
        if (!(random(0, 1) < crossOverRate)) {
            o.add(parent1);
            o.add(parent2);
        } else {
            int d = (int)random(0,dimensions);
            o.add(comb(parent1.subList(0,d),parent2.subList(d,dimensions)));
            o.add(comb(parent2.subList(0,d),parent1.subList(d,dimensions)));
        }
        return o;
    }

    /**
     * Determine the total amount of cost per population
     * @param pop
     */
    private void getCost(Population pop) {
        IntStream.range(0, numberOfSolutions).forEach(tempPopulation -> {
            if (pop.functions.get(tempPopulation) >= 0) {
                pop.crossOver.add((1 / (1 + pop.functions.get(tempPopulation))));
            } else {
                pop.crossOver.add((1 + Math.abs(pop.functions.get(tempPopulation))));
            }
            pop.getTotalCost += pop.crossOver.get(tempPopulation);
        });
    }

    /**
     * Generate the randomized sequence of mutation class
     */
    class Mutates {
        double percentage, bounds, precisions;

        /**
         * Mutate Constructor
         * @param percentage
         * @param bounds
         * @param precisions
         */
        Mutates(double percentage, double bounds, double precisions){

            this.percentage = percentage;
            this.bounds = bounds;
            this.precisions = precisions;
        }
    }

    /**
     * Create the temporary mutation of a population
     * @param tempPopulation
     * @param mutationRate
     * @param range
     */
    private void mutateOffSpring(ArrayList<Double> tempPopulation, Mutates mutationRate, double[] range) {
        IntStream.range(0, dimensions).filter(i -> random(0, 1) < mutationRate.bounds).forEach(i -> {
            double sTemp = tempPopulation.get(i);
            tempPopulation.set(i, sTemp + random(-1, 1) * (range[1] - range[0]) * mutationRate.bounds * Math.pow(2, (-1 * random(0, 1) * mutationRate.precisions)));
        });
    }

    /**
     *
     * Eliminate the weakest link
     * @param pop
     * @param newP
     * @param e
     */
    private void eliminate(Population pop, Population newP, int e){
        pop.quickSort(0, pop.populations.size()-1);
        newP.quickSort(0, newP.populations.size()-1);
        IntStream.range(0, e).forEach(i -> {
            newP.populations.set(e + 1 - i, pop.populations.get(i));
            newP.functions.set(e + 1 - i, pop.functions.get(i));
        });

        crossOverPopulation(pop, newP);
    }

    /**
     * Combine the best offsprings
     * @param parent1
     * @param parent2
     * @return
     */
    private ArrayList<Double> comb(List<Double> parent1, List<Double> parent2){
        ArrayList<Double> offspring = new ArrayList<>();
        offspring.addAll(parent1);
        offspring.addAll(parent2);
        return offspring;
    }

    /**
     * Combine the old population with the new best Population
     * @param pop
     * @param newPopulations
     */
    private void crossOverPopulation(Population pop, Population newPopulations){
        ArrayList<ArrayList<Double>> tempP = new ArrayList<>();
        tempP.addAll(pop.populations);
        ArrayList<Double> tempF = pop.functions;

        pop.functions.clear();
        pop.functions.addAll(newPopulations.functions);
        newPopulations.functions.clear();
        newPopulations.functions.addAll(tempF);

        pop.populations.clear();
        pop.populations.addAll(newPopulations.populations);
        newPopulations.populations.clear();
        newPopulations.populations.addAll(tempP);
    }

}
