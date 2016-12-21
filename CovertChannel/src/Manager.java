import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Manager {
    // constant
    public static final String READ = "read";
    public static final String WRITE = "write";
    public static final String BAD = "bad";
    public static final String CREATE = "create";
    public static final String DESTROY = "destroy";
    public static final String RUN = "run";

    // default values for covert channel
    private final int defaultV = 1;
    private final String hal = "hal";
    private final String lyle = "lyle";
    private final String obj = "object";

    private ReferenceMonitor referenceMonitor;


    public Manager() {
        referenceMonitor = new ReferenceMonitor();
    }


    public void createNewSubject(String name, SecurityLevel sl) {
        referenceMonitor.createSubject(name, sl);
    }

    public void runCovertChannel(int b) {
        if (b % 2 == 0) {
            runLyle();
        } else {
//            CREATE HAL OBJ
            referenceMonitor.setNewInstruction(InstructionObject.createCreateInstance(Manager.CREATE, hal, obj));
            runLyle();
        }
    }

    private void runLyle() {
//        CREATE LYLE OBJ
//        WRITE LYLE OBJ 1
//        READ LYLE OBJ
//        DESTROY LYLE OBJ
//        RUN LYLE
        referenceMonitor.setNewInstruction(InstructionObject.createCreateInstance(Manager.CREATE, lyle, obj));
        referenceMonitor.setNewInstruction(InstructionObject.createWriteInstance(Manager.WRITE, lyle, obj, defaultV));
        referenceMonitor.setNewInstruction(InstructionObject.createReadInstance(Manager.READ, lyle, obj));
        referenceMonitor.setNewInstruction(InstructionObject.createDestroyInstance(Manager.DESTROY, lyle, obj));
        referenceMonitor.setNewInstruction(InstructionObject.createRunInstance(Manager.RUN, lyle));



    }

    public void setOutput(FileOutputStream out) {
        SubjectManager.getSingleton().setOutput(out);
    }

    public void setLogOutput(PrintWriter out) {
        referenceMonitor.setLogOutput(out);
    }


//    public void createNewObject(String name, SecurityLevel sl) {
//        referenceMonitor.createObject(name, sl);
//    }


//    public void verifyAndRun(String line) {
//        StringTokenizer st = new StringTokenizer(line);
//        int num = st.countTokens();
//        String command = st.nextToken().toLowerCase();
//
//        if (num == 3) {
//            String str1 = st.nextToken().toLowerCase();
//            String str2 = st.nextToken().toLowerCase();
//            if (command.equals(Manager.READ) && verifyName(str1, str2)) {
//                referenceMonitor.setNewInstruction(InstructionObject.createReadInstance(command, str1, str2));
//            } else {
//                referenceMonitor.setNewInstruction(InstructionObject.BadInstruction);
//            }
//        } else if (num == 4) {
//            String str1 = st.nextToken().toLowerCase();
//            String str2 = st.nextToken().toLowerCase();
//            try {
//                int v = new Integer(st.nextToken());
//                if (command.equals(Manager.WRITE) && verifyName(str1, str2)) {
//                    referenceMonitor.setNewInstruction(InstructionObject.createWriteInstance(command, str1, str2, v));
//                } else {
//                    referenceMonitor.setNewInstruction(InstructionObject.BadInstruction);
//                }
//            } catch (NumberFormatException e) {
//                referenceMonitor.setNewInstruction(InstructionObject.BadInstruction);
//            }
//        } else {
//            referenceMonitor.setNewInstruction(InstructionObject.BadInstruction);
//        }
//
//        // run the instruction
//        referenceMonitor.execute();
//    }

//    private boolean verifyName(String str1, String str2) {
//        return referenceMonitor.verifySubjectName(str1) && referenceMonitor.verifyObjectName(str2);
//    }


//    public void printState() {
//        System.out.print(referenceMonitor.outputString());
//    }
}
