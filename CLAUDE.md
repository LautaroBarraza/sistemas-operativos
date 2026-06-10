# Sistemas Operativos — Contexto de sesión

## Materia
Sistemas Operativos I — UNICEN, Ingeniería de Sistemas.

## TP actual: TP4 — Deadlocks y programación concurrente

PDF: `pract4_2021.pdf`

## TP3 (ProdCons) — COMPLETADO

Código en: `concurrencia/ProdCons/src/edu/isistan/buffer/`

Para compilar y correr (javac no está en PATH, usar el del VS Code):
```bash
JAVAC=/home/atreus/.vscode/extensions/redhat.java-1.54.0-linux-x64/jre/21.0.10-linux-x86_64/bin/javac
JAVA=/home/atreus/.vscode/extensions/redhat.java-1.54.0-linux-x64/jre/21.0.10-linux-x86_64/bin/java
cd /home/atreus/workspace/sistemas_operativos/concurrencia/ProdCons
$JAVAC -d bin $(find src -name "*.java")
$JAVA -cp bin edu.isistan.buffer.ProdConsMain
```

Los parámetros se modifican en `ProdConsMain.java` (waitConsumer, waitProducer, produce, consume, nProducers, nConsumers).

## Modo de trabajo
Método socrático con skill `learn.md`. No dar respuestas directas — guiar con preguntas. Dar un pie de apoyo solo si el alumno está genuinamente bloqueado.

---

## Progreso de la sesión

### Conceptos ya cubiertos

**Race condition en CircularBuffer:**
- `next()` y `add()` no tienen sincronización.
- Múltiples hilos pasan el `while` simultáneamente y acceden a la misma posición.
- Resultado: elementos duplicados consumidos, posiciones `null` leídas, `posNext` corrompido.

**Bloqueo indefinido (starvation):**
- Con la config 4 (1 productor, 10 consumidores): los consumidores corrompen `posNext` hasta que el productor cree que el buffer está lleno cuando no lo está. El productor queda bloqueado esperando que alguien consuma — pero todos los consumidores ya terminaron.
- Diferencia con deadlock: no hay ciclo de espera mutua, solo uno bloqueado esperando algo que no llegará.

**Solución identificada:**
- Usar `synchronized` en `next()` y `add()`, con el buffer como lock (`this`).
- Reemplazar el busy-wait (`while { }`) por `wait()` dentro del bloque sincronizado.
- Usar `notifyAll()` (no `notify()`) al agregar o consumir un elemento.

**Por qué notifyAll() y no notify():**
- `notify()` despierta un hilo arbitrario del wait set.
- Si hay productores y consumidores esperando en el mismo objeto, `notify()` puede despertar al tipo incorrecto.
- Caso concreto: buffer vacío, 2 consumidores en wait, productor agrega elemento y llama `notify()`. Despierta consumidor A, que toma el elemento y llama `notify()` — despierta consumidor B (no a un productor). B ve buffer vacío, vuelve a wait. Bloqueo indefinido.
- `notifyAll()` despierta a todos; cada uno verifica su condición y solo el correcto avanza.

**CircularBuffer ya corregido:**
- Se agregó `synchronized` + `wait()` + `notifyAll()` en `next()` y `add()`.
- Se corrigió el bug en la condición de buffer lleno en `add()`: era `(posNext+1)%N == lastElem` (detectaba 1 elemento, no buffer lleno). Correcto: `(lastElem+1)%N == posNext`.
- `volatile` en los campos es redundante para métodos `synchronized`, pero sí importa para `size()` y `maxElements()` que no están sincronizados.

**Configs 1–6 corridas y verificadas con CircularBuffer corregido:**
- Config 1 (misma velocidad, 1v1): buffer con 1-2 elementos, termina OK.
- Config 2 (productor 10x más lento): consumidor espera casi siempre, termina OK.
- Config 3 (consumidor 10x más lento): buffer se llena, productor espera, termina OK.
- Config 4 (1 prod, 10 cons): solo 1 consumidor activo a la vez, los otros en wait().
- Config 5 (10 prod, 1 cons): buffer se llena, productores esperan.
- Config 6 (10v10, misma velocidad): termina correctamente.

**volatile vs synchronized:**
- `synchronized` garantiza visibilidad al salir del bloque (happens-before) — `volatile` es redundante en métodos sync.
- `size()` y `maxElements()` no son `synchronized`, por eso `volatile` sí tiene efecto ahí.

---

## TP4 — Pendiente

### Preguntas teóricas
1. Definición de deadlock + 4 condiciones + ejemplo real — **COMPLETO**
   - Deadlock: ciclo de espera mutua entre threads, ninguno puede avanzar ni liberar.
   - 4 condiciones: exclusión mutua, hold and wait, no preempción, espera circular.
   - Ejemplo: alta laboral necesita obra social, obra social necesita recibo de sueldo.

2a. 6 drives, n procesos, 2 drives cada uno — **COMPLETO**
   - Libre de deadlock si n ≤ 5. Con n=6: 6 procesos × 1 drive = 6 ocupados, ninguno libre → deadlock.

2b. 4 recursos, 3 procesos, máx 2 cada uno — **COMPLETO**
   - Estado seguro: 3×1 = 3 ocupados, 1 libre → algún proceso puede terminar.

3. Sistema bancario, transferencias — **COMPLETO**
   - Sí puede haber deadlock: A bloquea cuenta1 y espera cuenta2, B bloquea cuenta2 y espera cuenta1 → ciclo.
   - Soluciones: (a) ordenamiento de recursos (rompe espera circular), (b) no retener ambas cuentas simultáneamente (rompe hold and wait).

4. Algoritmo del banquero — **EN CURSO**
   - Need = Max - Asignación (por calcular)
   - **Próxima sesión empieza acá:** el alumno no vio el algoritmo del banquero en clase. Explicar Need primero, luego el algoritmo de seguridad (secuencia segura), luego resolver la tabla del ejercicio.

### Programación con semáforos
5a. Semáforo contador: N procesos concurrentes — **pendiente**
5b. Semáforo contador: bloquear máx N, retornar booleano — **pendiente**
6. El barbero dormilón — **pendiente**
7. Readers-Writers con semáforos — **pendiente**
8. Corrector ortográfico concurrente — **pendiente**
9. Supermercado Baratija — **pendiente**
10. Blancanieves y los 7 enanitos — **pendiente**
11. Club de fabricantes de vino — **pendiente**
