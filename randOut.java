import java.util.*;

public class randOut{

    public static HashMap<Integer, Integer> _pairings;

    public static Integer _k_rooms;

    public static void main(String []args){

    }

    // failure, return -1
    public static double s(double smax, int n, HashMap<Integer, double[]> pairs) {
        if (n <= 0) {
            return -1;
        }
        ArrayList<Integer> order = shuffle(n); // random ordering of elems
        ArrayList<ArrayList<Integer>> rooms = new ArrayList<>(); // breakout rooms
        HashMap<Integer, Double> level = new HashMap<>(); //maps rooms->stress
        int totalHappiness = 0;

        int k = n + 1;
        while (k > n) {
            k = (int)(Math.random() * n) + 1;
        }

        double stress = smax / (double) k;

        for (int i = 0; i < k; i++) {
            ArrayList<Integer> start = new ArrayList<>(); //temporary starter list
            start.add(i);
            rooms.add(start); // creates k breakout rooms
            level.put(i, 0.0);
        }

        rooms.get(0).add(order.get(0));
        for (int i = 1; i < n; i++) { // iterate thru all elems
            double hMax = -1;
            double addStress = -1;
            int addRoom = -1;
            for (int j = 0; j < k; j++) { // iterate thru all rooms
                double currHappy = 0;
                double currStress = 0;
                if (rooms.get(j).size() > 1) {
                    for (int t = 1; t < rooms.get(j).size(); t++) { //calculate stress and happiness added
                        double[] stressHappy = new double[2];
                        int first = pairRep(order.get(i), rooms.get(j).get(t));
                        int second = pairRep(rooms.get(j).get(t), order.get(i));

                        if (pairs.containsKey(first)) {
                            stressHappy = (double[]) pairs.get(first);

                        } else {
                            stressHappy = (double[]) pairs.get(second);
                        }
                        currHappy += stressHappy[0];
                        currStress += stressHappy[1];
                    }
                }
                if ((currHappy > hMax) && (level.get(j) + currStress <= stress)) {
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
        

        for (int i = 0; i < rooms.size(); i++) {
            if (stress < stressVal(pairs, rooms.get(i))) {
                return -1;
            }
        }
        

        persist(rooms);
        return totalHappiness;
    }


    // elem1 = 10, elem2 = 27 -> key = 100027
    public static HashMap<Integer, double[]> hash(ArrayList<ArrayList<Float>> input) {
        HashMap<Integer, double[]> map = new HashMap<>();
        double[] sh = new double[2];
        for (int i = 0; i < input.size(); i++) {
            int elem1 = (Math.round(input.get(i).get(0)));
            int elem2 = (Math.round(input.get(i).get(1)));
            double happy = (double) input.get(i).get(2);
            double stress = (double) input.get(i).get(3);
            sh[0] = happy;
            sh[1] = stress;
            map.put(pairRep(elem1, elem2), sh);
        }
        return map;
    }

    // represents elem pairs as: elem1 = 10, elem2 = 27 -> key = 100027
    public static int pairRep(int elem1, int elem2) {
        String a = "";
        String e1 = Integer.toString(elem1);
        String e2 = Integer.toString(elem2);
        return Integer.parseInt(elem1 + "00" + elem2);
    }


    public static ArrayList<Integer> shuffle(int n) {
        ArrayList<Integer> s = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            s.add(i);
        }
        Collections.shuffle(s);
        return s;
    }

    public static void persist(ArrayList<ArrayList<Integer>> rooms) {
        HashMap<Integer, Integer> pairs = new HashMap<>();
        for (int i = 0; i < rooms.size(); i++) {
            for (int j = 1; j < rooms.get(i).size(); j++) {
                pairs.put(rooms.get(i).get(j), rooms.get(i).get(0));
            }
        }
        _pairings = pairs;
        _k_rooms = rooms.size();
    }

    /**
     * Loops through random inputs and finds the most optimal happiness.
     */
    public static ArrayList<ArrayList<Integer>> compare(double smax, int n, HashMap<Integer, double[]> pairs) {
        double temp = s(smax, n, pairs);
        HashMap<Integer, Integer> optimal = _pairings;

        int change = 100;
        //int change = (int) Math.round((factorial(n)/(factorial(_k_rooms)*factorial(n - _k_rooms)))*0.9);

            while(change != 0) {
            double temp_two = -1;
            while (temp_two == -1) {
                temp_two = s(smax, n, pairs);
            }
            if (temp_two > temp) {
                temp = temp_two;
                optimal = _pairings;
                change = 100;
            } else {
                change--;
            }
        }
        Set<Integer> key = optimal.keySet(); //keys (people)
        ArrayList<Integer> keys = new ArrayList<> (key);
        Collection<Integer> value = optimal.values(); //values
        ArrayList<Integer> values = new ArrayList<> (value);
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>> ();
        for (int i = 0; i < keys.size(); i++) {
            ArrayList<Integer> pair = new ArrayList<>();
            pair.add(keys.get(i));
            pair.add(values.get(i));
            result.add(pair);
        }
        return result;
    }

    public static int factorial(int n) {
        int result = 1;

        int count = 1;
        while (count <= n) {
            result *= count;
            count++;
        }
        return result;
    }

    public static int stressVal(HashMap<Integer, double[]> map, ArrayList<Integer> rooms) {
        int stress = 0;
        double[] sh = new double[2];
        for (int i = 1; i < rooms.size(); i++) {
            for (int j = 1; j < i; j++) {
                if (map.containsKey(pairRep(rooms.get(i), rooms.get(j)))) {
                    sh = map.get(pairRep(rooms.get(i), rooms.get(j)));
                } else {
                    sh = map.get(pairRep(rooms.get(j), rooms.get(i)));
                }
                stress += sh[1];
            }
        }
        return stress;
    }
/*
    public static ArrayList randomK(int n) {
        ArrayList<Integer> s = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            s.add(i);
        }
        for (int i = 1; i < n / 2; i++) {
            s.add(i);
            s.add(i);
        }
        return s;
    }


    public static int randomizer(int n) {
        if (n == 10) {
            Collections.shuffle(_ten);
            return _ten.get(0);
        } if (n == 20) {
            Collections.shuffle(_twenty);
            return _twenty.get(0);
        } else {
            Collections.shuffle(_fif);
            return _fif.get(0);
        }
    }
    */
//    public static ArrayList<ArrayList<Integer>> compare(double smax, int n, HashMap<List<Integer>, List<Float>> pairs) {
//        double temp = s(smax, n, pairs);
//        HashMap<Integer, Integer> optimal = _pairings;
//        int change = 5;
//        while(change != 0) {
//            double temp_two = s(smax, n, pairs);
//            if (temp_two > temp) {
//                temp = temp_two;
//                optimal = _pairings;
//                change = 5;
//            } else {
//                change--;
//            }
//        }
//
//        Set<Integer> key = optimal.keySet(); //keys (people)
//        ArrayList<Integer> keys = new ArrayList<> (key);
//        Collection<Integer> value = optimal.values(); //values
//        ArrayList<Integer> values = new ArrayList<> (value);
//        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>> ();
//        for (int i = 0; i < n; i++) {
//            ArrayList<Integer> pair = new ArrayList<>();
//            pair.add(keys.get(i));
//            pair.add(values.get(i));
//            result.add(pair);
//        }
//        return result;
//    }

}