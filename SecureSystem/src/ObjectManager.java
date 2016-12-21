import java.util.HashMap;
import java.util.TreeMap;

public class ObjectManager {

    private static TreeMap<String, Integer> objects = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public void createNewObject(String name) {
        objects.put(name, 0);
    }

    public int READ(String name) {
        return objects.get(name);
    }

    public void WRITE(String name, int v) {
        objects.put(name, v);
    }

    public boolean contains(String name) {
        return objects.containsKey(name);
    }
}
