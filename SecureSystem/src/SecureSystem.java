import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SecureSystem {

    public static void main(String args[]) {
        //secure system

        SecurityLevel low  = SecurityLevel.LOW;
        SecurityLevel high = SecurityLevel.HIGH;

        Manager sys = new Manager();
        sys.createNewSubject("Lyle", low);
        sys.createNewSubject("Hal", high);

        sys.createNewObject("LObj", low);
        sys.createNewObject("HObj", high);

        // parse instructions
        try {
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            System.out.println("Reading from file: " + args[0]);
            String line = br.readLine();
            while (line != null) {
                sys.verifyAndRun(line);
                // outputString
                sys.printState();

                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("The file is not existed");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
