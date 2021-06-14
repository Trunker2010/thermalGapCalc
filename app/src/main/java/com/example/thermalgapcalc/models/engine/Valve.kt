package com.example.thermalgapcalc.models.engine

import javax.inject.Inject

class Valve @Inject constructor() {
    var state: ValveState? = null //большой или маленький зазор
    var washer = 0.0 //шайба
    var measuredGap = 0.0 //замер
}
