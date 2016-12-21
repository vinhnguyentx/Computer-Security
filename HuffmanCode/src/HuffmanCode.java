import java.util.*;

abstract class HuffmanTree implements Comparable<HuffmanTree> {
	public final int frequency; // the frequency of this tree
	public HuffmanTree(int freq) { frequency = freq; }

	// compares on the frequency
	public int compareTo(HuffmanTree tree) {
		return frequency - tree.frequency;
	}
}

class HuffmanLeaf extends HuffmanTree {
	public final String value; // the character this leaf represents

	public HuffmanLeaf(int freq, String val) {
		super(freq);
		value = val;
	}
}

class HuffmanNode extends HuffmanTree {
	public final HuffmanTree left, right; // subtrees

	public HuffmanNode(HuffmanTree l, HuffmanTree r) {
		super(l.frequency + r.frequency);
		left = l;
		right = r;
	}
}


public class HuffmanCode {

    private HashMap<String, Integer> charFreq;
    private HashMap<String, String> huffTable;
    private HashMap<String, Integer> huffWeight;

    public HuffmanCode(HashMap<String, Integer> table) {
        charFreq = table;
        huffTable = new HashMap<>();
        huffWeight = new HashMap<>();

        // build table
        buildTable();
    }

    public HuffmanCode(int[] f) {
    	charFreq = buildHashMap(f);
    	huffTable = new HashMap<>();
        huffWeight = new HashMap<>();
     // build table
        buildTable();
    }

    private HashMap<String, Integer> buildHashMap(int[] f) {
    	HashMap<String, Integer> freq = new HashMap<String, Integer>();
    	for (int i = 0; i < 26; ++i) {
    		freq.put((Encoder.alphabet[i]) + "", f[i]);
    	}
    	return freq;
    }

	// input is an array of frequencies, indexed by character code
	private HuffmanTree buildTree() {
		PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
		// initially, we have a forest of leaves
		// one for each non-empty character
		for (String s : charFreq.keySet()) {
			trees.offer(new HuffmanLeaf(charFreq.get(s), s));
		}

		assert trees.size() > 0;
		// loop until there is only one tree left
		while (trees.size() > 1) {
			// two trees with least frequency
			HuffmanTree a = trees.poll();
			HuffmanTree b = trees.poll();

			// put into new node and re-insert into queue
			trees.offer(new HuffmanNode(a, b));
		}
		return trees.poll();
	}


	private void createHashTable(HuffmanTree tree, StringBuffer prefix) {
		assert tree != null;
		if (tree instanceof HuffmanLeaf) {
			HuffmanLeaf leaf = (HuffmanLeaf)tree;

			// print out character, frequency, and code for this leaf (which is just the prefix)
//			System.out.println(leaf.value + "\t" + leaf.frequency + "\t" + prefix);
            huffWeight.put(leaf.value, leaf.frequency);
            huffTable.put(leaf.value, prefix.toString());

		} else if (tree instanceof HuffmanNode) {
			HuffmanNode node = (HuffmanNode)tree;

			// traverse left
			prefix.append('0');
			createHashTable(node.left, prefix);
			prefix.deleteCharAt(prefix.length()-1);

			// traverse right
			prefix.append('1');
			createHashTable(node.right, prefix);
			prefix.deleteCharAt(prefix.length()-1);
		}
	}

    private void buildTable() {
        HuffmanTree tree = buildTree();
        createHashTable(tree, new StringBuffer());
    }

    public HashMap<String, String> getHuffTable() {
        return huffTable;
    }


    public int totalWeight() {
        int sum = 0;
        for (String k : huffWeight.keySet()) {
            sum += huffWeight.get(k);
        }

        return sum;
    }
    public void printTable() {
        //print Huffman table here
        System.out.println("SYMBOL\tWEIGHT\tHUFFMAN CODE");
        for (String k : huffTable.keySet()) {
            System.out.println(k + "\t\t" + huffWeight.get(k) + "\t\t" + huffTable.get(k));
        }
    }
}
