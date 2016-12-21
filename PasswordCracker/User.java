import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;


public class User {
	String[] name;
	String salt;
	String pass;
	boolean found = false;

	public User (String[] name, String salt, String pass) {
		this.name = name;
		this.salt = salt;
		this.pass = pass;
	}
	
	// get users' information
	public static LinkedList<User> userInfo (String passwords) throws Exception{
    	File passFile = new File(passwords);
        Scanner sc = new Scanner(passFile);
        LinkedList<User> users = new LinkedList<User>();
        while (sc.hasNext()){
	        String line = sc.nextLine();

	        String[] tokens = line.split(":");

	        String[] name = tokens[4].toLowerCase().split(" ");
	        String pass = tokens[1];
	        String salt = pass.substring(0, 2);
	        
	        User user = new User(name, salt, pass);
	        users.add(user);
	    }
    	
        sc.close();
    	return users;
    }
	
	
}
