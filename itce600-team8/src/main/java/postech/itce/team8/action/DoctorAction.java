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
	
	//
	private String userName;
	private String currentIp;
	
	//beans
	private Doctor doctor;
	
	
	
	//getters & setters
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCurrentIp() {
		return currentIp;
	}

	public void setCurrentIp(String currentIp) {
		this.currentIp = currentIp;
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

	//
	public String registerIp(){
		logger.info("registerIp CALLED");
		
		Doctor doctor = doctorService.findDoctorByUserName(userName);
		logger.info("userName=" + doctor.getUserName() + "|");
		logger.info("(old)currentIp=" + doctor.getCurrentIp() + "|");
		
		doctor.setCurrentIp(currentIp);
		logger.info("(new)currentIp=" + doctor.getCurrentIp() + "|");
		
		doctorService.updateDoctor(doctor);
		
		return SUCCESS;
	}
}
