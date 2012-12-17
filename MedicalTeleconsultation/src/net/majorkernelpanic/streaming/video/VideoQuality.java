/*
 * Copyright (C) 2011 GUIGUI Simon, fyhertz@gmail.com
 * 
 * This file is part of Spydroid (http://code.google.com/p/spydroid-ipcamera/)
 * 
 * Spydroid is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This source code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this source code; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package net.majorkernelpanic.streaming.video;

import android.os.*;
import android.util.*;

public class VideoQuality {
	
	// ADDED: Dongwoo Kwon
	// In case of Samsung Galaxy Tab, orientation value should be set 0. If not, set 90.
	// Array of { Build.MANUFACTURER, Build.MODEL }
	public final static String[][] NO_NEED_FOR_CAMERA_PREVIEW_ROTATION = { 
		{ "SAMSUNG", "GT-P7500" },		// Samsung Galaxy Tab 10.1
	};
	
	public int frameRate = 0;
	public int bitRate = 0;
	public int resX = 0;
	public int resY = 0;
	public int orientation = 0;	//HiepNH - old: 90
	
	/** Default video stream quality */
	public final static VideoQuality defaultVideoQualiy = new VideoQuality(640,480,15,300000);

	public VideoQuality() {
		for (int i = 0; i < NO_NEED_FOR_CAMERA_PREVIEW_ROTATION.length; i++) {
			if (Build.MANUFACTURER.equalsIgnoreCase(NO_NEED_FOR_CAMERA_PREVIEW_ROTATION[i][0]) && 
					Build.MODEL.equalsIgnoreCase(NO_NEED_FOR_CAMERA_PREVIEW_ROTATION[i][1])) {
				this.orientation = 0;
				break;
			}
		}
	}

	public VideoQuality(int resX, int resY, int frameRate, int bitRate) {
		this();

		this.frameRate = frameRate;
		this.bitRate = bitRate;
		this.resX = resX;
		this.resY = resY;
		merge(this,defaultVideoQualiy);
	}
	
	public boolean equals(VideoQuality quality) {
		if (quality==null) return false;
		return (quality.resX == this.resX 				&
				quality.resY == this.resY 				&
				quality.frameRate == this.frameRate	&
				quality.bitRate == this.bitRate 		);
	}

	public VideoQuality clone() {
		return new VideoQuality(resX,resY,frameRate,bitRate);
	}

	public static VideoQuality parseQuality(String str) {
		VideoQuality quality = new VideoQuality(0,0,0,0);
		if (str != null) {
			String[] config = str.split("-");
			try {
				quality.bitRate = Integer.parseInt(config[0])*1000; // conversion to bit/s
				quality.frameRate = Integer.parseInt(config[1]);
				quality.resX = Integer.parseInt(config[2]);
				quality.resY = Integer.parseInt(config[3]);
			}
			catch (IndexOutOfBoundsException ignore) {}
		}
		return quality;
	}

	public static void merge(VideoQuality videoQuality, VideoQuality defaultVideoQuality) {
		if (videoQuality.resX==0) videoQuality.resX = defaultVideoQuality.resX;
		if (videoQuality.resY==0) videoQuality.resY = defaultVideoQuality.resY;
		if (videoQuality.frameRate==0) videoQuality.frameRate = defaultVideoQuality.frameRate;
		if (videoQuality.bitRate==0) videoQuality.bitRate = defaultVideoQuality.bitRate;
	}

}
