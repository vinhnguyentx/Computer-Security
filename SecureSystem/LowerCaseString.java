/**
 * Created by thanhnguyencs on 6/9/15.
 */
public class LowerCaseString {
    private final String s;

    public LowerCaseString(String s) {
        this.s = s;
    }

    public boolean equals (Object s2) {
        if (s2 == null)
            return false;
        if (s2.getClass() != this.getClass())
            return false;
        return this.s.toLowerCase().equals(((LowerCaseString) s2).s.toLowerCase());
    }

    public int hashCode() {
        return this.s.toLowerCase().hashCode();
    }

    public String toString() {
        return s;
    }
}
