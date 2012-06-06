NDX_PATH=/cygdrive/d/setup/Audio-Processing/ucungsr-TP-Biometrie/ndx/ucungsr

for f in $NDX_PATH/*.ndx; 
do 
	filename=$(basename $f)
	extension=${filename##*.}
	filename=${filename%.*}
	
	ComputeTest.exe --config ./cfg/target_seg_female.cfg  --ndxFilename ./ndx/ucungsr/$filename.ndx --inputWorldFilename world_all --outputFilename ./res/ucungsr/$filename.res  --debug false --verbose true --channelCompensation "not set"
done