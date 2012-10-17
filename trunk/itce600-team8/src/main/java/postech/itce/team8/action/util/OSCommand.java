package postech.itce.team8.action.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import postech.itce.team8.model.service.DoctorService;

public class OSCommand {
	
	
	public static final String ROOT_PATH = "E:\\caidat\\";
	public static final String SPRO_PATH = ROOT_PATH + "Audio-Processing\\SPro\\spro-4.0\\";
	public static final String WAVE_PATH = ROOT_PATH + "Apache\\apache-tomcat-6-spring\\webapps\\data\\";
	public static final String OUTPUT_PATH = ROOT_PATH + "Apache\\apache-tomcat-6-spring\\webapps\\data\\";
	/*
	public static final String ROOT_PATH = "D:\\setup\\";
	public static final String SPRO_PATH = ROOT_PATH + "Audio-Processing\\spro-4.0\\";
	public static final String WAVE_PATH = ROOT_PATH + "Apache\\apache-tomcat-6.0.35\\webapps\\data\\";
	public static final String OUTPUT_PATH = ROOT_PATH + "Apache\\apache-tomcat-6.0.35\\webapps\\data\\";
	*/
	
	//
	public static final String LIA_RAL_PATH = ROOT_PATH + "Audio-Processing\\ALIZE\\LIA_RAL\\bin\\";
	public static final String NORMFEAT_ENERGY_CFG = ROOT_PATH + "Audio-Processing\\ucungsr-TP-Biometrie\\cfg\\NormFeat_energy.cfg";
	//
	public static final String ENERGY_DETECTOR_CFG = ROOT_PATH + "Audio-Processing\\ucungsr-TP-Biometrie\\cfg\\EnergyDetector.cfg";
	//
	public static final String NORMFEAT_CFG = ROOT_PATH + "Audio-Processing\\ucungsr-TP-Biometrie\\cfg\\NormFeat.cfg";
	//
	public static final String TARGET_FEMALE_CFG = ROOT_PATH + "Audio-Processing\\ucungsr-TP-Biometrie\\cfg\\target_female.cfg";
	public static final String MIXTURE_FILES_PATH = ROOT_PATH + "Audio-Processing\\ucungsr-TP-Biometrie\\gmm\\";
	public static final String WORLD_MODEL_NAME = "world_all";
	//
	public static final String TARGET_SEG_FEMALE_CFG = ROOT_PATH + "Audio-Processing\\ucungsr-TP-Biometrie\\cfg\\target_seg_female.cfg";
	public static final String TARGET_LIST = "FAML FDHH FEAB FHRO FJAZ FMEL FMEV FSLJ FTEJ FUAN " +
			"MASM MCBR MFKC MKBP MLKH MMLP MMNA MNHP MOEW MPRA MREM MRKO MTLS ";
	
	
	//
	//runSPro
	public static void runSPro(String userName, int numberOfFiles) {
		try {
			String featureFilesPath = WAVE_PATH + userName + "\\prm\\";
			
			if (new File(featureFilesPath).exists() == false)
				new File(featureFilesPath).mkdir();
			
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
			//delete old .lbl file(s)
			String lblFiles[] = new File(labelFilesPath).list();
			for (String fileName:lblFiles){
				System.out.println(labelFilesPath + fileName);
				//new File(labelFilesPath + fileName).delete();
			}
			
			
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
			fileWriter.write(numberOfFiles-1 + "\n");
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
	
	
	//runComputeTest
	/**
	 * 
	 * @param userName
	 * @param loginId
	 * @return maxModel (userName)
	 */
	public static String runComputeTest(DoctorService doctorService, String userName, int loginId) {
		try {
			String waveLoginFilesPath = WAVE_PATH + userName + "\\login\\";
			String outputLoginScoresPath = WAVE_PATH + userName + "\\login\\";
			String featureLoginFilesPath = WAVE_PATH + userName + "\\prm\\login\\";
			String labelLoginFilesPath = WAVE_PATH + userName + "\\lbl\\login\\";
			String ndxLoginPath = WAVE_PATH + userName + "\\ndx\\login\\";
			
			//create xx-login folder(s)
			//no need to create [waveLoginFilesPath]
			
			if (new File(featureLoginFilesPath).exists() == false)
				new File(featureLoginFilesPath).mkdir();
			
			if (new File(labelLoginFilesPath).exists() == false)
				new File(labelLoginFilesPath).mkdir();
			
			if (new File(ndxLoginPath).exists() == false)
				new File(ndxLoginPath).mkdir();
			
			//1. run SPro
			if (new File(featureLoginFilesPath).exists() == false)
				new File(featureLoginFilesPath).mkdir();
			
			String command = "cmd /c " + SPRO_PATH + "sfbcep -F PCM16 -p 19 -e -D -A " +
					waveLoginFilesPath + Integer.toString(loginId) + ".wav " + 
					featureLoginFilesPath + Integer.toString(loginId) + ".prm";
			
			System.out.println("command=" + command);
					
			Process p1 = Runtime.getRuntime().exec(command);
			p1.waitFor();
			Thread.sleep(1000);
			
			//2. run NormFeatEnergy
			String lstLoginPath = WAVE_PATH + userName + "\\lst\\login\\";
			
			if (new File(lstLoginPath).exists() == false)
				new File(lstLoginPath).mkdir();
			
			//create .lst file, format: [login_id].lst
			FileWriter fileWriter = new FileWriter(lstLoginPath + loginId + ".lst");
			fileWriter.write(loginId + "\n");
			fileWriter.close();
			
			command = "cmd /c " + LIA_RAL_PATH + "NormFeat --config " + NORMFEAT_ENERGY_CFG +
					" --featureFilesPath " + featureLoginFilesPath + 
					" --inputFeatureFilename " + lstLoginPath + loginId + ".lst " +
					" --debug false --verbose true";
			
			System.out.println("command=" + command);
					
			Process p2 = Runtime.getRuntime().exec(command);
			p2.waitFor();
			Thread.sleep(1000);
			
			//3. run EnergyDetector
			//delete old .lbl file(s)
			String lblFiles[] = new File(labelLoginFilesPath).list();
			for (String fileName:lblFiles)
				new File(labelLoginFilesPath + fileName).delete();
			
			command = "cmd /c " + LIA_RAL_PATH + "EnergyDetector --config " + ENERGY_DETECTOR_CFG +
					" --featureFilesPath " + featureLoginFilesPath + 
					" --inputFeatureFilename " + lstLoginPath + loginId + ".lst " +
					" --labelFilesPath " + labelLoginFilesPath + 
					" --debug false --verbose true";
			
			System.out.println("command=" + command);
					
			Process p3 = Runtime.getRuntime().exec(command);
			p3.waitFor();
			Thread.sleep(1000);
			
			//4. run ReNormFeatEnergy
			command = "cmd /c " + LIA_RAL_PATH + "NormFeat --config " + NORMFEAT_CFG +
					" --featureFilesPath " + featureLoginFilesPath + 
					" --inputFeatureFilename " + lstLoginPath + loginId + ".lst " +
					" --labelFilesPath " + labelLoginFilesPath + 
					" --debug false --verbose true";
			
			System.out.println("command=" + command);
					
			Process p4 = Runtime.getRuntime().exec(command);
			p4.waitFor();
			Thread.sleep(1000);
			
			//5. run ComputeTest
			//create .ndx file, format: [loginId].ndx
			System.out.println(doctorService != null);
			System.out.println(doctorService.findDoctorUserNameList() != null);
			
			List<String> doctorUserNameList = doctorService.findDoctorUserNameList();
			
			fileWriter = new FileWriter(ndxLoginPath + loginId + ".ndx");
			fileWriter.write(loginId + " " + TARGET_LIST);
			for (String doctorUserName:doctorUserNameList)
				fileWriter.write(doctorUserName + " ");
			fileWriter.write("\n");
			fileWriter.close();
			
			command = "cmd /c " + LIA_RAL_PATH + "ComputeTest --config " + TARGET_SEG_FEMALE_CFG +
					" --ndxFilename " + ndxLoginPath + loginId + ".ndx" +
					" --inputWorldFilename " + WORLD_MODEL_NAME +
					" --outputFilename " + outputLoginScoresPath + loginId + ".res" +
					" --mixtureFilesPath " + MIXTURE_FILES_PATH + 
					" --featureFilesPath " + featureLoginFilesPath + 
					" --labelFilesPath " + labelLoginFilesPath + 
					" --debug false --verbose true --channelCompensation \"not set\"";
			
			System.out.println("command=" + command);
					
			Process p5 = Runtime.getRuntime().exec(command);
			p5.waitFor();
			
			//read score files and return maxModel
			BufferedReader br = new BufferedReader(new FileReader(outputLoginScoresPath + loginId + ".res"));
			String line;
			String maxModel = "";
			double maxScore = -1000000;
			while ((line = br.readLine()) != null) {
				String[] tokens = line.split(" ");
				String model = tokens[1];
				double score = Double.parseDouble(tokens[4]);
				if (score > maxScore){
					maxScore = score;
					maxModel = model;
				}
					
			}
			br.close();
			
			return maxModel;

			
			//6. ComputeNorm
			
			

		} catch (IOException e1) {
			return null;
		} catch (InterruptedException e2) {
			return null;
		}
	}
	
	
	
	
	//MAIN
	public static void main(String[] args) throws IOException{
		
//		String userName = "hiep";
//		int numberOfFiles = 3;
//		
//		String featureFilesPath = WAVE_PATH + userName + "\\prm\\";
//		
//		if (new File(featureFilesPath).exists() == false)
//			new File(featureFilesPath).mkdir();
//		
//		for (int i = 0;i < numberOfFiles;i++){
//		
//			String command = "cmd /c " + SPRO_PATH + "sfbcep -F PCM16 -p 19 -e -D -A " +
//					WAVE_PATH + userName + "\\" + Integer.toString(i) + ".wav " + 
//					OUTPUT_PATH + userName + "\\prm\\" + Integer.toString(i) + ".prm";
//			
//			System.out.println("command=" + command);
//					
//		}
//		
		
//		String userName = "hiep";
//		int numberOfFiles = 3;
//		
//		String lstPath = WAVE_PATH + userName + "\\lst\\";
//		String featureFilesPath = WAVE_PATH + userName + "\\prm\\";
//		
//		if (new File(lstPath).exists() == false)
//			new File(lstPath).mkdir();
//		
//		//create .lst file
//		FileWriter fileWriter = new FileWriter(lstPath + userName + ".lst");
//		for (int i = 0;i < numberOfFiles;i++)
//			fileWriter.write(i + "\n");
//		fileWriter.close();
//		
//		String command = "cmd /c " + LIA_RAL_PATH + "NormFeat --config " + NORMFEAT_ENERGY_CFG +
//				" --featureFilesPath " + featureFilesPath + 
//				" --inputFeatureFilename " + lstPath + userName + ".lst " +
//				" --debug false --verbose true";
//		
//		System.out.println("command=" + command);
		
		
		
		
//		String userName = "hiep";
//		
//		String featureFilesPath = WAVE_PATH + userName + "\\prm\\";
//		String labelFilesPath = WAVE_PATH + userName + "\\lbl\\";
//		String ndxPath = WAVE_PATH + userName + "\\ndx\\";
//		
//		System.out.println("cmd /c " + LIA_RAL_PATH + "TrainTarget --config " + TARGET_FEMALE_CFG +
//					" --targetIdList " + ndxPath + userName + ".ndx" +
//					" --featureFilesPath " + featureFilesPath + 
//					" --labelFilesPath " + labelFilesPath +
//					" --mixtureFilesPath " + MIXTURE_FILES_PATH + 
//					" --inputWorldFilename " + WORLD_MODEL_NAME + 
//					" --debug false --verbose true");
		
		
		
		
//		BufferedReader br = new BufferedReader(new FileReader("D:\\setup\\Apache\\apache-tomcat-6.0.35\\webapps\\data\\hiepnh\\login\\1.res"));
//		String line;
//		String maxModel = "";
//		double maxScore = -1000000;
//		while ((line = br.readLine()) != null) {
//			String[] tokens = line.split(" ");
//			String model = tokens[1];
//			double score = Double.parseDouble(tokens[4]);
//			if (score > maxScore){
//				maxScore = score;
//				maxModel = model;
//			}
//				
//		}
//		br.close();
//		
//		System.out.println("maxModel="+maxModel+" maxScore="+maxScore);
		
	}
	
}
