ARCHIVO=$1
CARACTER=$2
ARCHIVO_TEMP="${ARCHIVO}.tmp.$$"

if [ $# -ne 2 ]; then 
	echo "faltan parametros" 
	exit 1
fi

tr '[0-9]' "$CARACTER" < "$ARCHIVO" > "$ARCHIVO_TEMP"

# 5. Reemplazo atómico del archivo original
mv "$ARCHIVO_TEMP" "$ARCHIVO"
