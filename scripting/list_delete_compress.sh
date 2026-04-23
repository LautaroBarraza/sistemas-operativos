
# 1. Creación segura del archivo y garantía de destrucción (Trap)
log_file="mi_script.log"
archivo_temp=$(mktemp)
trap 'rm -f "$archivo_temp"' EXIT

# 2. Ejecución y volcado directo a disco
find ./ -size +1000k -print0 > "$archivo_temp"

# 3. Lectura controlada mediante un Descriptor de Archivo alternativo (FD 3)
while IFS= read -r -d '' -u 3 linea; do
    echo "$linea"
    
    # La entrada estándar (FD 0) está libre; el teclado responde perfectamente
    read -p "inserte borrar_archivo o comprimir_archivo: " comando

    case "$comando" in
        "borrar_archivo")
			echo "$(date '+%Y-%m-%d %H:%M:%S') - Eliminado: $linea" >> "$log_file"
            rm "$linea"
            echo "se borro"
            
            
            ;;
        "comprimir_archivo")
            gzip "$linea"
            echo "se comprimio"
            ;;
        *)
            echo "Error: Entrada no autorizada o comando inexistente." >&2
            exit 1
            ;;
    esac
done 3< "$archivo_temp"
