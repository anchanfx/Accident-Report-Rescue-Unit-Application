package nu.ac.th.rescueunit;

public interface IServerConnector {	
	public AcknowledgeDataCollection updateRescueUnit(ReportDataCollection reportDataCollection) throws ApplicationException;
	public AccidentData pollAccident(PollAccidentData pollAccidentData) throws ApplicationException;
	
}
