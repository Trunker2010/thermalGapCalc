package com.example.thermalgapcalc.models.engine

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface EngineComponent {
    fun getEngine(): Engine
}