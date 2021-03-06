package nu.ac.th.rescueunit;

public interface IServerConnector {	
	public AcknowledgeDataCollection updateRescueUnit(SelfUpdateData selfUpdateData) throws ApplicationException;
	public AccidentPollingData pollAccident(AccidentPollingRequestData pollAccidentData) throws ApplicationException;
	public AcknowledgeDataCollection reportMission(MissionReport missionReport) throws ApplicationException;
	
}
