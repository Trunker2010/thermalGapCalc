package com.example.thermalgapcalc.models.engine

import javax.inject.Inject

class Engine @Inject constructor() {
    companion object {
        var inTolerances = 0.0
        var inGapParams = 0.0
        var exGapParams = 0.0
        var exTolerances = 0.0
    }

    var cylindersList: MutableList<Cylinder> = mutableListOf()


}