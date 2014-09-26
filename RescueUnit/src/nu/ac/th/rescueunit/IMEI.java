package nu.ac.th.rescueunit;

public class IMEI {
	private static final String IMEI = "UNKNOWN";
		
		private String rImei;
		
		public IMEI(){
			this(IMEI);
		}
		
		public IMEI(String imei) {
			this.rImei = imei;
		}
		
		public String getImei(){
			return rImei;
		}
		
		public void setImei(String imei){
			rImei = imei;
		}
}
