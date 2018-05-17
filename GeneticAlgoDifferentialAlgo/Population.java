import java.util.ArrayList;
import java.util.stream.IntStream;

public class Population {

    int dimensions, numberOfSolutions;

    ArrayList<ArrayList<Double>> populations;

    ArrayList<Double> functions;
    ArrayList<Double> crossOver;

    double getTotalCost, getBestCost, getTotalFitness, getBestFitness;
    ArrayList<Double> bestSolutionByCost;
    ArrayList<Double> bestSolutionByFit;

    /**
     * Population Constructor
     */
    Population(){
        dimensions = 0;
        numberOfSolutions = 0;
        populations = new ArrayList<>();
        functions = new ArrayList<>();
        crossOver = new ArrayList<>();
        bestSolutionByCost = new ArrayList<>();
        bestSolutionByFit = new ArrayList<>();
    }

    /** Population Constructor
     */
    Population(int numberOfSolutions, int dimensions){
        this.dimensions = dimensions;
        this.numberOfSolutions = numberOfSolutions;
        populations = new ArrayList<>();
        IntStream.range(0, numberOfSolutions).forEach(placeHolder -> populations.add(new ArrayList<>()));
        functions = new ArrayList<>();
        crossOver = new ArrayList<>();
        bestSolutionByCost = new ArrayList<>();
        bestSolutionByFit = new ArrayList<>();
    }

    /**
     * Find the best fitness
     * @param fit
     */
    void evaluate(Functions fit){
        IntStream.range(0, numberOfSolutions).mapToObj(placeHolder -> fit.callFunction(populations.get(placeHolder), dimensions)).forEach(fitness -> functions.add(fitness));
    }

    /**
     * Find the best fit Solution
     */
    void setBestSolFit(){
        bestSolutionByFit.clear();
        getBestFitness = functions.get(0);
        bestSolutionByFit.addAll(populations.get(0));
        for(int placeHolder = 1; placeHolder < numberOfSolutions; placeHolder++){
            if(functions.get(placeHolder) < getBestFitness){
                bestSolutionByFit.clear();
                getBestFitness = functions.get(placeHolder);
                bestSolutionByFit.addAll(populations.get(placeHolder));
            }
        }
    }

    /**
     * Find the best Solution Cost
     */
    void setBestSolCost(){
        getBestCost = crossOver.get(0);
        IntStream.range(1, numberOfSolutions).filter(placeHolder -> functions.get(placeHolder) < getBestCost).forEach(placeHolder -> {
            getBestCost = crossOver.get(placeHolder);
            bestSolutionByCost = populations.get(placeHolder);
        });
    }

    /**
     *
     * Swap population dependent (Noisy)
     * @param bottom
     * @param top
     * @return
     */
    private int swapPopulations(int bottom, int top)
    {
        double pivot = functions.get(top);
        int placeHolder = (bottom-1);
        for (int j=bottom; j<top; j++) {
            if (!(functions.get(j) <= pivot)) {
                continue;
            }
            placeHolder++;

            // swapPopulations
            double tempoFunction = functions.get(placeHolder);
            ArrayList<Double> tempS = populations.get(placeHolder);

            populations.set(placeHolder, populations.get(j));
            functions.set(placeHolder, functions.get(j));
            populations.set(j, tempS);
            functions.set(j, tempoFunction);
        }
        double tempoFunction = functions.get(placeHolder+1);
        ArrayList<Double> tempS = populations.get(placeHolder+1);

        populations.set(placeHolder+1,populations.get(top));
        functions.set(placeHolder+1,functions.get(top));
        populations.set(top,tempS);
        functions.set(top,tempoFunction);

        return placeHolder+1;
    }

    /**
     *
     * A Simple quicksort alg
     * @param bottom
     * @param top
     */
    void quickSort(int bottom, int top)
    {
        if (bottom >= top) {
            return;
        }
        int pi = swapPopulations(bottom, top);
        quickSort(bottom, pi-1);
        quickSort(pi+1, top);
    }


}