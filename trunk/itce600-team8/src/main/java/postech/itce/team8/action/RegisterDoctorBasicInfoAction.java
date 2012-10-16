package postech.itce.team8.action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import postech.itce.team8.model.domain.Doctor;
import postech.itce.team8.model.service.DoctorService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("default")
public class RegisterDoctorBasicInfoAction extends ActionSupport {
	
	Logger logger = Logger.getLogger(RegisterDoctorBasicInfoAction.class);
	
	@Autowired
	private DoctorService doctorService;
	
	//beans
	private Doctor doctor;
	
	
	
	//getters & setters
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	//actions
	public String prepareDoctorBasicInfo(){
		
		return SUCCESS;
	}

	public String insertDoctorBasicInfo(){
		
		doctorService.insertDoctor(doctor);
		
		return SUCCESS;
	}
}
