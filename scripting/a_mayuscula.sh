ARCHIVO=$1
TEMP_FILE="${ARCHIVO}.tmp"

# Verifica cantidad exacta de argumentos recibidos
if [[ $# -ne 1 ]]; then
    echo "Error crítico: Faltan argumentos." >&2 # Redirección hacia STDERR
    mostrar_uso
    exit 1
fi

# Convierte a mayúsculas y guarda en un archivo temporal
tr '[:lower:]' '[:upper:]' < "$ARCHIVO"

