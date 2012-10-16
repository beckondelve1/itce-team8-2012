package postech.itce.team8.action.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class OSCommand {
	//public static final String SPRO_PATH = "E:\\caidat\\Audio-Processing\\ucungsr-TP-Biometrie\\bin\\spro\\";
	public static final String SPRO_PATH = "E:\\caidat\\Audio-Processing\\SPro\\spro-4.0\\";
	public static final String WAVE_PATH = "E:\\caidat\\Apache\\apache-tomcat-6-spring\\webapps\\data\\";
	public static final String OUTPUT_PATH = "E:\\caidat\\Apache\\apache-tomcat-6-spring\\webapps\\data\\";
	//
	public static final String LIA_RAL_PATH = "E:\\caidat\\Audio-Processing\\ALIZE\\LIA_RAL\\bin\\";
	public static final String NORMFEAT_ENERGY_CFG = "E:\\caidat\\Audio-Processing\\ucungsr-TP-Biometrie\\cfg\\NormFeat_energy.cfg";
	//
	public static final String ENERGY_DETECTOR_CFG = "E:\\caidat\\Audio-Processing\\ucungsr-TP-Biometrie\\cfg\\EnergyDetector.cfg";
	//
	public static final String NORMFEAT_CFG = "E:\\caidat\\Audio-Processing\\ucungsr-TP-Biometrie\\cfg\\NormFeat.cfg";
	//
	public static final String TARGET_FEMALE_CFG = "E:\\caidat\\Audio-Processing\\ucungsr-TP-Biometrie\\cfg\\target_female.cfg";
	public static final String MIXTURE_FILES_PATH = "E:\\caidat\\Audio-Processing\\ucungsr-TP-Biometrie\\gmm\\";
	public static final String WORLD_MODEL_NAME = "world";
	
	//runSPro
	public static void runSPro(String userName, int numberOfFiles) {
		try {
			if (new File(OUTPUT_PATH + userName + "\\prm\\").exists() == false)
				new File(OUTPUT_PATH + userName + "\\prm\\").mkdir();
			
			for (int i = 0;i < numberOfFiles;i++){
			
				String command = "cmd /c " + SPRO_PATH + "sfbcep -F PCM16 -p 19 -e -D -A " +
						WAVE_PATH + userName + "\\" + Integer.toString(i) + ".wav " + 
						OUTPUT_PATH + userName + "\\prm\\" + Integer.toString(i) + ".prm";
				
				System.out.println("command=" + command);
						
				Process p = Runtime.getRuntime().exec(command);
				p.waitFor();
				/*
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line = reader.readLine();
				while (line != null) {
					System.out.println(line);
					line = reader.readLine();
				}
				*/
			}

		} catch (IOException e1) {
		} catch (InterruptedException e2) {
		}
	}
	
	//runNormFeatEnergy
	public static void runNormFeatEnergy(String userName, int numberOfFiles) {
		try {
			String lstPath = WAVE_PATH + userName + "\\lst\\";
			String featureFilesPath = WAVE_PATH + userName + "\\prm\\";
			
			if (new File(lstPath).exists() == false)
				new File(lstPath).mkdir();
			
			//create .lst file
			FileWriter fileWriter = new FileWriter(lstPath + userName + ".lst");
			for (int i = 0;i < numberOfFiles;i++)
				fileWriter.write(i + "\n");
			fileWriter.close();
			
			String command = "cmd /c " + LIA_RAL_PATH + "NormFeat --config " + NORMFEAT_ENERGY_CFG +
					" --featureFilesPath " + featureFilesPath + 
					" --inputFeatureFilename " + lstPath + userName + ".lst " +
					" --debug false --verbose true";
			
			System.out.println("command=" + command);
					
			Process p = Runtime.getRuntime().exec(command);
			p.waitFor();

		} catch (IOException e1) {
		} catch (InterruptedException e2) {
		}
	}
	
	//runEnergyDetector
	public static void runEnergyDetector(String userName, int numberOfFiles) {
		try {
			String lstPath = WAVE_PATH + userName + "\\lst\\";
			String featureFilesPath = WAVE_PATH + userName + "\\prm\\";
			String labelFilesPath = WAVE_PATH + userName + "\\lbl\\";
			
			if (new File(labelFilesPath).exists() == false)
				new File(labelFilesPath).mkdir();
			
			String command = "cmd /c " + LIA_RAL_PATH + "EnergyDetector --config " + ENERGY_DETECTOR_CFG +
					" --featureFilesPath " + featureFilesPath + 
					" --inputFeatureFilename " + lstPath + userName + ".lst " +
					" --labelFilesPath " + labelFilesPath + 
					" --debug false --verbose true";
			
			System.out.println("command=" + command);
					
			Process p = Runtime.getRuntime().exec(command);
			p.waitFor();

		} catch (IOException e1) {
		} catch (InterruptedException e2) {
		}
	}
	
	
	//runReNormFeatEnergy
	public static void runReNormFeatEnergy(String userName, int numberOfFiles) {
		try {
			String lstPath = WAVE_PATH + userName + "\\lst\\";
			String featureFilesPath = WAVE_PATH + userName + "\\prm\\";
			String labelFilesPath = WAVE_PATH + userName + "\\lbl\\";
			
			String command = "cmd /c " + LIA_RAL_PATH + "NormFeat --config " + NORMFEAT_CFG +
					" --featureFilesPath " + featureFilesPath + 
					" --inputFeatureFilename " + lstPath + userName + ".lst " +
					" --labelFilesPath " + labelFilesPath + 
					" --debug false --verbose true";
			
			System.out.println("command=" + command);
					
			Process p = Runtime.getRuntime().exec(command);
			p.waitFor();

		} catch (IOException e1) {
		} catch (InterruptedException e2) {
		}
	}
	
	//runTrainTarget
	public static void runTrainTarget(String userName, int numberOfFiles) {
		try {
			String featureFilesPath = WAVE_PATH + userName + "\\prm\\";
			String labelFilesPath = WAVE_PATH + userName + "\\lbl\\";
			String ndxPath = WAVE_PATH + userName + "\\ndx\\";
			
			if (new File(ndxPath).exists() == false)
				new File(ndxPath).mkdir();
			
			//create .ndx file
			FileWriter fileWriter = new FileWriter(ndxPath + userName + ".ndx");
			fileWriter.write(userName + " ");
			for (int i = 0;i < numberOfFiles-1;i++)
				fileWriter.write(i + " ");
			fileWriter.write(numberOfFiles + "\n");
			fileWriter.close();
			
			String command = "cmd /c " + LIA_RAL_PATH + "TrainTarget --config " + TARGET_FEMALE_CFG +
					" --targetIdList " + ndxPath + userName + ".ndx" +
					" --featureFilesPath " + featureFilesPath + 
					" --labelFilesPath " + labelFilesPath +
					" --mixtureFilesPath " + MIXTURE_FILES_PATH + 
					" --inputWorldFilename " + WORLD_MODEL_NAME + 
					" --debug false --verbose true";
			
			System.out.println("command=" + command);
					
			Process p = Runtime.getRuntime().exec(command);
			p.waitFor();

		} catch (IOException e1) {
		} catch (InterruptedException e2) {
		}
	}
	
	
	//MAIN
	public static void main(String[] args) {
		String userName = "hiep";
		
		String featureFilesPath = WAVE_PATH + userName + "\\prm\\";
		String labelFilesPath = WAVE_PATH + userName + "\\lbl\\";
		String ndxPath = WAVE_PATH + userName + "\\ndx\\";
		
		System.out.println("cmd /c " + LIA_RAL_PATH + "TrainTarget --config " + TARGET_FEMALE_CFG +
					" --targetIdList " + ndxPath + userName + ".ndx" +
					" --featureFilesPath " + featureFilesPath + 
					" --labelFilesPath " + labelFilesPath +
					" --mixtureFilesPath " + MIXTURE_FILES_PATH + 
					" --inputWorldFilename " + WORLD_MODEL_NAME + 
					" --debug false --verbose true");
	}
	
}
