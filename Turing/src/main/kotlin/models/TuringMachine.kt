package models.turingmachine

import models.transition.Transition

data class TuringMachine(
    val states: List<String>,
    val entryAlphabet: List<String>,
    val tapeAlphabet: List<String>,
    val transitions: List<Transition>,
    val initialState: String,
    val finalState: String,
)
