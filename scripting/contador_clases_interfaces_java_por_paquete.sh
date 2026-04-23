DIR="${1:-.}"

find "$DIR" -name "*.java" -exec dirname {} \;|sort -u | while read -r directorio; do
	conteo=$(grep "^import " "$directorio"/*.java | sed  's/import //;s/;//g' |sort -u | wc -l)
	echo "$conteo" "$directorio"
done
