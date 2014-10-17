package nu.ac.th.rescueunit;

import java.util.concurrent.TimeUnit;

import android.content.Context;

public class SelfUpdate implements Runnable{
	private Context context;
	private SelfUpdateListener listener;
	private IServerConnector connector;
	private SelfUpdateData selfUpdateData;
	private AcknowledgeDataCollection ack;
	
	private int updateInterval;

	public SelfUpdate(Context context, SelfUpdateListener listener, int updateInterval, 
			SelfUpdateData selfUpdateData, IServerConnector connector){
		super();
		this.context = context;
		this.listener = listener;
		this.updateInterval = updateInterval;
		this.selfUpdateData = selfUpdateData;
		this.connector = connector;
		
		initialize();
	}
	
	private void initialize() {
		readRescueUnitUpdate();
	}
	
	private void readRescueUnitUpdate() {
		selfUpdateData.setPosition(ApplicationSharedPreference.getPosition(context));
		selfUpdateData.setStatus(ApplicationSharedPreference.getStatus(context));
	}
	
	public void run(){
		
		while(true) {
			readRescueUnitUpdate();
			try {
				try {
					ack = connector.updateRescueUnit(selfUpdateData);
					listener.onUpdateData(ack);
				} catch (ApplicationException e) {
					// NO CONNECTION, NO RESPOND, EMPTY RESPOND?
				}
				
				TimeUnit.SECONDS.sleep(updateInterval);
			} catch (InterruptedException e) {
				
			}
		}
	}
}
