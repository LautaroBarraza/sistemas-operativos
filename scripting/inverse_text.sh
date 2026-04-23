archivo=$1
temp="${archivo}.tmp.$$"
if [ $# -ne 1 ]; then
	echo "parametros incorrectos"
	exit 1
fi

tac "$archivo" > "$temp"
mv "$temp" "$archivo"
