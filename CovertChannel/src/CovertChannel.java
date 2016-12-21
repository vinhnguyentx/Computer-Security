import java.io.*;
import java.math.BigInteger;

public class CovertChannel {


    public static void main(String args[]) {
        long startTime = System.nanoTime();
        int fileSize = 0;
        String logFileName = "log";
        SecurityLevel low  = SecurityLevel.LOW;
        SecurityLevel high = SecurityLevel.HIGH;

        Manager sys = new Manager();
        sys.createNewSubject("Lyle", low);
        sys.createNewSubject("Hal", high);

        String fileName;
        PrintWriter outLog = null;
        if (args.length == 2) {
            assert args[0].equals("v");
            try {
                outLog = new PrintWriter(logFileName);
                sys.setLogOutput(outLog);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Can't create log file");
            }
            fileName = args[1];

        } else if (args.length == 1) {
            fileName = args[0];
        } else {
            throw new IllegalArgumentException("The input parameters for the program are not valid");
        }

        FileInputStream in = null;
        FileOutputStream out = null;
        try {

            in = new FileInputStream(fileName);
            out = new FileOutputStream(fileName + ".out");
            sys.setOutput(out);
            int b;
            while ((b = in.read()) != -1) {
                ++fileSize;
                for (int i = 0; i < 8; ++i) {
                    sys.runCovertChannel(b);
                    b >>= 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("There is something wrong with files");
        } finally {
            try {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
                if (outLog != null) {
                    outLog.flush();
                    outLog.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.nanoTime();
        long timeRun = (endTime - startTime)/1000000;
        // output the speed:
        System.out.println("File size: " + fileSize);
        System.out.println("Time to run (milliseconds): " + timeRun);
        System.out.println("Bandwidth: " + fileSize * 8 / timeRun);

    }
}
