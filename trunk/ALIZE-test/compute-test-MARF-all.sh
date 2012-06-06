NDX_PATH=/cygdrive/d/setup/Audio-Processing/ucungsr-TP-Biometrie/ndx/MARF

for f in $NDX_PATH/*.ndx; 
do 
	filename=$(basename $f)
	extension=${filename##*.}
	filename=${filename%.*}
	
	ComputeTest.exe --config ./cfg/target_seg_female.cfg  --ndxFilename ./ndx/MARF/$filename.ndx --inputWorldFilename world_all --outputFilename ./res/MARF/$filename.res  --debug false --verbose true --channelCompensation "not set"
done