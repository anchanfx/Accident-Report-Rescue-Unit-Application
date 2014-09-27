package nu.ac.th.rescueunit;

import java.util.concurrent.TimeUnit;

import android.util.Log;

public class PollAccident implements Runnable {
	private PollAccidentListener listener;
	private int pollingInterval;
	private AccidentPollingData pollAccidentData;
	private IServerConnector connector;
	
	public PollAccident(PollAccidentListener listener, int pollingInterval, 
			AccidentPollingData pollAccidentData, IServerConnector connector) {
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
				try {
					data = connector.pollAccident(pollAccidentData);
					listener.onDataReceived(data);
				} catch (ApplicationException e) {
					// NO CONNECTION, NO RESPOND, EMPTY RESPOND?
				}
				
				TimeUnit.SECONDS.sleep(pollingInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
