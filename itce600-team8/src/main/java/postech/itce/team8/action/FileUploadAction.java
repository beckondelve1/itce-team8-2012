package postech.itce.team8.action;

import java.io.File;
import java.io.InputStream;
import java.io.StringBufferInputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import postech.itce.team8.action.util.OSCommand;
import postech.itce.team8.model.domain.Doctor;
import postech.itce.team8.model.service.DoctorService;

import com.opensymphony.xwork2.ActionSupport;

public class FileUploadAction extends ActionSupport implements
		ServletRequestAware {

	@Autowired
	private DoctorService doctorService;

	//
	private File fileUpload;
	private String fileUploadContentType;
	private String fileUploadFileName;
	//
	private HttpServletRequest request;
	//
	private String userName;
	private InputStream inputStream;

	// getters & setters
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getFileUploadContentType() {
		return fileUploadContentType;
	}

	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	// 1.
	public String execute() throws Exception {
		//String path = request.getSession().getServletContext().getRealPath("/");
		String path = OSCommand.WAVE_PATH;

		System.out.println("Real path = " + path);
		path = path + "\\" + getUserName(); // save to user's folder
		if (new File(path).exists() == false)
			new File(path).mkdir();
		
		
		System.out.println("path = " + path);

		// TODO make user folder
		File fileToCreate = null;
		try {
			String filePath = path;
			// 0.wav, 1.wav,...
			if (!this.fileUploadFileName.equals("temp.wav")) {
				fileToCreate = new File(filePath, this.fileUploadFileName);
				FileUtils.copyFile(this.fileUpload, fileToCreate);
				
			} else { // temp.wav: move and rename it to
						// /[userName]/login/[lastLoginId].wav
				Doctor doctor = doctorService.findDoctorByUserName(userName);
				int lastLoginId = doctor.getLastLoginId();
				lastLoginId++;
				doctor.setLastLoginId(lastLoginId);

				doctorService.updateDoctor(doctor);
				//
				if (new File(filePath + "\\login").exists() == false)
					new File(filePath + "\\login").mkdir();
				
				fileToCreate = new File(filePath + "\\login", Integer.toString(lastLoginId) + ".wav");
				FileUtils.copyFile(this.fileUpload, fileToCreate);

				//
				inputStream = new StringBufferInputStream(Integer.toString(lastLoginId));
				return LOGIN;
			}

		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}

		return SUCCESS;

	}

	// 2.
	public String display() {
		return NONE;
	}

	// 3.
	public String finishUpload() {
		return "enrollVoice";
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

}
