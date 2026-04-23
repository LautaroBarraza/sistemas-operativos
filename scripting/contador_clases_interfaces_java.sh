

DIR="${1:-.}"

find "$DIR" -name "*.java" -type f | while read -r archivo; do
	conteo=$(grep "^import " "$archivo" | sed  's/import //;s/;//g' |sort -u | wc -l)
	echo "$conteo" "$archivo"
done
