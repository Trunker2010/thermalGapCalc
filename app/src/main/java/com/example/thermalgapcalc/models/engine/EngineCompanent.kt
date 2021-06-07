package com.example.thermalgapcalc

import com.example.thermalgapcalc.models.engine.Engine
import dagger.Component

@Component
interface EngineComponent {
    fun getEngine(): Engine
}