#!/bin/bash

if [ $# -eq 0 ]; then
	echo 'Error'
	exit 1
fi


for archivo in "$@"; do
	extension="${archivo##*.}"
	case "$extension" in
		"gz")
			gunzip "$archivo"
			echo "se descomprimio el gz"
		;;
		"bz2")
			bunzip2 "$archivo"
			echo "se descomprimio el bz2"
		;;
		"zip")
			unzip "$archivo"
			echo "se descomprimio el zip"
		;;
		"tar")
			tar -xvf "$archivo"
			echo "se descomprimio el tar"
		;;

		*)
			echo "no es un archivo comprimido"
		;;
	esac
done
