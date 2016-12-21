import junit.framework.TestCase;

/**
 * Created by thanhnguyencs on 6/8/15.
 */

public class TestMain extends TestCase{
    public void testMain() {
        RegTest.Utility.redirectStdOut("out.txt");  // redirects standard out to file "out.txt"

        String[] args = {"instructionList"};
        SecureSystem.main(args);

        RegTest.Utility.validate("out.txt", "correctOut.txt", false); // test passes if files are equal
    }
}
