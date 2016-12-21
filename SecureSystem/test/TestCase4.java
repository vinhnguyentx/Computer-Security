import junit.framework.TestCase;

public class TestCase4 extends TestCase {
    public void testMain() {
        RegTest.Utility.redirectStdOut("testcase4Out.txt");  // redirects standard out to file "out.txt"

        String[] args = {"testcase4"};
        SecureSystem.main(args);

        RegTest.Utility.validate("testcase4Out.txt", "correctTestcase4.txt", false); // test passes if files are equal
    }
}
