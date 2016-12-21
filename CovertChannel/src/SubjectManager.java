import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

public class SubjectManager {

    private static TreeMap<String, Integer> subjects = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private static final SubjectManager singleton = new SubjectManager();
    private FileOutputStream out;
    private int b;
    private int countBits;

    private SubjectManager() {
        b = 0;
        countBits = 0;
    }
    public static SubjectManager getSingleton() {
        return singleton;
    }

    public void setOutputFile(FileOutputStream out) {
        this.out = out;
    }
    public void create(String name) {
        subjects.put(name, 0);
    }

    public void setTemp(String name, int value) {
        subjects.put(name, value);
    }

    public void runHal() {
        // no use
    }

    public void runLyle() {

        if (subjects.get("lyle").byteValue() == 0) {
           b = b | (1 << countBits);
        }
        ++countBits;
        if (countBits == 8) {
            try {
                out.write(b);
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("something is wrong with the byte variable");
            }
            countBits = 0;
            b = 0;
        }
    }

    public void setOutput(FileOutputStream output) {
        this.out = output;
    }
}
