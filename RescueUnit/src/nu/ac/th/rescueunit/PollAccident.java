package nu.ac.th.rescueunit;

import java.util.concurrent.TimeUnit;

import android.util.Log;

public class PollAccident implements Runnable {
	private PollAccidentListener listener;
	private int pollingInterval;
	private PollAccidentData pollAccidentData;
	private IServerConnector connector;
	
	public PollAccident(PollAccidentListener listener, int pollingInterval, 
			PollAccidentData pollAccidentData, IServerConnector connector) {
		super();
		this.listener = listener;
		this.pollingInterval = pollingInterval;
		this.pollAccidentData = pollAccidentData;
		this.connector = connector;
	}

	@Override
	public void run() {
		AccidentData data = null;
		
		while(true) {
			try {
				Log.v("FAXXXX", "INSIDE THREAD LOOP");
				try {
					data = connector.pollAccident(pollAccidentData);
					listener.onDataReceived(data);
				} catch (ApplicationException e) {
					// NO CONNECTION, NO RESPOND, EMPTY RESPOND?
					Log.v("FAXXXX", "NO RESPOND?");
				}
				
				TimeUnit.SECONDS.sleep(pollingInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
