import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Main {

    /** Return contents of FILE as a String.
     *  FILE is normal file
     */
    static ArrayList<String> read(File file){
        if (!file.isFile()){
            throw new IllegalArgumentException("must be a normal file");
        }
        try {
            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("inputs/" + file.getName())));
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

    /** Filter out all but plain files. */
    private static FilenameFilter PLAIN_FILES =
        new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isFile();
            }
        };

    // key = [elem1, elem2], value = [stress, happiness]
    public static HashMap hash(ArrayList<ArrayList<Float>> pairs) {
        HashMap<Float[], Float[]> map = new HashMap<>();
        Float[] p = new Float[2];
        Float[] s = new Float[2];
        for (int i = 0; i < pairs.size(); i++) {
            p[0] = pairs.get(i).get(0); // first elem
            p[1] = pairs.get(i).get(1); // second elem
            s[0] = pairs.get(i).get(2); // stress
            s[1] = pairs.get(i).get(3); // happiness
            map.put(p, s); // maps pairs to stress and happiness vals
        }
        return map;
    }

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) {
        File inputs = new File("inputs");
        if (!inputs.isDirectory()) {
            throw new IllegalArgumentException("inputs must be directory");
        }
        File[] files = inputs.listFiles(PLAIN_FILES);
        Arrays.sort(files);
        if (files == null) {
            return;
        } else {
            for (File file : files) {
                String name = file.getName().replaceFirst("[.][^.]+$", "");

                ArrayList<String> lines = read(file);
                int number = Integer.parseInt(lines.remove(0).replaceAll(" ", ""));
                float max = Float.parseFloat(lines.remove(0).replaceAll(" ", ""));

                ArrayList<ArrayList<Float>> finalInput = new ArrayList<ArrayList<Float>>();
                for (String line: lines) {
                    ArrayList<Float> numLines = new ArrayList<Float>();
                    String[] liner = line.split(" ");
                    for (String num: liner){
                        numLines.add(Float.parseFloat(num));
                    }
                    finalInput.add(numLines);
                }

                HashMap inputHash = hash(finalInput);
//                ArrayList<ArrayList<Float>> newLines = randOut.compare(number, max, inputHash);

//                write(name, newLines);

                /** remove this when test done: **/
                write(name, finalInput);
//                break;
                /*********************************/
            }
            return;
        }

    }

}