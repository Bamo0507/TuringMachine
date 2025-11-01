package models.transition

data class Transition(
    val inputState: String,
    val inputCache: String,
    val inputTape: String,
    val outputState: String,
    val outputCache: String,
    val outputTape: String, // Lo que termino escribiendo en la cinta,
    val direction: String, // L -> Left, R -> Right
)

// Extension Function para validar rápidamente quién es la transición válida
fun Transition.isValid(
    inputState: String,
    inputCache: String,
    inputTape: String,
): Boolean {
    return (
        this.inputState == inputState &&
        this.inputCache == inputCache &&
        this.inputTape == inputTape
    )
}
