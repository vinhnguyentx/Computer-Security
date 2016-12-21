
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;


public class Encoder {
	public static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

	public static void main(String[] args) throws FileNotFoundException {
        String frequenciesFile = args[0];
        int k = Integer.parseInt(args[1]);

        ArrayList<Integer> frequencies = new ArrayList<>();  // array stores frequencies of characters
        HashMap<String, Integer> charFreq = collectFrequencies(frequenciesFile, frequencies);

        //Use HuffmanCode to devise a binary encoding
        HuffmanCode hc = new HuffmanCode(charFreq);

        System.out.println("Huffman table:\n");
        hc.printTable();

        // requirement 3
        RandomAndCrypto rc = new RandomAndCrypto(k, hc.totalWeight(), frequencies, hc.getHuffTable());
        rc.createTextFile();
        rc.encrypt();
        rc.decrypt();

        System.out.println("\nEntropy: " + entropy(charFreq, hc.totalWeight()));
        System.out.println("Actual average bits per symbol: " + RandomAndCrypto.averageBits(k));


        System.out.println("\n//------- calculate the new table for 2-symbol derived alphabet -----//\n");
        HashMap<String, Integer> table2Symbol = createNewTable(2, frequencies);

        System.out.println("Huffman table:\n");
        HuffmanCode hc2 = new HuffmanCode(table2Symbol);
        hc2.printTable();

        RandomAndCrypto.encrypt(2, hc2.getHuffTable());
        RandomAndCrypto.decrypt(2, hc2.getHuffTable());

        System.out.println("\nEntropy (2 symbol derived alphabet): " + entropy(table2Symbol, hc2.totalWeight()));
        System.out.println("Actual average bits per symbol: " + RandomAndCrypto.averageBits(k));

//        HashMap<String, Integer> r = readFile("referenceFile");
//        HuffmanCode huff = new HuffmanCode(r);
//        huff.printTable();
//        RandomAndCrypto.encrypt(1, huff.getHuffTable());
	}

    private static HashMap<String, Integer> collectFrequencies(String fileName, ArrayList<Integer> frequencies) {
        BufferedReader br = null;
        HashMap<String, Integer> charFreq = new HashMap<String, Integer>();
        try {
            // collect Character frequencies
            br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            int numChars = 0;
            while (line != null) {
                int freq = Integer.parseInt(line);
                frequencies.add(freq);
                charFreq.put(String.valueOf((char) ('a' + numChars)), freq);
                ++numChars;
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("The file is not existed");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return charFreq;
    }

    private static HashMap<String, Integer> createNewTable(int numSymbol, ArrayList<Integer> frequencies) {
        HashMap<String, Integer> table = new HashMap<>();
        int size = frequencies.size();

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                char[] keyChar = {alphabet[i], alphabet[j]};
                String key = new String(keyChar);
                int value = frequencies.get(i) * frequencies.get(j);
                table.put(key, value);
            }
        }
        return table;
    }

    public static double entropy(HashMap<String, Integer> charFreq, int sum) {
        double e = 0.0;
        for (String s : charFreq.keySet()) {
            double prob = (double) charFreq.get(s)/sum;
            e -= prob * Math.log(prob)/Math.log(2);
        }
        return e;
    }

    public static HashMap<String, Integer> readFile(String referenceFile) throws FileNotFoundException {
    	BufferedReader br = null;
    	PrintWriter out = new PrintWriter("testText");
    	int[] freq = new int[26];
    	try {
    		br = new BufferedReader(new FileReader(referenceFile));
    		int c;

    		while ( (c = br.read()) != -1) {
    			if ( c >= 97 && c <= 122 ) {
    				freq[c - 97] ++;
    				out.print((char) c);
    			} else if (c >= 65 && c <= 90 ){
    				freq[c - 65] ++;
    				out.print((char) (c + 32));
    			}
    		}

    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    		throw new IllegalArgumentException("The file is not existed");
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		if (br != null) {
    			try {
    				br.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    		if (out != null) {
                out.close();;
            }
    	}
    	return filterArray(freq);
    }

    private static HashMap<String, Integer> filterArray(int[] f) {
    	HashMap<String, Integer> hf = new HashMap<String, Integer>();
    	for (int i = 0; i < 26; ++i) {
    		if (f[i] != 0) {
    			hf.put(Encoder.alphabet[i] + "", f[i]);
    		}
    	}
    	return hf;
    }

}
