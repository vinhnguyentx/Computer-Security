import junit.framework.TestCase;

public class TestCase3 extends TestCase {
    public void testMain() {
        RegTest.Utility.redirectStdOut("testcase3.txt");  // redirects standard out to file "out.txt"

        String[] args = {"testCase3"};
        SecureSystem.main(args);

        RegTest.Utility.validate("testcase3.txt", "correctTestcase3.txt", false); // test passes if files are equal
    }
}
