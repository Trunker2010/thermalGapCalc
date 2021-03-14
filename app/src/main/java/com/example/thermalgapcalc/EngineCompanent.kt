package com.example.thermalgapcalc

import com.example.thermalgapcalc.models.Engine
import dagger.Component

@Component
interface EngineComponent {
    fun getEngine(): Engine
}