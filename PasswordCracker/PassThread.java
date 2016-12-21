import java.util.ArrayList;
import java.util.LinkedList;

public class PassThread extends Thread {
	User user;
	ArrayList<String> passList;

	PassThread (ArrayList<String> passList, User user) {
		this.user = user;
		this.passList = passList;
	}
	
	static void process(ArrayList<String> list, LinkedList<User> users) throws  Exception{
		ArrayList<Thread> threads = new ArrayList<Thread>(users.size());
		for (User u : users) {
			if (u.found) {
				continue;		// password found, go next
			}
			
	    	PassThread crack = new PassThread(list, u);
	        Thread t = new Thread(crack);
	        t.start();
	        threads.add(t);
		}
		
		// allows one thread to wait for the completion of another
		for (Thread t : threads) {
			t.join();
		}
    }

	public void run() { 
		for (String p : passList) {
			String encPass = jcrypt.crypt(user.salt, p);
			if (encPass.equals(user.pass)) {
				System.out.println("Password of " + getname(user) + "= " + p);
				user.found = true;
				return;
			}
		}
	}
	
	public String getname(User user) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < user.name.length; ++i) {
			sb.append(user.name[i].substring(0, 1).toUpperCase());
			sb.append(user.name[i].substring(1));
			sb.append(" ");
		}
		return sb.toString();
	}
}