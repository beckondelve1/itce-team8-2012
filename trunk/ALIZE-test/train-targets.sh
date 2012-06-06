

for name in FAML FDHH FEAB FHRO FJAZ FMEL FMEV FSLJ FTEJ FUAN; 
do 
	
	TrainTarget.exe --config ./cfg/target_female.cfg --targetIdList ./ndx/$name.ndx --inputWorldFilename world_all --debug false --verbose true
done

for name in MASM MCBR MFKC MKBP MLKH MMLP MMNA MNHP MOEW MPRA MREM MRKO MTLS;
do 
	
	TrainTarget.exe --config ./cfg/target_male.cfg --targetIdList ./ndx/$name.ndx --inputWorldFilename world_all --debug false --verbose true
done