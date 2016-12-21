import java.util.LinkedHashMap;

public class ReferenceMonitor {
    private ObjectManager objectManager;
    private SubjectManager subjectManager;
    private InstructionObject inst;
    private StringBuilder sb;
    private LinkedHashMap<LowerCaseString, SecurityLevel> subjectLevel = new LinkedHashMap<>();
    private LinkedHashMap<LowerCaseString, SecurityLevel> objectLevel = new LinkedHashMap<>();

    public ReferenceMonitor () {
        objectManager = new ObjectManager();
        subjectManager = new SubjectManager();
        inst = null;
        sb = new StringBuilder();
    }

    public void createSubject(String name, SecurityLevel sl) {
        if (!subjectLevel.containsKey(new LowerCaseString(name))) {
            subjectManager.create(name);
            subjectLevel.put(new LowerCaseString(name), sl);
        }
    }


    public void createObject(String name, SecurityLevel sl) {
        if (!objectLevel.containsKey(new LowerCaseString(name))) {
            objectLevel.put(new LowerCaseString(name), sl);
            objectManager.createNewObject(name);
        }
    }


    public void setNewInstruction(InstructionObject newInstruction) {
        inst = newInstruction;
        sb = new StringBuilder();
        sb.append('\n');
    }

    public void execute() {
        if (inst.getCommand().equals(Manager.READ)) {
            executeRead();
        } else if (inst.getCommand().equals(Manager.WRITE)) {
            executeWrite();
        } else {
            sb.append("Bad Instruction\n");
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

        // output
        sb.append(subject);
        sb.append(" reads ");
        sb.append(object);
        sb.append('\n');
    }

    private void executeWrite() {
        String subject = inst.getSubject();
        String object = inst.getObject();
        int value = inst.getValue();

        if (objectLevel.get(new LowerCaseString(object)).dominates(subjectLevel.get(new LowerCaseString(subject)))) {
            objectManager.WRITE(object, value);
        }

        sb.append(subject);
        sb.append(" writes value ");
        sb.append(value);
        sb.append(" to ");
        sb.append(object);
        sb.append('\n');
    }

    public boolean verifySubjectName(String name) {
        return subjectLevel.containsKey(new LowerCaseString(name));
    }

    public boolean verifyObjectName(String name) {
        return objectLevel.containsKey(new LowerCaseString(name));
    }

    public String outputString() {
        sb.append("The current state is:\n");
        for (LowerCaseString k : objectLevel.keySet()) {
            sb.append("   ");
            sb.append(k);
            sb.append(" has value: ");
            sb.append(objectManager.READ(k.toString()));
            sb.append('\n');
        }

        for (LowerCaseString k : subjectLevel.keySet()) {
            sb.append("   ");
            sb.append(k);
            sb.append(" has recently read: ");
            sb.append(subjectManager.getTemp(k.toString()));
            sb.append('\n');
        }
        return sb.toString();
    }
}
