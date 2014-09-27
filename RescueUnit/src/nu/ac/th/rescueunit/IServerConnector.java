package nu.ac.th.rescueunit;

public interface IServerConnector {	
	public AcknowledgeDataCollection updateRescueUnit(ReportDataCollection reportDataCollection) throws ApplicationException;
	public AccidentData pollAccident(AccidentPollingData pollAccidentData) throws ApplicationException;
	public AcknowledgeDataCollection reportMission(MissionReport missionReport) throws ApplicationException;
	
}
