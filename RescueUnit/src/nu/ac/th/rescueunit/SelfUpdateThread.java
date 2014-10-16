package nu.ac.th.rescueunit;

import android.content.Context;

public class SelfUpdateThread implements Runnable{
	private SelfUpdateThreadListener listener;
	private IServerConnector connector;
	private LocatorListener mLocatorListener;
	private ILocator mILocator;
	private Context context;
	private SelfUpdateData selfUpdateData;
	private AcknowledgeDataCollection ack;
	
	public SelfUpdateThread(SelfUpdateThreadListener listener,IServerConnector connector,Context context){
		super();
		this.listener = listener;
		this.connector = connector;
		this.context = context;
		
		initialize();
	}
	
	private void initialize() {
		selfUpdateData = new SelfUpdateData(new Position(), new RescueUnitStatus(), IMEI.getDeviceIMEI(context));
		
		mLocatorListener = new LocatorListener() {
			@Override
			public void onLocationUpdated(Position position) {
				onPostionUpdate(position);
			}
		};
		
		mILocator = new GPS(mLocatorListener, new Position(), context);
		mILocator.startLocatePosition();
	}
	
	private void onPostionUpdate(Position position){
		selfUpdateData.setPosition(position);
	}
	
	private void upDateStatus(RescueUnitStatus rescueUnitStatus){
		selfUpdateData.setStatus(rescueUnitStatus);
	}
	
	public void run(){
		try {
			ack = connector.updateRescueUnit(selfUpdateData);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	}
}
