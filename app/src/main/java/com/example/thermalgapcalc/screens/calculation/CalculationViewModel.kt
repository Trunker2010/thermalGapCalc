package com.example.thermalgapcalc.screens.calculation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.thermalgapcalc.models.engine.Engine
import com.example.thermalgapcalc.models.engine.ValveState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalculationViewModel @Inject constructor(val engine: Engine) : ViewModel() {
    private val exMin = Engine.exGapParams - Engine.exTolerances
    private val exMax = Engine.exGapParams + Engine.exTolerances
    private val inMin = Engine.inGapParams - Engine.inTolerances
    private val inMax = Engine.inGapParams + Engine.inTolerances

    fun calcState() {
        calcExValveState()
        calcInValveState()
    }

    private fun calcInValveState() {
        for (cylinder in engine.cylindersList) {
            for (inValve in cylinder.inValveList) {
                when {
                    inValve.measuredGap >= inMax -> {
                        inValve.state = ValveState.BIG
                    }
                    inValve.measuredGap <= inMin -> {
                        inValve.state = ValveState.SMALL
                    }
                    else -> {
                        inValve.state = ValveState.NORMAL
                    }
                }
            }
        }
    }

    private fun calcExValveState() {
        for (cylinder in engine.cylindersList) {
            for (exValve in cylinder.exValveList) {
                when {
                    exValve.measuredGap >= exMax -> {
                        exValve.state = ValveState.BIG
                    }
                    exValve.measuredGap <= exMin -> {
                        exValve.state = ValveState.SMALL
                    }
                    else -> {
                        exValve.state = ValveState.NORMAL
                    }
                }
            }
        }
    }
}
