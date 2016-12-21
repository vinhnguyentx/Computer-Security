import java.util.ArrayList;


public class mangle {
	String word;

	public mangle (String word) {
		this.word = word;
	}
	
	static ArrayList<String> mangle (String word){
    	ArrayList<String> mangledWords = new ArrayList<String>();
    	String mWord;
    	
    	mangledWords.add(word);		// lower case
    	
    	mWord = word.substring(1);
    	mangledWords.add(mWord);
    	mWord = word.substring(0, word.length()-1);
    	mangledWords.add(mWord);
    	
    	// capitalization
    	mWord = word.toUpperCase();
    	mangledWords.add(mWord);
    	mWord = word.substring(0, 1).toUpperCase() + word.substring(1);
    	mangledWords.add(mWord);
    	mWord = word.substring(0, 1) + word.toUpperCase().substring(1);
    	mangledWords.add(mWord);
    	
    	mWord = word + word;
    	mangledWords.add(mWord);
    	
    	mWord = new StringBuilder(word).reverse().toString();
    	mangledWords.add(mWord);
	    
        for(char c = '!'; c <= '~'; ++c){
            mWord = word + String.valueOf(c);
            mangledWords.add(mWord);

            mWord = String.valueOf(c) + word;
            mangledWords.add(mWord);
        }
    	return mangledWords;
    }
}
