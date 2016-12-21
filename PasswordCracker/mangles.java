import java.util.ArrayList;

public class mangles {

	static ArrayList<String> mangle(String word){
    	ArrayList<String> mangledWords = new ArrayList<String>();
    	String mWord;
    	
    	//prepend & append
    	for (char c = '!'; c <= '~'; ++c){
            mWord = word + String.valueOf(c);
            mangledWords.add(mWord);

            mWord = String.valueOf(c) + word;
            mangledWords.add(mWord);
        }
    	
    	//delete first character
    	mWord = word.substring(1);
    	mangledWords.add(mWord);
    	
    	//delete last character
    	mWord = word.substring(0, word.length()-1);
    	mangledWords.add(mWord);
    	
    	//reverse the string
    	mWord = new StringBuilder(word).reverse().toString();
    	mangledWords.add(mWord);
    	
    	//duplicate
    	mWord = word + word;
    	mangledWords.add(mWord);
    	
    	//reflect the string
    	String temp = new StringBuilder(word).reverse().toString();
    	mWord = word + temp;
    	mangledWords.add(mWord);
    	
    	mWord = temp + word;
    	mangledWords.add(mWord);
    	
    	//uppercase
    	mWord = word.toUpperCase();
    	mangledWords.add(mWord);
    	
    	//capitalize
    	mWord = word.substring(0, 1).toUpperCase() + word.substring(1);
    	mangledWords.add(mWord);
    	
    	//ncapitalize
    	mWord = word.substring(0, 1) + word.toUpperCase().substring(1);
    	mangledWords.add(mWord);

    	//toggle case, capitalize from first character
    	mWord = new StringBuilder().toString();
    	for (int i = 0; i < word.length() - 1; ) {
    		mWord += word.substring(i, i + 1).toUpperCase();
    		mWord += word.substring(i + 1, i + 2);
    		i +=2;
    	}
    	if (mWord.length() < word.length()) {
    		mWord += word.substring(word.length() - 1, word.length()).toUpperCase();
    	}
    	mangledWords.add(word);
    	
    	//toggle case, capitalize from second character
    	mWord = new StringBuilder().toString();
    	for (int i = 0; i < word.length() - 1; ) {
    		mWord += word.substring(i, i + 1);
    		mWord += word.substring(i + 1, i + 2).toUpperCase();
    		i +=2;
    	}
    	if (mWord.length() < word.length()) {
    		mWord += word.substring(word.length() - 1, word.length());
    	}
    	mangledWords.add(word);
    	    	
    	return mangledWords;
    }
}