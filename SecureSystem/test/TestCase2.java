import junit.framework.TestCase;

public class TestCase2 extends TestCase {
    public void testMain() {
        RegTest.Utility.redirectStdOut("testcase2.txt");  // redirects standard out to file "out.txt"

        String[] args = {"testCase2"};
        SecureSystem.main(args);

        RegTest.Utility.validate("testcase2.txt", "correctTestcase2.txt", false); // test passes if files are equal
    }
}
