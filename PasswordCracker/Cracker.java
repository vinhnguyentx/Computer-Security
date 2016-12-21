import java.util.ArrayList;
import java.util.Arrays;


public class Cracker extends Thread {
	User user;
    ArrayList<String> mangleList;
	
    Cracker (ArrayList<String> mList, User user) {
    	this.user = user;
    	mangleList = mList;
    }
    
    public void run(){
		for (String w : mangleList) {
			String encStr = jcrypt.crypt(user.salt, w);
			if (encStr.equals(user.pass)) {
			    System.out.printf("FOUND: password for %s = \"%s\"          \n", 
			    	Arrays.toString(user.name), w);
				user.found = true;
				return;
			}
		}
    }
}
