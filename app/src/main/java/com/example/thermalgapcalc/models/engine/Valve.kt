package com.example.thermalgapcalc.models.engine

import javax.inject.Inject

class Valve @Inject constructor() {
    var washer = 0.0 //шайба
    var gap = 0.0 //допустимый зазор
    var measuredGap = 0.0
    var tolerance = 0.00//погрешность

}
