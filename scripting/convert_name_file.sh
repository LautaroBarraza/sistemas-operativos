directorio="${1:-.}"
cant=0
if [ ! -d "$directorio" ]; then
	echo "Error: el directorio no existe"
	exit 1
fi

for archivo in "$directorio"/*; do
	[ -f "$archivo" ] || continue
	
	nombre="${archivo##*/}"
	base="${archivo%/*}"
	
	nom_limpio=$(echo "${nombre// /_}" | tr '[:upper:]' '[:lower:]')

	if [ "$nombre" != "$nom_limpio" ]; then
		if [ -e "$base/$nom_limpio" ]; then
			echo "ya existe ese destino"
		else
			mv "$archivo" "$base/$nom_limpio"
			echo se renombro
			let cant++
		fi
	fi
done
echo $cant
