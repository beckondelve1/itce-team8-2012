package postech.itce.teleconsultation;

import android.util.*;

public class NetworkCameraService {
	private OnvifService Onvif = null;
	private String CameraIp;
	private String profileName;
	
	public NetworkCameraService() {}
	
	public String getCameraIp() {
		return this.CameraIp;
	}
	
	public boolean setCameraIp(String cameraip) {
		if(cameraip == null || cameraip == "") {
			return false;
		}
		
		/*
		this.Onvif = new OnvifService(cameraip);
		String response = Onvif.getDeviceInformation();
		if(response != null) {
			Log.i("Onvif_RSP", response);
			this.CameraIp = cameraip;
		} else {
			this.CameraIp = null;
			this.Onvif = null;
			return false;
		}
		*/
		this.CameraIp = cameraip;
		
		return true;
	}
	
	public String getDeviceInformation() {
		if(this.Onvif == null)
			return null;
		
		String response = this.Onvif.getDeviceInformation();
		if(response != null)
			Log.i("Onvif_RSP", response);
		
		return response;
	}
	
	public String getStreamUrl() {
		if(this.Onvif == null)
			return null;
		
		String response = this.Onvif.getStreamUri("RTP-Unicast", "UDP", "hiep");
		if(response != null)
			Log.i("Onvif_RSP", response);
		
		return response;
	}
	
	public String getHttpStreamUrl() {
		return "http://" + this.CameraIp;
	}
	
	public String getStreamUrlAxis(String profile) {
		return getStreamUrlAxis(profile, 60);
	}
	
	public String getStreamUrlAxis(String profile, int timeout) {
		
		/*
		if(this.Onvif == null)
			return null;
		
		return "rtsp://onvifuser:itce500@" + this.CameraIp + "/onvif-media/media.amp?profile=" + profile + "&amp;sessiontimeout=" + String.valueOf(timeout) + "&amp;streamtype=unicast";
		*/
		
		Log.d("getStreamUrlAxis","rtsp://admin:4321@" + this.CameraIp + ":554/onvif/" + getProfileName() + "/media.smp" + "&amp;sessiontimeout=" + String.valueOf(timeout) + "&amp;streamtype=unicast");
		return "rtsp://admin:4321@" + this.CameraIp + ":554/onvif/" + getProfileName() + "/media.smp";
		
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	
	
}
