package postech.itce.team8.action;

import com.opensymphony.xwork2.ActionSupport;

public class EnrollVoiceAction extends ActionSupport {

	private String userName;
	private int numberOfFiles;
	
	// getters & setters
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public int getNumberOfFiles() {
		return numberOfFiles;
	}

	public void setNumberOfFiles(int numberOfFiles) {
		this.numberOfFiles = numberOfFiles;
	}

	//
	@Override
	public String execute() throws Exception {

		System.out.println("DEBUG - EnrollVoiceAction: " + userName + " " + numberOfFiles);
		
		return SUCCESS;
	}

}
