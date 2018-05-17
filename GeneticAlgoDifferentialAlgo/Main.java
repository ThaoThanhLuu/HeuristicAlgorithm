import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;

public abstract class Main extends  FitnessLibrary{

    // All fitness equations
    private static Functions[] functionList = {new Schwefel(),
            new FirstDeJong(),
            new Rosenbrock(),
            new Rastrigin(),
            new Griewangk(),
            new SineEnvelopeSineWave(),
            new StretchedVSineWave(),
            new AckeysOne(),
            new AckeysTwo(),
            new EggHolder(),
            new Rana(),
            new Pathological(),
            new Michalewicz(),
            new MastersCosineWave()};

    private static String algorithmInput = "";
    private static double elitismRate = 0.05;
    private static double F = 0.8;
    private static int option;

    /**
     *
     * Conduct Experiment
     * @param args
     */

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Type DE (Differential Alg) or GA (Genetic Alg): ");
        algorithmInput = scanner.next();

        if (algorithmInput.equals("DE")) {
            option = getOption();
            experiment();
        }else if(algorithmInput.equals("GA")){
                experiment();
        }else {
            System.out.println("invalid please try again");
            System.exit(0);
        }
    }

    /**
     * Check for the approriate option for DE mutation
     * @param alg
     * @param iterations
     * @return
     */
    static ArrayList<Double> check(Algorithm alg, int iterations){
        ArrayList<Double> results = new ArrayList<>();
        int i = 0;
        while (i < iterations) {
            alg.execute();
            results.add(alg.bestFunction);
            alg.bestFunction = 0;
            i++;
        }
        return results;
    }

    /**
     * Get the mutation option for DE
     * @return
     */
    static int getOption(){
        Scanner in = new Scanner(System.in);
        int input;
        do{
            System.out.println("mutation option: ");
            System.out.println("1: best-1-Exp");
            System.out.println("7: rand=1=Bin");
            System.out.println("9: best=2=Bin");
            System.out.println("results: ");
            input = in.nextInt();
        }while(input <= 0 || input >= 11);
        return input;
    }

    /**
     * conduct experiment of DE and/or GA
     */

    static void experiment() throws FileNotFoundException {

        for (int i = 0, listLength = functionList.length; i < listLength; i++) {
            Functions functions = functionList[i];
            int dimensions = 10;
            ArrayList<Double> results = new ArrayList<>();
            long avgTime = 0;

            // Parameters
            int check = 30;
            int iterations = 10;
            int numberOfSolutions = 50;
            double crossOverRate = 0.89;
            double mutationRate = 0.89;
            double mutationRange = 0.01;
            double mutationPrecision = 6.0;
            String line = "";
            if ("GA".equals(algorithmInput)) {
                results = check(new GeneticAlgorithm(functions, numberOfSolutions, dimensions, functions.range(), iterations, crossOverRate, mutationRate, mutationRange, mutationPrecision, elitismRate), check);

            } else if ("DE".equals(algorithmInput)) {
                results = check(new DifferentialEvolution(dimensions, iterations, numberOfSolutions, functions, F, crossOverRate, functions.range(), option), check);

            }
            System.out.print(functions.name() + ": " + results.toString());
            avgTime = functions.getTime() / check;
            int seconds = (int) (avgTime / 1000) % 60;
            System.out.println(":"+seconds);

        }
    }

}

