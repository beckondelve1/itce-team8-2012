package postech.itce.team8.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import postech.itce.team8.model.domain.Patient;
import postech.itce.team8.model.service.PatientService;

import com.opensymphony.xwork2.ActionSupport;

public class PatientAction extends ActionSupport {

	Logger logger = Logger.getLogger(PatientAction.class);
	
	@Autowired
	private PatientService patientService;
	
	//beans, fields
	private String doctorName; 
	private List<Patient> patientList;
	
	//ctor
	
	
	//actions
	public String findPatientsByDoctorName(){
		patientList = patientService.findPatientsByDoctorName(doctorName);
		
		return SUCCESS;
	}
	
	//getters & setters
	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public List<Patient> getPatientList() {
		return patientList;
	}

	public void setPatientList(List<Patient> patientList) {
		this.patientList = patientList;
	}
}
