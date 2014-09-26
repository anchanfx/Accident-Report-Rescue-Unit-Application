package nu.ac.th.rescueunit;

public interface IServerConnector {	
	public  AcknowledgeDataCollection sendReport(ReportDataCollection reportDataCollection) throws ApplicationException;
}
