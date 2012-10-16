package postech.itce.team8.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class FileUploadAction extends ActionSupport implements ServletRequestAware {

	private File fileUpload;
	private String fileUploadContentType;
	private String fileUploadFileName;
	private HttpServletRequest request;
	
	//
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

	//
	public String execute() throws Exception {
		String path = request.getSession().getServletContext().getRealPath("/");
		
		System.out.println("Real path = " + path);
		path = path + "/../data";
		
		File fileToCreate = null;
		try {
			String filePath = path;
			fileToCreate = new File(filePath, this.fileUploadFileName);
			FileUtils.copyFile(this.fileUpload, fileToCreate);
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}

		return SUCCESS;

	}

	public String display() {
		return NONE;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

}
