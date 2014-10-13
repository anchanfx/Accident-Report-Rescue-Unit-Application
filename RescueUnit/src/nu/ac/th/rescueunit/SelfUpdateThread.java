package nu.ac.th.rescueunit;

public class SelfUpdateThread implements Runnable{
	private SelfUpdateThreadListener listener;
	private IServerConnector connector;
	
	public SelfUpdateThread(SelfUpdateThreadListener listener,IServerConnector connector){
		super();
		this.listener = listener;
		this.connector = connector;
	}
	
	public void run(){
		
		
	}
}
