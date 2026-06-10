#!/bin/bash

directorio="$1"

procesar_nodos() {

	while IFS= read -r -d '' ruta; do
		dir="${ruta#./}"
		profundidad=$(echo -n "$dir" | tr -cd "/" | wc -c)
		ult="${dir##*/}"
		indentacion=""
		for ((i=0; i<profundidad; i++)); do
			indentacion+="	"
			if [ $profundidad -eq $i ]; then
				indentacion+="*"
			fi
		done
		echo "${indentacion}*$ult"
	done
}

buscar_directorio() {
	local target="$1"
	find "$target" -type d -print0 | sort | procesar_nodos
}

buscar_archivos() {
	local target="$1"
	find "$target" -print0 | sort | procesar_nodos
}


modo="$1"
directorio="$2"

case "$modo" in 
	-d)
		buscar_directorio "$directorio"
	;;
	-f)
		buscar_archivos "$directorio"
	;;

	*)
	echo "parametro '$modo' incorrecto"
	exit 1
	;;

esac

