package postech.itce.teleconsultation.medicalrecord;

public class Patient {
	public static final String TAG = "Patient";
	
	public static final int PATIENT_SEX_MALE = 1;
	public static final int PATIENT_SEX_FEMALE = 2;
	
	protected class MedicalRecord {
		public static final String TAG = "MedicalRecord";
		
		private String dateTime;
		private String doctorName;
		private int pulse;
		private int bloodPressure;
		private int bloodSugar;
		private int spO2;
		private String diagnosis;
	}
	
	private String id, name, ipAddress, streamUrl;
	private int sex, age, height, weight;
	private MedicalRecord records[];
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
	
	public void setIpAddress(String ip) {
		this.ipAddress = ip;
	}
	
	public String getStreamUrl() {
		return streamUrl;
	}
	
	public void setStreamUrl(String url) {
		this.streamUrl = url;
	}
	
}
