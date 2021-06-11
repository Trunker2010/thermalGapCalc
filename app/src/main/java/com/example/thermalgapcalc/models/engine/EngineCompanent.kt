package com.example.thermalgapcalc

import com.example.thermalgapcalc.models.engine.Engine
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface EngineComponent {
    fun getEngine(): Engine
}