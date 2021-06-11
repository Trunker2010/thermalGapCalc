package com.example.thermalgapcalc.models.engine

import javax.inject.Inject

class Cylinder @Inject constructor() {
    var inValveList = mutableListOf<Valve>()
    var exValveList = mutableListOf<Valve>()
}
