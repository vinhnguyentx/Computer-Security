import java.io.*;
import java.util.*;

public class RandomAndCrypto {

    // constant name
    private static final String plaintext = "testText";
    private static final String encrypted = "testText.enc";
    private static final String decrypted = "testText.dec";



    private int num;
    private ArrayList<Integer> f;
    private HashMap<String, String> table;
    private int total;
    private static int actualBits;

    public RandomAndCrypto(int k, int total, ArrayList<Integer> frequencies, HashMap<String, String> huffTable) {
        num = k;
        table = huffTable;
        f = frequencies;
        this.total = total;
        actualBits = 0;
    }

    // used for extra credit 2
    RandomAndCrypto(int total, ArrayList<Integer> frequencies, HashMap<String, String> huffTable) {
        num = 0;
        table = huffTable;
        f = frequencies;
        this.total = total;
    }

    public void createTextFile() {
        // get number of characters used
        int numChar = table.size();

        // build an array to randomize text
        char[] randomChar = new char[total];
        int temp = 0;
        for (int i = 0; i < numChar; ++i) {
            for (int j = 0; j < f.get(i); ++j) {
                randomChar[temp+j] = Encoder.alphabet[i];
            }
            temp += f.get(i);
        }

        // build the text file
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(plaintext));
            for (int i = 0; i < num; ++i) {
                int rand = (int) (Math.random() * total);
                out.write(randomChar[rand]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("can't not write to testText file");
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // requirement 3
    public void encrypt() {
        encrypt(1, table);
    }

    // requirement 3
    public void decrypt() {
        decrypt(1, table);
    }

    // encode  with numSymbol symbol and specific table (newTable) - requirement
    public static void encrypt(int numSymbol, HashMap<String, String> newTable) {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new FileReader(plaintext));
            out = new PrintWriter(encrypted + numSymbol);
            char[] ch = new char[numSymbol];
            int c;
            while ((c = in.read(ch, 0, numSymbol)) != -1) {
                String key = new String(ch);
                String value = newTable.get(key);
                out.write(value);
                actualBits += value.length();
                out.write('\n');
            }
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
                out.close();;
            }
        }
    }


    // totalChar: number of characters is used.
    public static double averageBits(int numChar) {
        return (double) actualBits / numChar;
    }

    // decode with numSymbol - requirement 5 + extra credit
    public static void decrypt(int numSymbol, HashMap<String, String> newTable) {
        HashMap<String, String> reverseTable = reverseMap(newTable);
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new FileReader(encrypted + numSymbol));
            out = new PrintWriter(decrypted + numSymbol);
            String line;
            while ((line = in.readLine()) != null) {
                String value = reverseTable.get(line);
                out.write(value);
            }
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
    }


    // method used to swap the keys and values of hashmap
    private static HashMap<String, String> reverseMap(HashMap<String, String> originalMap) {
        Set s = originalMap.keySet();
        Iterator i = s.iterator();
        HashMap<String, String> ret = new HashMap<>();

        while (i.hasNext()) {
            String k = (String) i.next();
            String value = originalMap.get(k);
            ret.put(value, k);
        }
        assert originalMap.size() == ret.size(); // it should not lose any key value pair.
        return ret;
    }
}
