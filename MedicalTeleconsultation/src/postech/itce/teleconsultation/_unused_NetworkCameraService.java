package postech.itce.teleconsultation;

import android.util.*;

public class _unused_NetworkCameraService {
	private _unused_OnvifService Onvif = null;
	private String CameraIp;
	
	public _unused_NetworkCameraService() {}
	
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
		
		String response = this.Onvif.getStreamUri("RTP-Unicast", "UDP", "bandwidth_h264");
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
		if(this.Onvif == null)
			return null;
		
		return "rtsp://onvifuser:itce500@" + this.CameraIp + "/onvif-media/media.amp?profile=" + profile + "&amp;sessiontimeout=" + String.valueOf(timeout) + "&amp;streamtype=unicast";
	}
}
