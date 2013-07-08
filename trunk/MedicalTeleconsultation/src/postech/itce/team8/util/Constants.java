package postech.itce.team8.util;

public class Constants {

	// 119.202.84.55
	// 172.168.139.194
	
	public static String SERVER_ADDR = "http://172.168.151.253:8080/";

	public static String LOGIN_PASSWORD_URL = SERVER_ADDR + "itce600-team8/doctor/loginPassword.do";
	public static String UPLOAD_URL = SERVER_ADDR + "itce600-team8/doctor/executeUpload.do";
	public static String ENROLL_URL = SERVER_ADDR + "itce600-team8/doctor/enrollVoice.do";
	public static String IDENTIFY_URL = SERVER_ADDR + "itce600-team8/doctor/identifyVoice.do";
	public static String AGENDA_URL = SERVER_ADDR + "itce600-team8/consultation/findConsultationsByDoctorName.do";
	
	//
	public static void updateURLs(String serverAddress){
		SERVER_ADDR = serverAddress;
		LOGIN_PASSWORD_URL = SERVER_ADDR + "itce600-team8/doctor/loginPassword.do";
		UPLOAD_URL = SERVER_ADDR + "itce600-team8/doctor/executeUpload.do";
		ENROLL_URL = SERVER_ADDR + "itce600-team8/doctor/enrollVoice.do";
		IDENTIFY_URL = SERVER_ADDR + "itce600-team8/doctor/identifyVoice.do";
		AGENDA_URL = SERVER_ADDR + "itce600-team8/consultation/findConsultationsByDoctorName.do";
	}
}
