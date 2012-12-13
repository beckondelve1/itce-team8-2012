package postech.itce.team8.model.domain;

import java.util.Date;

public class Consultation {
	private int id;
	private int doctorId;
	private int patientId;
	private String patientFullName;
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
	
	
}
