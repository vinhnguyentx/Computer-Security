public class SecurityLevel {

    private final int level;
    // Constants
    private static final int LOW_CONSTANT = 1;
    private static final int HIGH_CONSTANT = 2;

    static final SecurityLevel LOW = new SecurityLevel(LOW_CONSTANT);
    static final SecurityLevel HIGH = new SecurityLevel(HIGH_CONSTANT);

    private SecurityLevel (int level) {
        this.level = level;
    }

    public boolean dominates(SecurityLevel object) {
        return this.level >= object.level;
    }
}
