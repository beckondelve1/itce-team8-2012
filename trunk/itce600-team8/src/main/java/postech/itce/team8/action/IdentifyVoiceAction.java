package postech.itce.team8.action;

import org.springframework.beans.factory.annotation.Autowired;

import postech.itce.team8.action.util.OSCommand;
import postech.itce.team8.model.service.DoctorService;

import com.opensymphony.xwork2.ActionSupport;

public class IdentifyVoiceAction extends ActionSupport {

	private String userName;
	private int loginId;
	
	@Autowired
	private DoctorService doctorService;

	// getters & setters
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	//
	@Override
	public String execute() throws Exception {

		System.out.println("DEBUG - IdentifyVoiceAction: " + userName + " "
				+ loginId);

		String maxModel = OSCommand.runComputeTest(doctorService, userName, loginId);
		
		if (maxModel.equals(userName)){
			System.out.println("Voice Identification reasult for " + userName + " : SUCCESS !");
			return SUCCESS;
		}else{
			System.out.println("Voice Identification reasult for " + userName + " : ERROR !");
			return ERROR;
		}
	}

}
