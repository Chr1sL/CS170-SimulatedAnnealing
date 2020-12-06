import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Main {
    /** Global OPTIMAL - starts off as initial random groupings. */
    public static ArrayList<ArrayList<Float>> optimal = new ArrayList<>();

    /** Return contents of FILE as a String.
     *  FILE is normal file
     */
    static ArrayList<String> read(File file){
        if (!file.isFile()){
            throw new IllegalArgumentException("must be a normal file");
        }
        try {
            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(file.getName())));
            return lines;
        }
        catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /** Write into NAME file CONTENT to end of file.
     *  file with name NAME
     */
    static void write(String name, ArrayList<ArrayList<Float>> content){
        try {
            String filename= "Phase2_outputs/" + name + ".out";
            File myObj = new File(filename);

            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }


            FileWriter fw = new FileWriter(filename,true);

            Iterator<ArrayList<Float>> it = content.iterator();
            while(it.hasNext()) {
                ArrayList<Float> line = it.next();
                String printThis = "";
                int i = 0;
                for (Float num: line){
                    if (i < 2) {
                        printThis += Integer.toString(Math.round(num)) + " ";
                    } else {
                        printThis += Float.toString(num) + " ";
                    }
                    i++;
                }
                printThis = printThis.substring(0, printThis.length() - 1);
                if (it.hasNext()) {
                    fw.write(printThis + "\n");
                } else {
                    fw.write(printThis);
                }
            }
            
            fw.close();

        } catch (IOException excp) {
            throw new IllegalArgumentException(excp.getMessage());
        }

    }

    /** Compare CONTENT to OPTIMAL.
     *  Return void. Update OPTIMAL if necessary.
     */
    static void comparator(ArrayList<ArrayList<Float>> content) {
        //im so tired and
    }

    /** Filter out all but plain files. */
    private static FilenameFilter PLAIN_FILES =
        new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isFile();
            }
        };

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) {
        File inputs = new File("inputs");
        if (!inputs.isDirectory()) {
            throw new IllegalArgumentException("inputs must be directory");
        }
        String[] files = inputs.list(PLAIN_FILES);
        if (files == null) {
            return;
        } else {


            /** THIS IS JUST A TEST:
             *  Test currently writes parses pairs from .in and writes into .out
             * ***/

            File file = new File("50.in");
            String name = file.getName().replaceFirst("[.][^.]+$", "");

            ArrayList<String> lines = read(file);
            int number = Integer.parseInt(lines.remove(0));
            float max = Float.parseFloat(lines.remove(0));

            ArrayList<ArrayList<Float>> finalInput = new ArrayList<ArrayList<Float>>();
            for (String line: lines) {
                ArrayList<Float> numLines = new ArrayList<Float>();
                String[] liner = line.split(" ");
                for (String num: liner){
                    numLines.add(Float.parseFloat(num));
                }
                finalInput.add(numLines);
            }

            write(name, finalInput);
            return;
            /**************************/

//            for (File file : files) {
//                String name = file.getName().replaceFirst("[.][^.]+$", "");
//
//                ArrayList<String> lines = read(file);
//                int number = Integer.parseInt(lines.remove(0));
//                float max = Float.parseFloat(lines.remove(0));
//
//                ArrayList<ArrayList<Float>> finalInput = new ArrayList<ArrayList<Float>>();
//                for (String line: lines) {
//                    ArrayList<Float> numLines = new ArrayList<Float>();
//                    String[] liner = line.split(" ");
//                    for (String num: liner){
//                        numLines.add(Float.parseFloat(num));
//                    }
//                    finalInput.add(numLines);
//                }

            /**
             *  Run = placeholder name of class
             *      at the moment, i assume that it will return an ArrayList<ArrayList<Float>>,
             *      but feel free to change that to something else that is iterable (though, should probably keep
             *      it a float since they're floats
             *  finalInput = ArrayList<ArrayList<Float>> of pairings
             *  number = # of students
             *  max = max happiness
             *  test.calc() = instance method name of actual program that will find output
             */

//                Run test = new Run(finalInput, number, max);
//                ArrayList<ArrayList<Float>> newLines = test.calc();
//                write(newLine, name);
//                return;
//            }
        }

    }

}