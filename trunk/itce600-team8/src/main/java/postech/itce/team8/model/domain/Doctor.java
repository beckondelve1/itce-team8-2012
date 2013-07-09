package postech.itce.team8.model.domain;

public class Doctor {

	private int id;
	private String fullName;
	private String userName;
	private String password;
	private String currentIp;
	private int lastLoginId;
	

	// ctr
	public Doctor() {

	}

	public Doctor(String fullName, String userName, String password) {
		super();
		this.fullName = fullName;
		this.userName = userName;
		this.password = password;
	}

	public Doctor(String fullName, String userName, String password,
			int lastLoginId) {
		super();
		this.fullName = fullName;
		this.userName = userName;
		this.password = password;
		this.lastLoginId = lastLoginId;
	}
	
	public Doctor(String fullName, String userName, String password,
			String currentIp, int lastLoginId) {
		super();
		this.fullName = fullName;
		this.userName = userName;
		this.password = password;
		this.currentIp = currentIp;
		this.lastLoginId = lastLoginId;
	}

	//
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

	public int getLastLoginId() {
		return lastLoginId;
	}

	public void setLastLoginId(int lastLoginId) {
		this.lastLoginId = lastLoginId;
	}

	public String getCurrentIp() {
		return currentIp;
	}

	public void setCurrentIp(String currentIp) {
		this.currentIp = currentIp;
	}

	
}
