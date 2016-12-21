import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by thanhnguyencs on 6/8/15.
 */
public class SubjectManager {

    private static TreeMap<String, Integer> subjects = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public void create(String name) {
        subjects.put(name, 0);
    }

    public void setTemp(String name, int value) {
        subjects.put(name, value);
    }

    public int getTemp(String name) {
        return subjects.get(name);
    }

    public boolean contains(String name) {
        return subjects.containsKey(name);
    }
}
