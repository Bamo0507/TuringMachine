import io.yaml.MachineLoader.loadMachineYaml
import sim.simulatemachine.simulateMachine

fun main(){
    val (turingMachine, simulationStrings) 
        = loadMachineYaml("src/main/kotlin/input_files/disruptor.yaml")

    // Simular Maquina
    for (string in simulationStrings) {
        val result = simulateMachine(turingMachine, string)
        println("Prueba: $string")
        println("Aceptado: ${result.accepted}")
        println("Cinta Final (sin Blanks): ${result.outputTape.replace("B", "")}")
        
        var counter = 0
        for(instantDescription in result.instantDescriptions){
            println("$counter: $instantDescription")
            counter++
        }

        println("\n")
    }
}