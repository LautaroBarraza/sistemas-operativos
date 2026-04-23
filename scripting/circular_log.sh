#!/bin/bash


dir_base=logs
cant=3

if [ $(find "$dir_base"/"log_app.log" -size +1b | wc -l) -ge 1  ]; then

	if [ " $(ls -1 $dir_base |grep .zip | wc -l) " -ge $cant  ]; then
		slottofree="$(ls -1t $dir_base | grep .zip |tail -1)"
		rm $dir_base/$slottofree
	fi

	num="$(ls -1t $dir_base | grep .zip | head -1 | cut -d"-" -f1)"
	num="$(( num +1 ))"
	zip -m $dir_base/$num-log_app.zip $dir_base/log_app.log
	
fi




