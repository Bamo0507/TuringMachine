import io.yaml.MachineLoader.loadMachineYaml
import sim.simulatemachine.simulateMachine

fun main(){
    val (turingMachineRecognizer, simulationStringsRecognizer) 
        = loadMachineYaml("src/main/kotlin/input_files/disruptor.yaml")

    // Simular Reconocedora
    for (string in simulationStringsRecognizer) {
        val result = simulateMachine(turingMachineRecognizer, string)
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