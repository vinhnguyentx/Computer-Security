/**
 * Created by thanhnguyencs on 6/8/15.
 */
public class InstructionObject {
    private String command;
    private String subject;
    private String object;
    private int value;


    public static final InstructionObject BadInstruction = new InstructionObject(Manager.BAD, null, null, 0);

    private InstructionObject (String cmd, String subject, String object, int value) {
        this.command = cmd;
        this.subject = subject;
        this.object = object;
        this.value = value;
    }

    public static InstructionObject createReadInstance(String cmd, String subject, String object) {
        return new InstructionObject(cmd, subject, object, 0);
    }

    public static InstructionObject createWriteInstance(String cmd, String subject, String object, int value) {
        return new InstructionObject(cmd, subject, object, value);
    }

    public String getCommand() {
        return command;
    }

    public String getSubject() {
        return subject;
    }

    public String getObject() {
        return object;
    }

    public int getValue() {
        return value;
    }
}
