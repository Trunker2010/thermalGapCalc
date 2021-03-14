package com.example.thermalgapcalc.screens.calc

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.thermalgapcalc.models.Cylinder
import com.example.thermalgapcalc.DaggerEngineComponent
import com.example.thermalgapcalc.models.Engine
import com.example.thermalgapcalc.models.Valve

class CalcViewModel(app: Application) : AndroidViewModel(app) {
    var engine: Engine
    private var cylinderCount = 0
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
                    Log.d("setValveCount", count.toString())
                    field = 2

                }


                4 -> {
                    Log.d("setValveCount", count.toString())
                    field = 4
                }

            }
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


    fun setCylinderCount(count: Int) {
        cylinderCount = count

        engine.cylindersList = mutableListOf()
        do {
            engine.cylindersList.add(Cylinder())
        } while (engine.cylindersList.size != count)
        Log.d("setCylinderCount", engine.cylindersList.size.toString())


        setInValve(valveCount)
        setExValve(valveCount)

        updateGapsParams()
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