import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * This class is only used for generating ASM code.
 *
 *
 * Created by Yifan on 10/29/16.
 */
public class ASMified {

    private static void collect(){
        Path path = Paths.get("raw_result");
        try (PrintWriter out = new PrintWriter(Files.newBufferedWriter
                (path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)))
        {
            out.print(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("file error");
        }
    }



    public int add(int a, int b){
        int c = a + b;
        return c;
    }
}
