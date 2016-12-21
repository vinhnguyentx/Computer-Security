import java.io.FileOutputStream;
import java.io.OutputStream;

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

    public static InstructionObject createCreateInstance(String cmd, String subject, String object) {
        return new InstructionObject(cmd, subject, object, 0);
    }

    public static InstructionObject createDestroyInstance(String cmd, String subject, String object) {
        return new InstructionObject(cmd, subject, object, 0);
    }

    public static InstructionObject createRunInstance(String cmd, String subject) {
        return new InstructionObject(cmd, subject, null, 0);
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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(command.toUpperCase());
        sb.append(" ");
        sb.append(subject.toUpperCase());
        if (command.equals(Manager.RUN)) {
            return sb.toString();
        }
        sb.append(" ");
        sb.append(object.toUpperCase());
        if (command.equals(Manager.WRITE)) {
            sb.append(" ");
            sb.append(value);
        }
        return sb.toString();
    }
}
