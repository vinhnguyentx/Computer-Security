import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class ReferenceMonitor {
    private ObjectManager objectManager;
    private SubjectManager subjectManager;
    private InstructionObject inst;
    private PrintWriter output;
    private boolean logEnable;
    private LinkedHashMap<LowerCaseString, SecurityLevel> subjectLevel = new LinkedHashMap<>();
    private LinkedHashMap<LowerCaseString, SecurityLevel> objectLevel = new LinkedHashMap<>();

    public ReferenceMonitor () {
        objectManager = new ObjectManager();
        subjectManager = SubjectManager.getSingleton();
        inst = null;
        output = null;
        logEnable = false;
    }

    public void createSubject(String name, SecurityLevel sl) {
        if (!subjectLevel.containsKey(new LowerCaseString(name))) {
            subjectManager.create(name);
            subjectLevel.put(new LowerCaseString(name), sl);
        }
    }

    public void setLogOutput(PrintWriter out) {
        output = out;
        logEnable = true;
    }

    public void createObject(String name, SecurityLevel sl) {
        if (!objectLevel.containsKey(new LowerCaseString(name))) {
            objectLevel.put(new LowerCaseString(name), sl);
            objectManager.createNewObject(name);
        }
    }


    public void setNewInstruction(InstructionObject newInstruction) {
        inst = newInstruction;
        if (logEnable) {
            output.println(inst.toString());
        }
        execute();

    }

    public void execute() {
        if (inst.getCommand().equals(Manager.READ)) {
            executeRead();
        } else if (inst.getCommand().equals(Manager.WRITE)) {
            executeWrite();
        } else if (inst.getCommand().equals(Manager.CREATE)) {
            executeCreate();
        } else if (inst.getCommand().equals(Manager.DESTROY)) {
            executeDestroy();
        } else if (inst.getCommand().equals(Manager.RUN)) {
            executeRun();
        } else {
            // bad instruction
        }
    }

    private void executeRun() {
        if (inst.getSubject().equals("lyle")) {
            subjectManager.runLyle();
        } else if (inst.getSubject().equals("hal")) {
            // no use
        } else {
            // no ops
        }

    }

    private void executeRead() {
        String subject = inst.getSubject();
        String object = inst.getObject();

        if (subjectLevel.get(new LowerCaseString(subject)).dominates(objectLevel.get(new LowerCaseString(object)))) {
            subjectManager.setTemp(subject, objectManager.READ(object));
        } else {
            subjectManager.setTemp(subject, 0);
        }
    }

    private void executeWrite() {
        String subject = inst.getSubject();
        String object = inst.getObject();
        int value = inst.getValue();

        if (objectLevel.get(new LowerCaseString(object)).dominates(subjectLevel.get(new LowerCaseString(subject)))) {
            objectManager.WRITE(object, value);
        }
    }

    private void executeCreate() {
        String subject = inst.getSubject();
        String object = inst.getObject();
        SecurityLevel l = subjectLevel.get(new LowerCaseString(subject));

        if (!objectLevel.containsKey(new LowerCaseString(object))) {
            objectManager.CREATE(object);
            objectLevel.put(new LowerCaseString(object), l);
        }
    }

    private void executeDestroy() {
        String subject = inst.getSubject();
        String object = inst.getObject();

        if (objectLevel.get(new LowerCaseString(object)).dominates(subjectLevel.get(new LowerCaseString(subject)))) {
            objectManager.DESTROY(object);
            objectLevel.remove(new LowerCaseString(object));
        }
    }

    public boolean verifySubjectName(String name) {
        return subjectLevel.containsKey(new LowerCaseString(name));
    }

    public boolean verifyObjectName(String name) {
        return objectLevel.containsKey(new LowerCaseString(name));
    }

    // object Manager class
    public class ObjectManager {

        private TreeMap<String, Integer> objects = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        private ObjectManager() {}

        public void createNewObject(String name) {
            objects.put(name, 0);
        }

        public int READ(String name) {
            return objects.get(name);
        }

        public void WRITE(String name, int v) {
            objects.put(name, v);
        }

        public void CREATE(String name) {
            objects.put(name, 0);
        }

        public void DESTROY(String name) {
            objects.remove(name);
        }
    }
}
