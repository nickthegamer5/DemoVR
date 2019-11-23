
public class SearchAgent implements Runnable {
	
	private Thread t;
	
	SearchAgent() {
	    System.out.println("Creating SearchAgent");
	}
	   
	public void run() {
		System.out.println("Running SearchAgent");
		
		
		System.out.println("Thread SearchAgent exiting.");
	}
   
   public void start () {
      System.out.println("Starting SearchAgent");
      if (t == null) {
         t = new Thread (this, "SearchAgent");
         t.start ();
      }
   }
	
}
