import java.io.*;
import java.util.*;

class PasswordCrack{
    static final boolean DEBUG = true;
    static ArrayList<String> dict;
    static LinkedList<User> users;
    
    public static void main(String[] args) throws Exception{
        String dictStr = args[0];
        String passwords = args[1];
        
        // create array of users' info
        users = User.userInfo(passwords);
        
        // input dictionary
        File dFile = new File(dictStr);
        Scanner sc = new Scanner(dFile);
        dict = new ArrayList<String>(400);
        while(sc.hasNext()){
        	dict.add(sc.next().toLowerCase());
        }
        sc.close();
                
        //mangling
		for (User u : users) {
			if (u.found) { 
				continue;		// password already found
			}
			
			ArrayList<String> mangledWords = new ArrayList<String>();
			for (String n : u.name) {
				mangledWords.addAll(mangles.mangle(n));
			}
			
	    	PassThread crack = new PassThread(mangledWords, u);
	        crack.run();
		}

		//evaluating
        for (String word : dict) {
            ArrayList<String> mangledWords = mangles.mangle(word);
            PassThread.process(mangledWords, users);

            for (String w : mangledWords) {
                ArrayList<String> doubleMangle = mangles.mangle(w);
                PassThread.process(doubleMangle, users);
            }
        }
        
    }
}