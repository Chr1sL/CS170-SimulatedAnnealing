import java.util.*;

public class randOut{

     public static HashMap _pairings;

     public static void main(String []args){
        
     }

     // failure, return -1
     public static double s(double smax, int n, HashMap pairs) {
         ArrayList<Integer> order = shuffle(n); // random ordering of elems
         ArrayList<ArrayList<Integer>> rooms = new ArrayList<>(); // breakout rooms
         HashMap<Integer, Double> level = new HashMap<>(); //maps rooms->stress
         int totalHappiness = 0;
         
         int k = (int)(Math.random() * n) / (int) 1;
         double stress = smax / (double) k;
         
         for (int i = 0; i < k; i++) {
             ArrayList<Integer> start = new ArrayList<>(); //temporary starter list
             start.add(i);
             rooms.add(start); // creates k breakout rooms
             level.put(i, 0.0);
         }
         
         rooms.get(0).add(0);
         for (int i = 1; i < n; i++) { // iterate thru all elems
             double hMax = -1;
             double addStress = -1;
             int addRoom = -1;
             for (int j = 0; j < k; j++) { // iterate thru all rooms
                double currHappy = 0;
                double currStress = 0;
                if (rooms.get(j).size() > 1) {
                    for (int t = 1; t < rooms.get(j).size(); t++) { //calculate stress and happiness added
                        double[] first = new double[]{order.get(i), rooms.get(j).get(t)};
                        double[] second = new double[]{rooms.get(j).get(t), order.get(i)};
                        double[] stressHappy = new double[2]; //(double[]) pairs.get(second)
                        if (pairs.containsKey(first)) {
                            stressHappy = (double[]) pairs.get(first);
                        }
                        else {
                            stressHappy = (double[]) pairs.get(second);
                        }
                        currStress += stressHappy[0];
                        currHappy += stressHappy[1];
                    }
                }
                if (currHappy > hMax && level.get(j) + currStress <= stress) {
                    hMax = currHappy;
                    addRoom = j;
                    addStress = level.get(j) + currStress;
                }
             }
             if (addRoom == -1) {
                 return -1;
             }
             rooms.get(addRoom).add(order.get(i));
             level.put(addRoom, addStress);
             totalHappiness += hMax;
         }
         persist(rooms);
         return totalHappiness;
     }
     

     
     public static ArrayList shuffle(int n) {
        ArrayList<Integer> s = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            s.add(i);
        }
        Collections.shuffle(s);
        return s;
     }

     public static void persist(ArrayList<ArrayList<Integer>> rooms) {
        HashMap<Integer, Integer> pairs = new HashMap<>();
        for (int i = 1; i < rooms.size(); i++) {
            for (int j = 1; j < rooms.get(i).size(); j++) {
                pairs.put(rooms.get(i).get(j), i);
            }
        }
        _pairings = pairs;
     }

    /**
     * Loops through random inputs and finds the most optimal happiness.
     */
    public static ArrayList compare(double smax, int n, HashMap pairs) {
        double temp = s(smax, n, pairs);
        HashMap optimal = _pairings;
        int change = 5;
        while(change != 0) {
            double temp_two = s(smax, n, pairs);
            if (temp_two > temp) {
                temp = temp_two;
                optimal = _pairings;
                change = 5;
            } else {
                change --;
            }
        }

        Set<Float> key = optimal.keySet(); //keys (people)
        ArrayList<Float> keys = new ArrayList<Float> (key);
        Collection<Float> value = optimal.values(); //values
        ArrayList<Float> values = new ArrayList<Float> (value);
        ArrayList<ArrayList<Float>> result = new ArrayList<ArrayList<Float>> ();
        for (int i = 0; i < n; i++) {
            ArrayList<Float> pair = new ArrayList<Float>();
            pair.add(keys.get(i));
            pair.add(values.get(i));
            result.add(pair);
        }
        return result;
    }
     
}