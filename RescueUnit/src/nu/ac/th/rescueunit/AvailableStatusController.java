package nu.ac.th.rescueunit;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

public class AvailableStatusController {
	
	private ApplicationDbHelper db;
	private Context context;
	
	public AvailableStatusController(Context context) {
		this.context = context;
		db = new ApplicationDbHelper(context);
	}
	
	public void onStatusChange(int accidentID, int status) {
		switch (status) {
			case RescueState.ACCEPT:
				availableToUnavailable(accidentID, status);	
				break;
			case RescueState.COMPLETE:
			case RescueState.ABANDON:
				unavailableToAvailable(accidentID, status);
				break;	
			default:
				break;
		}
	}
	
	private void availableToUnavailable(int accidentID, int status) {
		try {
			db.addAccidentInProgress(accidentID);
			updateAvailableStatus(false);
		} catch(SQLiteConstraintException e) {
			// Constraint Fail
			// Assume duplication on Accident
		}
		
	}
	
	private void unavailableToAvailable(int accidentID, int status) {
		try {
			db.removeAccidentInProgress(accidentID);
			
			if(db.isAccidentInProgressEmpty()) {
				updateAvailableStatus(true);
			}
		} catch(SQLiteConstraintException e) {
			// Constraint Fail
			// WHY???
		}
	}
	
	private void updateAvailableStatus(boolean available) {
		RescueUnitStatus rescueUnitStatus = ApplicationSharedPreference.getStatus(context);
		rescueUnitStatus.setAvailable(available);
		ApplicationSharedPreference.storeStatus(context, rescueUnitStatus);
	}
}
