package postech.itce.team8.action;

import postech.itce.team8.action.util.OSCommand;

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
		
		//1.
		OSCommand.runSPro(userName, numberOfFiles);
		Thread.sleep(1000);
		//2.
		OSCommand.runNormFeatEnergy(userName, numberOfFiles);
		Thread.sleep(1000);
		//3.
		OSCommand.runEnergyDetector(userName, numberOfFiles);
		Thread.sleep(1000);
		//4.
		OSCommand.runReNormFeatEnergy(userName, numberOfFiles);
		Thread.sleep(1000);
		//5.
		OSCommand.runTrainTarget(userName, numberOfFiles);
		
		return SUCCESS;
	}

}
