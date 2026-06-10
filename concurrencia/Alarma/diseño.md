# Alarma Hogareña — Diseño Concurrente

## Actores (hilos)
- **N SensorMagnetico** — detecta apertura, notifica a la alarma
- **M SensorMovimiento** — detecta movimiento, notifica a la alarma
- **1 Alarma** — hilo central, espera eventos y gestiona estado
- **1 Dueño** — activa/desactiva la alarma

## Estado compartido (en objeto Alarma, protegido con synchronized)
- `boolean magneticoDisparado` — se puso true si algún sensor magnético se disparó
- `int contMovimiento` — cuántos sensores de movimiento se dispararon
- `Estado estado` — enum: DESACTIVADA / ACTIVADA / ALERTADA

## Condiciones de disparo
- 1 sensor magnético disparado → alarma se alerta
- 2 o más sensores de movimiento disparados → alarma se alerta

## Métodos del monitor Alarma
- `synchronized activarSensorMagnetico()` → magneticoDisparado = true, notifyAll()
- `synchronized activarSensorMovimiento()` → contMovimiento++, notifyAll()
- `synchronized activar()` → estado = ACTIVADA, reset booleano y contador, notifyAll()
- `synchronized desactivar()` → estado = DESACTIVADA, notifyAll()

## Pseudocódigo del hilo Alarma (run)

```
synchronized(this):
    // Fase 1: esperar que haya algo para procesar (y que esté activada)
    while (estado != ACTIVADA || (!magneticoDisparado && contMovimiento < 2)):
        wait()

    // Fase 2: alertar
    estado = ALERTADA

    // Fase 3: esperar a que el dueño desactive
    while (estado == ALERTADA):
        wait()

// volver al inicio del ciclo
```

## Pseudocódigo SensorMagnetico (run)
```
while (true):
    simular tiempo aleatorio de espera
    alarma.activarSensorMagnetico()
```

## Pseudocódigo SensorMovimiento (run)
```
while (true):
    simular tiempo aleatorio de espera
    alarma.activarSensorMovimiento()
```

## Pseudocódigo Dueño (run)
```
while (true):
    simular uso (ej: esperar, luego activar/desactivar)
    alarma.activar() / alarma.desactivar()
```

## Propiedades garantizadas
- **Sin busy wait**: la alarma bloquea con wait() en lugar de loopearse
- **Sin race condition**: todo acceso a estado compartido es synchronized
- **Sin deadlock**: un solo lock (objeto Alarma), sin espera circular posible
