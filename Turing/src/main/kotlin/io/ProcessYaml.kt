package io.yaml

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.nio.file.Files
import java.nio.file.Paths

import models.turingmachine.TuringMachine
import models.transition.Transition

// DTOs para manejar el .yaml
data class QStates(
    @JsonProperty("q_list") val qList: List<String> = emptyList(),
    val initial: String = "",
    val final: String = ""
)

// Params de Entrada Transicion
data class Params(
    @JsonProperty("initial_state") val initialState: String,
    @JsonProperty("mem_cache_value") val memCacheValue: String,
    @JsonProperty("tape_input") val tapeInput: String
)

// Params de Salida Transicion
data class Output(
    @JsonProperty("final_state") val finalState: String,
    @JsonProperty("mem_cache_value") val memCacheValue: String,
    @JsonProperty("tape_output") val tapeOutput: String,
    @JsonProperty("tape_displacement") val tapeDisplacement: String
)

// Regla 'delta' (funcion transicion)
data class DeltaRule(
    val params: Params,
    val output: Output
)

data class Machine(
    @JsonProperty("q_states") val qStates: QStates,
    @JsonProperty("alphabet") val alphabet: List<String> = emptyList(),
    @JsonProperty("tape_alphabet") val tapeAlphabet: List<String> = emptyList(),
    @JsonProperty("delta") val delta: List<DeltaRule> = emptyList(),
    @JsonProperty("simulation_strings") val simulationStrings: List<String> = emptyList()
)

object MachineLoader {
    private val mapper: ObjectMapper = ObjectMapper(YAMLFactory()).apply {
        registerModule(KotlinModule.Builder().build())
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
    }

    // Lee el input file, y devuelve la MT y los strings a simular
    fun loadMachineYaml(
        path: String
    ) : Pair<TuringMachine, List<String>> {
        val path = Paths.get(path)
        require(Files.exists(path)) { "El archivo $path no existe" }
        
        val yaml = Files.newBufferedReader(path).use { reader ->
            mapper.readValue(reader, Machine::class.java)
        }

        // Mapear a Transition desde Delta
        val transitions = yaml.delta.map { rule ->
            Transition(
                inputState = rule.params.initialState,
                inputCache = rule.params.memCacheValue,
                inputTape = rule.params.tapeInput,
                outputState = rule.output.finalState,
                outputCache = rule.output.memCacheValue,
                outputTape = rule.output.tapeOutput,
                direction = rule.output.tapeDisplacement
            )
        }

        val turingMachine = TuringMachine(
            states = yaml.qStates.qList,
            entryAlphabet = yaml.alphabet,
            tapeAlphabet = yaml.tapeAlphabet,
            transitions = transitions,
            initialState = yaml.qStates.initial,
            finalState = yaml.qStates.final
        )

        return Pair(turingMachine, yaml.simulationStrings)
    }
}