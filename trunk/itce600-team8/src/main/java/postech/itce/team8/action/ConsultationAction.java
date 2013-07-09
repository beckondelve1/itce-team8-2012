package postech.itce.team8.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import postech.itce.team8.model.domain.Consultation;
import postech.itce.team8.model.domain.Patient;
import postech.itce.team8.model.service.ConsultationService;
import postech.itce.team8.model.service.PatientService;

import com.opensymphony.xwork2.ActionSupport;

public class ConsultationAction extends ActionSupport {

	Logger logger = Logger.getLogger(ConsultationAction.class);
	
	@Autowired
	private ConsultationService consultationService;
	
	//beans, fields
	private String doctorName; 
	private List<Consultation> consultationList;
	
	//ctor
	
	
	//actions
	public String findConsultationsByDoctorName(){
		logger.info("PARAM: doctorName = " + doctorName);
		System.out.println("PARAM: doctorName = " + doctorName);
		
		consultationList = consultationService.findConsultationsByDoctorName(doctorName);
		
		return SUCCESS;
	}
	
	//getters & setters
	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public List<Consultation> getConsultationList() {
		return consultationList;
	}

	public void setConsultationList(List<Consultation> consultationList) {
		this.consultationList = consultationList;
	}
}
