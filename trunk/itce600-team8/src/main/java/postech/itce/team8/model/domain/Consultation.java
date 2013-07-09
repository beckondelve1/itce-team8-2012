package postech.itce.team8.model.domain;

import java.util.Date;

public class Consultation {
	private int id;
	private int doctorId;
	private String doctorCurrentIp;
	private int patientId;
	private String patientFullName;
	private String patientCurrentIp;
	private String defaultURL;	// RTPS://<patientCurrentIp> + : + defaultURL, e.g. "554/axis-media/media.amp?videocodec=h264&streamprofile=hiep"
	private String cameraUser;
	private String cameraPass;
	private Date startTime;
	private Date endTime;
	private Date expectedTime;
	private String status;		//NEW, IN PROGRESS, COMPLETED, CANCELED
	//Ctor
	public Consultation(){
		
	}
	
	
	//
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getExpectedTime() {
		return expectedTime;
	}
	public void setExpectedTime(Date expectedTime) {
		this.expectedTime = expectedTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


	public String getPatientFullName() {
		return patientFullName;
	}


	public void setPatientFullName(String patientFullName) {
		this.patientFullName = patientFullName;
	}


	public String getDoctorCurrentIp() {
		return doctorCurrentIp;
	}


	public void setDoctorCurrentIp(String doctorCurrentIp) {
		this.doctorCurrentIp = doctorCurrentIp;
	}


	public String getPatientCurrentIp() {
		return patientCurrentIp;
	}


	public void setPatientCurrentIp(String patientCurrentIp) {
		this.patientCurrentIp = patientCurrentIp;
	}


	public String getDefaultURL() {
		return defaultURL;
	}


	public void setDefaultURL(String defaultURL) {
		this.defaultURL = defaultURL;
	}


	public String getCameraUser() {
		return cameraUser;
	}


	public void setCameraUser(String cameraUser) {
		this.cameraUser = cameraUser;
	}


	public String getCameraPass() {
		return cameraPass;
	}


	public void setCameraPass(String cameraPass) {
		this.cameraPass = cameraPass;
	}
	
	
	
}
