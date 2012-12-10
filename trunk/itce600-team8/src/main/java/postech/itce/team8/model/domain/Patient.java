package postech.itce.team8.model.domain;

public class Patient {
	private int id;
	private String fullName;
	private String userName;
	private String password;
	private String address;
	private String ipAddress;
	private String defaultProfileURL;
	private String cameraUser;
	private String cameraPass;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDefaultProfileURL() {
		return defaultProfileURL;
	}

	public void setDefaultProfileURL(String defaultProfileURL) {
		this.defaultProfileURL = defaultProfileURL;
	}

	public String getCameraUser() {
		return cameraUser;
	}

	public void setCameraUser(String cameraUser) {
		this.cameraUser = cameraUser;
	}

	public String getCameraPass() {
		return cameraPass;
	}

	public void setCameraPass(String cameraPass) {
		this.cameraPass = cameraPass;
	}

	// Ctor

	//

}
