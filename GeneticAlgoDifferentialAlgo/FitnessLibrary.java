import java.util.ArrayList;

import java.util.stream.IntStream;

/**
 *  Abstract Library containing all fifteen functions of private class
 */
public abstract class FitnessLibrary extends Functions {

    /**
     * Schwefel function
     */

    protected static class Schwefel extends Functions {

        public double callFunction(ArrayList<Double> array, int numbers) {
            long start = System.nanoTime();
            double s = IntStream.range(0, numbers).mapToDouble(i -> -(array.get(i)) * Math.sin(Math.sqrt(Math.abs(array.get(i))))).sum();
            time += System.nanoTime() - start;
            return s;
        }

        public double[] range() {
            double[] r = {-512,512};
            return r;
        }

        public String name(){
            return "Schwefel";
        }
    }

    /**
     *  First DeJong Function
     */

    protected static class FirstDeJong extends Functions {

        public double callFunction(ArrayList<Double> array, int numbers) {
            long start = System.nanoTime();
            double s = IntStream.range(0, numbers).mapToDouble(i -> Math.pow(array.get(i), 2)).sum();
            time += System.nanoTime() - start;
            return s;
        }

        public double[] range() {
            double[] r = {-100,100};
            return r;
        }

        public String name(){
            return "1st De Jong";
        }
    }

    /**
     *  Rosenbrock Function
     */

    protected static class Rosenbrock extends Functions {

        public double callFunction(ArrayList<Double> array, int numbers) {
            long start = System.nanoTime();
            double s = IntStream.range(0, numbers - 1).mapToDouble(i -> (100 * ((Math.pow(array.get(i), 2)) - array.get(i + 1))) + Math.pow((1 - array.get(i)), 2)).sum();
            time += System.nanoTime() - start;
            return s;
        }

        public double[] range() {
            double[] r = {-100,100};
            return r;
        }

        public String name(){
            return "Rosenbrock";
        }
    }

    /**
     *  Rastrigin Function
     */

    protected static class Rastrigin extends Functions {

        public double callFunction(ArrayList<Double> array, int numbers) {
            long start = System.nanoTime();
            double s = IntStream.range(0, numbers).mapToDouble(i -> Math.pow(array.get(i), 2) - (10 * Math.cos(2 * Math.PI * array.get(i)))).sum();
            s = s*2*numbers;
            time += System.nanoTime() - start;
            return s;
        }

        public double[] range() {
            double[] r = {-30,30};
            return r;
        }

        public String name(){
            return "Rastrigin";
        }
    }

    /**
     *  SineEnvelopeSineWave Function
     */

    protected static class SineEnvelopeSineWave extends Functions {

        public double callFunction(ArrayList<Double> array, int numbers) {
            long start = System.nanoTime();
            double s = IntStream.range(0, numbers - 1).mapToDouble(i -> 0.5 + Math.pow(Math.sin((Math.pow(array.get(i), 2) + Math.pow(array.get(i + 1), 2) - 0.5)), 2) / Math.pow((1 + 0.001 * (Math.pow(array.get(i), 2) + Math.pow(array.get(i + 1), 2))), 2)).sum();
            s = -s;
            time += System.nanoTime() - start;
            return s;
        }

        public double[] range() {
            double[] r = {-30,30};
            return r;
        }

        public String name(){
            return "Sine Envelope Sine Wave";
        }
    }

    /**
     *  StretchedVSineWave Function
     */

    protected static class StretchedVSineWave extends Functions {

        public double callFunction(ArrayList<Double> array, int numbers) {
            long start = System.nanoTime();
            double s = IntStream.range(0, numbers - 1).mapToDouble(i -> (Math.pow((Math.pow(array.get(i), 2) + Math.pow(array.get(i + 1), 2)), .25) * Math.pow(Math.sin((50 * Math.pow((Math.pow(array.get(i), 2) + Math.pow(array.get(i + 1), 2)), .1))), 2) + 1)).sum();
            time += System.nanoTime() - start;
            return s;
        }

        public double[] range() {
            double[] r = {-30,30};
            return r;
        }

        public String name(){
            return "Stretched V Sine Wave";
        }
    }

    /**
     *  AckeysOneFunction
     */

    protected static class AckeysOne extends Functions {

        public double callFunction(ArrayList<Double> array, int numbers) {
            long start = System.nanoTime();
            double s = IntStream.range(0, numbers - 1).mapToDouble(i -> (1 / Math.pow((Math.E), .2)) * Math.sqrt(Math.pow(array.get(i), 2) + Math.pow(array.get(i + 1), 2)) + 3 * (Math.cos(2 * array.get(i)) + Math.sin(2 * array.get(i + 1)))).sum();
            time += System.nanoTime() - start;
            return s;
        }

        public double[] range() {
            double[] r = {-32, 32};
            return r;
        }

        public String name() {
            return "Ackey's One";
        }
    }

    /**
     *  AckeysTwo Function
     */

    protected static class AckeysTwo extends Functions {

        public double callFunction(ArrayList<Double> array, int numbers) {
            long start = System.nanoTime();
            double s = IntStream.range(0, numbers - 1).mapToDouble(i -> 20 + Math.E - (20 / (Math.pow(Math.E, .2) * Math.sqrt((Math.pow(array.get(i), 2) + Math.pow(array.get(i + 1), 2)) / 2))) - Math.pow(Math.E, 0.5 * (Math.cos(2 * Math.PI * array.get(i)) + Math.cos(2 * Math.PI * array.get(i + 1))))).sum();
            time += System.nanoTime() - start;
            return s;
        }

        public double[] range() {
            double[] r = {-32,32};
            return r;
        }

        public String name(){
            return "Ackey's Two";
        }
    }

    /**
     *  EggHolder Function
     */

    protected static class EggHolder extends Functions {

        public double callFunction(ArrayList<Double> array, int numbers) {
            long start = System.nanoTime();
            double s = IntStream.range(0, numbers - 1).mapToDouble(i -> -array.get(i) * Math.sin(Math.sqrt(Math.abs(array.get(i) - array.get(i + 1) - 47))) - (array.get(i + 1) + 47) * Math.sin(Math.sqrt(Math.abs(array.get(i + 1) + 47 + (array.get(i) / 2))))).sum();
            time += System.nanoTime() - start;
            return s;
        }

        public double[] range() {
            double[] r = {-500,500};
            return r;
        }

        public String name(){
            return "Egg Holder";
        }
    }

    /**
     *  Griewangk Function
     */

    protected static class Griewangk extends Functions {

        public double callFunction(ArrayList<Double> array, int numbers) {
            long start = System.nanoTime();
            double s = 0.0;
            double p = 1.0;
            double I = 1.0;
            int i = 0;
            while (i < numbers) {
                s += (Math.pow(array.get(i),2)/4000);
                p = p * Math.cos((array.get(i)/(Math.sqrt(I))));
                I++;
                i++;
            }
            s = 1 + s - p;
            time += System.nanoTime() - start;
            return s;
        }

        public double[] range() {
            double[] r = {-500,500};
            return r;
        }

        public String name(){
            return "Griewangk";
        }
    }

    /**
     *  Rana Function
     */

    protected static class Rana extends Functions {

        public double callFunction(ArrayList<Double> array, int numbers) {
            long start = System.nanoTime();
            double s = IntStream.range(0, numbers - 1).mapToDouble(i -> array.get(i) * Math.sin(Math.sqrt(Math.abs(array.get(i + 1) - array.get(i) + 1))) * Math.cos(Math.sqrt(Math.abs(array.get(i + 1) + array.get(i) + 1))) + (array.get(i + 1) + 1) * Math.cos(Math.sqrt(Math.abs(array.get(i + 1) - array.get(i) + 1))) * Math.sin(Math.sqrt(Math.abs(array.get(i + 1) + array.get(i) + 1)))).sum();
            time += System.nanoTime() - start;
            return s;
        }

        public double[] range() {
            double[] r = {-500,500};
            return r;
        }

        public String name(){
            return "Rana";
        }
    }

    /**
     *  PathologicalFunction
     */

    protected static class  Pathological extends Functions {

        public double callFunction(ArrayList<Double> array, int numbers) {
            long start = System.nanoTime();
            double s = IntStream.range(0, numbers - 1).mapToDouble(i -> 0.5 + (Math.pow(Math.sin(Math.sqrt(100 * Math.pow(array.get(i), 2) + Math.pow(array.get(i + 1), 2))), 2) - 0.5) / (1 + 0.001 * Math.pow((Math.pow(array.get(i), 2) - 2 * array.get(i) * array.get(i + 1) + Math.pow(array.get(i + 1), 2)), 2))).sum();
            time += System.nanoTime() - start;
            return s;
        }

        public double[] range() {
            double[] r = {-100,100};
            return r;
        }

        public String name(){
            return "Pathological";
        }
    }

    /**
     *  Michalewicz Function
     */

    protected static class Michalewicz extends Functions {

        public double callFunction(ArrayList<Double> array, int numbers) {
            long start = System.nanoTime();
            double s = IntStream.range(0, numbers).mapToDouble(i -> Math.sin(array.get(i)) * Math.pow((Math.sin((i * Math.pow(array.get(i), 2)) / Math.PI)), 10)).sum();
            s = -s;
            time += System.nanoTime() - start;
            return s;
        }

        public double[] range() {
            double[] r = {0,Math.PI};
            return r;
        }

        public String name(){
            return "Michalewicz";
        }
    }


    /**
     *  MastersCosineWave Function
     */

    protected static class MastersCosineWave extends Functions {

        public double callFunction(ArrayList<Double> array, int numbers) {
            long start = System.nanoTime();
            double s = IntStream.range(0, numbers - 1).mapToDouble(i -> Math.pow(Math.E, (-(1 / 8) * (Math.pow(array.get(i), 2) + Math.pow(array.get(i + 1), 2) + 0.5 * array.get(i + 1) * array.get(i)))) * Math.cos(4 * Math.sqrt(Math.pow(array.get(i), 2) + Math.pow(array.get(i + 1), 2) + 0.5 * array.get(i + 1) * array.get(i)))).sum();
            s = -s;
            time += System.nanoTime() - start;
            return s;
        }

        public double[] range() {
            double[] r = {-30,30};
            return r;
        }

        public String name(){
            return "Master's Cosine Wave";
        }
    }


}