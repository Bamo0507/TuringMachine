package sim.simulatemachine

import models.turingmachine.TuringMachine
import models.transition.Transition
import utils.validatetransitions.validatingTransitions

// Resultado esperado de simular maquina
data class SimulationResult(
    val instantDescriptions: List<String>,
    val accepted: Boolean,
    val outputTape: String,
)

// Devuelve una lista de IDs y un booleano indicando si se acepta o no
fun simulateMachine(
    turingMachine: TuringMachine,
    inputString: String,
) : SimulationResult {
    // Valores iniciales -------------------
    // construir cinta
    val tape = mutableListOf<String>()
    var tapeIndex = 1 // comienza en 1 por los blanks que se pondran
    
    // poner B en inicio
    tape.add("B")
    
    // agregar simbolos de entrada
    for (symbol in inputString) {
        tape.add(symbol.toString())
    }
    
    // poner B en final
    tape.add("B")
    
    var state = turingMachine.initialState

    // Cache
    var cache = "B"

    var instantDescriptions = mutableListOf<String>()

    // loop simulando turing machine
    while(true){
        // Crear instant description
        var instantDescription = tape.toMutableList()
        instantDescription.add(tapeIndex, "[$state, $cache]")

        instantDescriptions.add(instantDescription.joinToString(" ") { it })
        
        var currentTransition = validatingTransitions(
            transitions = turingMachine.transitions,
            inputState = state,
            inputCache = cache,
            inputTape = tape[tapeIndex]
        )

        // si no hay transicion, sale
        if(currentTransition == null){
            break
        }

        // aplicar transición
        tape[tapeIndex] = currentTransition.outputTape
        cache = currentTransition.outputCache
        state = currentTransition.outputState

        // si es final, salimos
        if (state == turingMachine.finalState) {
            val lastId = tape.toMutableList()
            lastId.add(tapeIndex, "[$state, $cache]")
            instantDescriptions.add(lastId.joinToString(" "))
            break
        }

        // mover cabeza
        if (currentTransition.direction == "R") {
            tapeIndex++
            if (tapeIndex >= tape.size) tape.add("B")
        } else { // "L"
            tapeIndex--
            if (tapeIndex < 0) { tape.add(0, "B"); tapeIndex = 0 }
        }
    }

    // unir la cinta en una sola cadena
    val tapeResult = tape.joinToString("") { it }

    val accepted = state == turingMachine.finalState
    
    return SimulationResult(instantDescriptions, accepted, tapeResult)
}