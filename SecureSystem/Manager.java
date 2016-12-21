import java.util.StringTokenizer;

/**
 * Created by thanhnguyencs on 6/8/15.
 */
public class Manager {
    // constant
    public static final String READ = "read";
    public static final String WRITE = "write";
    public static final String BAD = "bad";

    ReferenceMonitor referenceMonitor;


    public Manager() {
        referenceMonitor = new ReferenceMonitor();
    }


    public void createNewSubject(String name, SecurityLevel sl) {
        referenceMonitor.createSubject(name, sl);
    }


    public void createNewObject(String name, SecurityLevel sl) {
        referenceMonitor.createObject(name, sl);
    }


    public void verifyAndRun(String line) {
        StringTokenizer st = new StringTokenizer(line);
        int num = st.countTokens();
        String command = st.nextToken().toLowerCase();

        if (num == 3) {
            String str1 = st.nextToken().toLowerCase();
            String str2 = st.nextToken().toLowerCase();
            if (command.equals(Manager.READ) && verifyName(str1, str2)) {
                referenceMonitor.setNewInstruction(InstructionObject.createReadInstance(command, str1, str2));
            } else {
                referenceMonitor.setNewInstruction(InstructionObject.BadInstruction);
            }
        } else if (num == 4) {
            String str1 = st.nextToken().toLowerCase();
            String str2 = st.nextToken().toLowerCase();
            try {
                int v = new Integer(st.nextToken());
                if (command.equals(Manager.WRITE) && verifyName(str1, str2)) {
                    referenceMonitor.setNewInstruction(InstructionObject.createWriteInstance(command, str1, str2, v));
                } else {
                    referenceMonitor.setNewInstruction(InstructionObject.BadInstruction);
                }
            } catch (NumberFormatException e) {
                referenceMonitor.setNewInstruction(InstructionObject.BadInstruction);
            }
        } else {
            referenceMonitor.setNewInstruction(InstructionObject.BadInstruction);
        }

        // run the instruction
        referenceMonitor.execute();
    }

    private boolean verifyName(String str1, String str2) {
        return referenceMonitor.verifySubjectName(str1) && referenceMonitor.verifyObjectName(str2);
    }


    public void printState() {
        System.out.print(referenceMonitor.outputString());
    }
}
