SPRO_PATH=/cygdrive/c/setup/Audio-Processing/SPro/spro-4.0
WAVE_PATH=/cygdrive/c/setup/Audio-Processing/ucungsr-read-only/training-samples
OUTPUT_PATH=prm

# WAVE_PATH=/cygdrive/c/setup/Audio-Processing/ucungsr-read-only/testing-samples
# OUTPUT_PATH=prm-test

for f in $WAVE_PATH/*.wav; 
do 
	filename=$(basename $f)
	extension=${filename##*.}
	filename=${filename%.*}
	
	$SPRO_PATH/sfbcep -F PCM16 -p 19 -e -D -A $WAVE_PATH/$filename.wav $OUTPUT_PATH/$filename.prm
done