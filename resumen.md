# Resumen de Teoría — Sistemas Operativos I

---

## 1. Race Condition

**Qué es:** Dos o más threads acceden a un dato compartido sin sincronización. El resultado depende del orden de ejecución (no determinístico).

**Cómo detectarlo:**
- Resultados inconsistentes o distintos entre corridas.
- Elementos duplicados, valores null, contadores incorrectos.
- Desaparece al agregar prints (porque cambia el timing).

**Ejemplo:** Dos threads leen `posNext = 3`, ambos escriben en posición 3, uno pisa al otro. O ambos leen `element != null`, ambos consumen el mismo elemento.

**Solución:** `synchronized` en todos los métodos que accedan al dato compartido.

---

## 2. Busy-Wait

**Qué es:** Un thread espera una condición girando en un loop vacío sin ceder la CPU.

```java
while (element == null) { } // busy-wait
```

**Problema:** El hilo consume CPU activamente sin hacer trabajo útil. Otros hilos (como el productor) tienen menos CPU disponible.

**Solución:** Reemplazar con `wait()` dentro de un bloque `synchronized`.

```java
synchronized (this) {
    while (element == null) {
        this.wait(); // libera el lock y duerme
    }
}
```

---

## 3. synchronized, wait(), notifyAll()

### synchronized
- Solo un thread a la vez puede ejecutar un bloque/método `synchronized` sobre el mismo objeto.
- Al salir del bloque, garantiza visibilidad (happens-before): todo lo escrito es visible para el próximo thread que adquiera el lock.

### wait()
- Solo se puede llamar dentro de un bloque `synchronized`.
- **Libera el lock** y pone el thread a dormir.
- El thread se despierta cuando alguien llama `notify()` o `notifyAll()` sobre el mismo objeto.
- Siempre usar dentro de un `while`, nunca `if` (puede haber spurious wakeups o el thread incorrecto se despierta).

```java
while (!condition) {
    this.wait();
}
```

### notify() vs notifyAll()
- `notify()`: despierta un thread arbitrario del wait set. Peligroso si hay productores y consumidores esperando en el mismo objeto — puede despertar al tipo incorrecto.
- `notifyAll()`: despierta a todos. Cada uno verifica su condición y solo el correcto avanza. **Preferir siempre notifyAll().**

**Caso problemático con notify():** buffer vacío, 2 consumidores en wait, productor agrega elemento y llama `notify()`. Despierta consumidor A → consume → llama `notify()` → despierta consumidor B (no al productor). B ve buffer vacío, vuelve a wait. Bloqueo indefinido.

---

## 4. volatile vs synchronized

### volatile
- Fuerza que lecturas y escrituras vayan directo a memoria principal (no caché local del núcleo).
- NO garantiza atomicidad de operaciones compuestas (ej: `i++`).
- Útil para variables leídas por métodos NO sincronizados.

### synchronized
- Garantiza exclusión mutua Y visibilidad (happens-before al salir del bloque).
- `volatile` es redundante en métodos `synchronized`.
- Para métodos que NO son `synchronized` (como `size()`), `volatile` sí tiene efecto.

---

## 5. Starvation vs Deadlock

| | Starvation | Deadlock |
|---|---|---|
| Qué pasa | Un thread nunca obtiene el recurso | Múltiples threads se bloquean mutuamente |
| Por qué | Otros siempre tienen prioridad, o nadie libera | Ciclo de espera — A espera a B, B espera a A |
| ¿Hay ciclo? | No necesariamente | Sí, siempre |
| ¿Puede resolverse solo? | A veces | No |

---

## 6. Deadlock

**Definición:** Ciclo de espera mutua entre dos o más threads donde cada uno retiene un recurso que el otro necesita, y ninguno puede avanzar ni liberar.

### Las 4 condiciones (deben cumplirse TODAS)

| Condición | Descripción |
|---|---|
| **Exclusión mutua** | El recurso solo puede usarlo un thread a la vez |
| **Hold and Wait** | Un thread retiene recursos mientras espera otros |
| **No preempción** | Los recursos no pueden quitarse a la fuerza; solo se liberan voluntariamente |
| **Espera circular** | Existe un ciclo: A espera a B, B espera a C, C espera a A |

**Para evitar deadlock:** basta con eliminar UNA de las cuatro condiciones.

### Ejemplo real: alta laboral
- Para registrarte en el trabajo necesitás ID de obra social.
- Para tener obra social necesitás recibo de sueldo del trabajo.
- Ciclo → deadlock.

### Cómo detectarlo
- Procesos bloqueados indefinidamente esperando recursos entre sí.
- Grafo de asignación de recursos con ciclo.

### Soluciones

**1. Ordenamiento de recursos (rompe espera circular)**
Todos los threads adquieren los recursos siempre en el mismo orden (ej: por ID ascendente). Así nunca puede formarse un ciclo.

**2. No retener mientras se espera — Hold and Wait (rompe hold and wait)**
Un thread pide todos los recursos que necesita de una vez, o libera lo que tiene antes de pedir más.

**3. Preempción (rompe no preempción)**
El SO puede quitarle recursos a un proceso si hay deadlock. Poco práctico en general.

**4. Prevención de espera circular**
Igual que ordenamiento de recursos — si el orden es global, no hay ciclo posible.

---

## 7. Algoritmo del Banquero (pendiente de desarrollar)

Permite verificar si un estado es **seguro** — si existe alguna secuencia en la que todos los procesos pueden terminar.

- **Need = Max − Asignación** (lo que le falta a cada proceso para llegar a su máximo)
- **Disponible**: recursos actualmente libres
- Un estado es seguro si existe una secuencia P_i tal que cada proceso puede terminar con los recursos disponibles en ese momento, y al terminar libera los suyos para el siguiente.

*(Desarrollo completo pendiente — ejercicio 4 del TP4)*
