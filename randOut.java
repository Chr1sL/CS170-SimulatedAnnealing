import java.util.*;

public class randOut{

     public static HashMap _pairings;

     public static void main(String []args){
        
     }
     
     
     // failure, return -1
     public double s(double smax, int n, HashMap pairs) {
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
                    for (int t = 1; t < rooms.get(j).size(); t++) {//calculate stress and happiness added
                        double[] first = new double[]{i, rooms.get(j).get(t)};
                        double[] second = new double[]{rooms.get(j).get(t), i};
                        double[] stressHappy = new double[2];
                        if (pairs.containsKey(first)) {
                            stressHappy = (double[]) pairs.get(first);
                        } else {
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
             rooms.get(addRoom).add(i);
             level.put(addRoom, addStress);
             totalHappiness += hMax;
         }


         return totalHappiness;
     }
     

     
     public ArrayList shuffle(int n) {
        ArrayList<Integer> s = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            s.add(i);
        }
        Collections.shuffle(s);
        return s;
     }

     public void persist(ArrayList<ArrayList<Integer>> rooms) {
        HashMap<Integer, Integer> pairs = new HashMap<>();
        for (int i = 1; i < rooms.size(); i++) {
            for (int j = 1; j < rooms.get(i).size(); j++) {
                pairs.put(rooms.get(i).get(j), i);
            }
        }
        _pairings = pairs;
     }
     
}