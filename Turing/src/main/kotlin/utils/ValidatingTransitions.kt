package utils.validatetransitions

import models.transition.Transition
import models.transition.isValid

fun validatingTransitions(
    transitions: List<Transition>,
    inputState: String,
    inputCache: String,
    inputTape: String,
): Transition?{
    for(transition in transitions){
        if(transition.isValid(inputState, inputCache, inputTape)){
            return transition
        }
    }
    return null
}
