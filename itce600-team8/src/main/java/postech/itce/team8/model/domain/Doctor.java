package postech.itce.team8.model.domain;

public class Doctor {

	private int id;
	private String fullName;
	private String userName;
	private String password;
	
	//ctr
	public Doctor(String fullName, String userName, String password) {
		super();
		this.fullName = fullName;
		this.userName = userName;
		this.password = password;
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
	
	
	
}
