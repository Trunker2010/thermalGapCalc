package com.example.thermalgapcalc.models.engine

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Engine @Inject constructor() {
    companion object {
        var cylinderCount = 0
        var exTolerances = 0.0
        var inTolerances = 0.0
        var exGapParams = 0.0
        var inGapParams = 0.0
    }

    var cylindersList: MutableList<Cylinder> = mutableListOf()
}