
WAVE_PATH=/cygdrive/d/setup/Audio-Processing/MARF/apps/SpeakerIdentApp/training-samples
# WAVE_PATH=/cygdrive/c/setup/Audio-Processing/ucungsr-read-only/training-samples
# WAVE_PATH=/cygdrive/c/setup/Audio-Processing/ucungsr-read-only/testing-samples

for f in $WAVE_PATH/*.wav; 
do 
	filename=$(basename $f)
	extension=${filename##*.}
	filename=${filename%.*}
	
	echo $filename
done