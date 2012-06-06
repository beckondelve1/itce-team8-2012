SPRO_PATH=/cygdrive/d/setup/Audio-Processing/spro-4.0
WAVE_PATH=/cygdrive/d/setup/Audio-Processing/MARF/apps/SpeakerIdentApp/training-samples
OUTPUT_PATH=/cygdrive/d/setup/Audio-Processing/ucungsr-TP-Biometrie/prm-test

for f in $WAVE_PATH/*.wav; 
do 
	filename=$(basename $f)
	extension=${filename##*.}
	filename=${filename%.*}
	
	$SPRO_PATH/sfbcep -F PCM16 -p 19 -e -D -A $WAVE_PATH/$filename.wav $OUTPUT_PATH/$filename.prm
done