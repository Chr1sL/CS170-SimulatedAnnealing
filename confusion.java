import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.*;
import java.util.*;

public class confusion {
    public static void main(String... args) {
        File read = new File("results.txt");

        if (!read.isFile()){
            throw new IllegalArgumentException("must be a normal file");
        }
        try {

            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(read.getName())));
            Collections.sort(lines);
            File ordered = new File("Orderedresults.txt");
            ordered.createNewFile();
            FileWriter fw = new FileWriter("Orderedresults.txt",false);
            Iterator<String> it = lines.iterator();

            while(it.hasNext()) {
                String line = it.next();
                String printThis = "";
                if (line.contains("Invalid") || line.contains("0.0")) {
                    String newFile = line.substring(0 , line.indexOf(":"));
                    File source = new File("inputs/" + newFile + ".in");
                    if (!source.isFile()){
                        System.out.println("source not file");
                    }
                    File target = new File("redo/" + newFile + ".in");
                    target.createNewFile();
                    if (!target.isFile()){
                        System.out.println("target not file");
                    }
                    System.out.println(source.getName());
                    System.out.println(target.getName());

                    Files.copy(Paths.get("inputs/" + newFile + ".in"), Paths.get("redo/" + newFile + ".in"), REPLACE_EXISTING);


                    if (it.hasNext()) {
                        fw.write(line + "\n");
                    } else {
                        fw.write(printThis);
                    }
                }
            }

            fw.close();
        }
        catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
