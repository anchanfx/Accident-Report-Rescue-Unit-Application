package nu.ac.th.rescueunit;

public class SelfUpdateThread implements Runnable{
	private SelfUpdateThreadListener listener;
	private IServerConnector connector;
	private SelfUpdateData data;
	
	public SelfUpdateThread(SelfUpdateThreadListener listener,IServerConnector connector,SelfUpdateData data){
		super();
		this.listener = listener;
		this.connector = connector;
		this.data = data;
	}
	
	public void run(){
		try {
			connector.updateRescueUnit(data);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	}
}
