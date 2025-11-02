# Proyecto 3 - Máquina de Turing

## Descripción

El objetivo de este proyecto es implementar una Máquina de Turing que pueda simular la ejecución de una Máquina de Turing dada en un archivo YAML. Por motivos de familiaridad y por la orientación del lenguaje a trabajar de forma orientada a objetos se decidió trabajar con Kotlin.

## Video

[![Vista previa del video](https://img.youtube.com/vi/OytBxoeViFM/maxresdefault.jpg)](https://youtu.be/OytBxoeViFM)

## Estructura del proyecto

El proyecto se divide en dos partes:

1. La lectura del archivo YAML y la creación de la Máquina de Turing.
2. La simulación de la Máquina de Turing.

Los archivos utilizados se encuentran en `/input_files`, aquí se tieen el `disruptor.yaml` y `recognizer.yaml`. Estos tienen la estructura formal de la máquina como se solicitó en el documento del proyecto. El procesamiento de los mismos se realiza en `/io/ProcessYaml.kt`.

Los modelos a utilizar se encuentran en `/models`, aquí se tiene el `TuringMachine.kt` y `Transition.kt`. En `TuringMachine.kt`, se tiene la representación completa de las partes de una Máquina de Turing, por otro lado, en `Transition.kt`, se tiene un data class para representar todas las partes de una transición, tanto lo que se espera como entrada, como la salida que se da (esto involucra interacción con el estado, cinta y cache).

En `/sim` se tiene la lógica para en base a las características de la máquina dada, simular la ejecución de la máquina dada una cadena de entrada. Aquí nos encargamos de manejar los cambios en la cita y la interacción que se tiene con ella (desplazamiento hacia izquierda o derecha).

En `/utils` se tiene la lógica para validar las transiciones dadas en el archivo YAML, básicamente se utiliza para conocer si existe alguna transición que reciba como entrada el estado en el que nos encontramos, lo que se tiene en cache, y lo que se lee en la cinta. De ser así, retorna la transición a utilizar, de lo contrario devuelve **null**.

Por último, en `/main` se tiene la lógica para ejecutar el programa, aquí se maneja la lectura del archivo YAML, la creación de la Máquina de Turing y la simulación de la Máquina de Turing.

## Interacción con Proyecto

Para ejecutar el proyecto, se debe ejecutar los siguientes comandos:

```bash
cd Turing
./gradlew clean build
./gradlew run
```

Tomar en cuenta, que dependiendo la máquina que se quiera ejecutar, se debe cambiar el archivo de entrada en el archivo `Main.kt`.