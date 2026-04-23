#!/bin/bash


directorio="./logs"
cantidad=3


if [ (find "$directorio"/log_app.log -size +1b | wc -l) -ge 1 ]; then 
	

fi
