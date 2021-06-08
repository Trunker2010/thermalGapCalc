package com.example.thermalgapcalc.screens.calc

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.thermalgapcalc.models.engine.Cylinder
import com.example.thermalgapcalc.DaggerEngineComponent
import com.example.thermalgapcalc.models.engine.Engine
import com.example.thermalgapcalc.models.engine.Valve

class CalcViewModel(app: Application) : AndroidViewModel(app) {
    var engine: Engine
    var cylinderCount = 0
    var inGapParams = 0.00
    var exGapParams = 0.00
    var inTolerances = 0.00
    var exTolerances = 0.00

    init {
        val engineComponent = DaggerEngineComponent.create()
        engine = engineComponent.getEngine()
    }

    var valveCount = 0
        set(count) {
            when (count) {
                2 -> {
                    field = 2
                    Log.d("setValveCount", count.toString())
                }
                4 -> {
                    field = 4
                    Log.d("setValveCount", count.toString())
                }
            }
            updateCylinderCount(cylinderCount)
            setInValve(valveCount)
            setExValve(valveCount)
        }

    fun setInValve(count: Int) {

        for (cylinder in engine.cylindersList) {
            var inValveCount = count / 2
            cylinder.inValveList.clear()
            do {

                cylinder.inValveList.add(Valve())
                inValveCount--
            } while (inValveCount != 0)
            Log.d("setInValve", cylinder.inValveList.size.toString())
        }

        updateInTolerances(inTolerances)
        updateInGapParams(inGapParams)
    }

    fun setExValve(count: Int) {

        for (cylinder in engine.cylindersList) {
            var exValveCount = count / 2
            cylinder.exValveList.clear()
            do {
                cylinder.exValveList.add(Valve())
                exValveCount--
            } while (exValveCount != 0)
        }
        updateExGapParams(exGapParams)
        updateExTolerances(exTolerances)
    }

    fun updateCylinderCount(count: Int) {
//если прибавить цилиндры
        if (count > cylinderCount) {
            val countDif = count - cylinderCount
            addCylinders(countDif)
        }
        if (count < cylinderCount) {
            removeCylinders(count)
        }

        cylinderCount = count
        setInValve(valveCount)
        setExValve(valveCount)
        updateGapsParams()
    }

    private fun addCylinders(count: Int) {
        do {
            engine.cylindersList.add(Cylinder())
        } while (engine.cylindersList.size != count + cylinderCount)
    }

    private fun removeCylinders(count: Int) {
        do {
            engine.cylindersList.removeLast()
        } while (engine.cylindersList.size > count)
    }

    private fun updateGapsParams() {
        updateInTolerances(inTolerances)
        updateInGapParams(inGapParams)
        updateExGapParams(exGapParams)
        updateExTolerances(exTolerances)
    }

    fun updateInGapParams(gap: Double) {
        inGapParams = gap
        for (cylinder in engine.cylindersList) {
            for (valve in cylinder.inValveList) {
                valve.gap = gap
            }
        }
    }

    fun updateExGapParams(gap: Double) {
        exGapParams = gap
        for (cylinder in engine.cylindersList) {
            for (valve in cylinder.exValveList) {
                valve.gap = gap
            }
        }
    }

    fun updateInTolerances(tolerances: Double) {
        inTolerances = tolerances
        for (cylinder in engine.cylindersList) {
            for (valve in cylinder.inValveList) {
                valve.tolerance = tolerances
            }
        }
    }

    fun updateExTolerances(tolerances: Double) {
        exTolerances = tolerances
        for (cylinder in engine.cylindersList) {
            for (valve in cylinder.exValveList) {
                valve.tolerance = tolerances
            }
        }
    }
}