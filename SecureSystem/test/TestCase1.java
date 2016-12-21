import junit.framework.TestCase;

public class TestCase1 extends TestCase{
    public void testMain() {
        RegTest.Utility.redirectStdOut("testcase1.txt");  // redirects standard out to file "out.txt"

        String[] args = {"testCase1"};
        SecureSystem.main(args);

        RegTest.Utility.validate("testcase1.txt", "correctTestcase1.txt", false); // test passes if files are equal
    }
}
