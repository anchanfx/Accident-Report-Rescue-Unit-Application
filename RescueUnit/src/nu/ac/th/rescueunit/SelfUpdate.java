package nu.ac.th.rescueunit;

import java.util.concurrent.TimeUnit;

import android.content.Context;

public class SelfUpdate implements Runnable{
	private SelfUpdateThreadListener listener;
	private IServerConnector connector;
	private Context context;
	private SelfUpdateData selfUpdateData;
	private AcknowledgeDataCollection ack;
	private int updateInterval;
	
	public SelfUpdate(SelfUpdateThreadListener listener,int updateInterval, 
			SelfUpdateData selfUpdateData, IServerConnector connector){
		super();
		this.listener = listener;
		this.updateInterval = updateInterval;
		this.selfUpdateData = selfUpdateData;
		this.connector = connector;
		
		initialize();
	}
	
	private void initialize() {
		updateSelfUpdateData();
	}
	
	private void updateSelfUpdateData() {
		// Update Position
		// Update RescueUnitStatus
	}
	
	public void run(){
		
		while(true) {
			updateSelfUpdateData();
			try {
				try {
					ack = connector.updateRescueUnit(selfUpdateData);
					listener.onUpdateData(ack);
				} catch (ApplicationException e) {
					// NO CONNECTION, NO RESPOND, EMPTY RESPOND?
				}
				
				TimeUnit.SECONDS.sleep(updateInterval);
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
		}
	}
}
