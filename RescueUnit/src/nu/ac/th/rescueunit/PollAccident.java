package nu.ac.th.rescueunit;

import java.util.concurrent.TimeUnit;

import android.util.Log;

public class PollAccident implements Runnable {
	private PollAccidentListener listener;
	private int pollingInterval;
	private AccidentPollingRequestData pollRequestData;
	private IServerConnector connector;
	
	public PollAccident(PollAccidentListener listener, int pollingInterval, 
			AccidentPollingRequestData pollAccidentData, IServerConnector connector) {
		super();
		this.listener = listener;
		this.pollingInterval = pollingInterval;
		this.pollRequestData = pollAccidentData;
		this.connector = connector;
	}

	@Override
	public void run() {
		AccidentPollingData data = null;
		
		while(true) {
			try {
				try {
					data = connector.pollAccident(pollRequestData);
					listener.onDataReceived(data);
				} catch (ApplicationException e) {
					// NO CONNECTION, NO RESPOND, EMPTY RESPOND?
				}
				
				TimeUnit.SECONDS.sleep(pollingInterval);
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
		}
	}
}
