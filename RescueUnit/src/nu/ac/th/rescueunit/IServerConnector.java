package nu.ac.th.rescueunit;

public interface IServerConnector {	
	public AcknowledgeDataCollection updateRescueUnit(SelfDataCollection selfDataCollection) throws ApplicationException;
	public AccidentPollingData pollAccident(AccidentPollingRequestData pollAccidentData) throws ApplicationException;
	public AcknowledgeDataCollection reportMission(MissionReport missionReport) throws ApplicationException;
	
}
