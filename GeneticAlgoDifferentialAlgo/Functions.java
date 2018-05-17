import java.util.ArrayList;

public abstract class Functions {

    public long time = 0;

    public abstract double callFunction(ArrayList<Double> array, int numbers);
    public abstract double[] range();
    public abstract String name();

    protected long getTime(){
        return time;
    }
}