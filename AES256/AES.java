import java.io.*;

/**
 * Created by thanhnguyencs and vinhnguyentx on 6/28/15.
 */
public class AES {
    public static boolean DEBUG = false;


    public static void main(String[] args) {
        long startTime = System.nanoTime();
        if (args[0].equals("db")) {
            // debug mode. Command line: java AES db <d or e> <key> <one line of plaintext>
            debug(args);
        } else {

            String option = args[0];
            String keyFile = args[1];
            String fileData = args[2];
            String key = getKey(keyFile);
            if (!validateInputLine(key)) {
                throw new IllegalArgumentException("Key is malformed");
            }
            DataCrypto aes = new DataCrypto(key);
            BufferedReader in = null;
            PrintWriter out = null;
            try {
                in = new BufferedReader(new FileReader(fileData));
                String line;
                if (option.equals("e")) {
                    out = new PrintWriter(fileData + ".enc");
                    while ((line = in.readLine()) != null) {
                        if (validateInputLine(line)) {
                            String ret = aes.encrypt(line);
                            out.println(ret);
                        }
                    }
                } else if (option.equals("d")) {
                    out = new PrintWriter(fileData + ".dec");
                    while ((line = in.readLine()) != null) {
                        if (validateInputLine(line)) {
                            String ret = aes.decrypt(line);
                            out.println(ret);
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Can't find the data file");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    out.close();
                }
            }

            long endTime = System.nanoTime();
            double timeRun = (endTime - startTime) / 1000000000.0;

            System.out.println("Time to run option " + option + " on " + fileData + " : " + timeRun);
        }
    }

    private static void debug(String[] args) {
        AES.DEBUG = true;

        String option = args[1];
        String key = args[2];
        String data = args[3];
        if (!validateInputLine(key)) {
            throw new IllegalArgumentException("Key is malformed");
        }
        if (option.equals("e")) {
            DataCrypto aes = new DataCrypto(key);
            String ret = "";
            if (validateInputLine(data))
                ret = aes.encrypt(data);
            System.out.println();
            System.out.println("Key: \n" + key);
            System.out.println("Plaintext: \n" + data);
            System.out.println("Cipher: \n" + ret);
        } else if (option.equals("d")) {
            DataCrypto aes = new DataCrypto(key);
            String ret = "";
            if (validateInputLine(data))
                ret = aes.decrypt(data);
            System.out.println();
            System.out.println("Key: \n" + key);
            System.out.println("Cipher: \n" + data);
            System.out.println("Plaintext: \n" + ret);
        }
    }

    private static boolean validateInputLine(String key) {
        for (int i = 0; i < 256 && i < key.length(); ++i) {  // hard code 256
            char c = key.charAt(i);
            boolean t = ((c >= '0' && c <= '9') ||
                        (c >= 'a' && c <= 'f') ||
                        (c >= 'A' && c <= 'F'));
            if (!t)
                return false;
        }
        return true;
    }

    private static String getKey(String keyFile) {
        String key = "";
        BufferedReader inKey = null;
        try {
            inKey = new BufferedReader(new FileReader(keyFile));
            key = inKey.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Can't find the key file");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inKey != null) {
                try {
                    inKey.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return key;
    }
}
