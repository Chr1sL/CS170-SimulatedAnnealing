import java.util.HashMap;
import java.util.*;

public class randNew {

    /**
     * Loops through random inputs and finds the most optimal happiness.
     */
    public static ArrayList<ArrayList<Integer>> compare(double smax, int n, HashMap<Integer, double[]> pairs) {

        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>> ();
        for (int i = 0; i < n; i ++){
            ArrayList<Integer> values = new ArrayList<>();
            values.add(i);
            values.add(i);
            result.add(values);
        }

        return result;
    }

}
