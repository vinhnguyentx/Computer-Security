import junit.framework.TestCase;

public class TestCase5 extends TestCase {
    public void testMain() {
        RegTest.Utility.redirectStdOut("testcase5Out.txt");  // redirects standard out to file "out.txt"

        String[] args = {"testcase5.txt"};
        SecureSystem.main(args);

        RegTest.Utility.validate("testcase5Out.txt", "correctTestcase5.txt", false); // test passes if files are equal
    }
}
