package postech.itce.team8.action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import postech.itce.team8.model.domain.Doctor;
import postech.itce.team8.model.service.DoctorService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("default")
public class DoctorAction extends ActionSupport {
	
	Logger logger = Logger.getLogger(DoctorAction.class);
	
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
	public String loginPassword(){
		logger.info("userName=" + doctor.getUserName() + "|");
		logger.info("password=" + doctor.getPassword() + "|");
		
		if (doctorService.isDoctorLoginExisted(doctor))
			return SUCCESS;
		else
			return ERROR;
	}

}
