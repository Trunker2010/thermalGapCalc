package com.example.thermalgapcalc.screens.calculation

import androidx.lifecycle.ViewModel
import com.example.thermalgapcalc.models.engine.Engine
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalculationViewModel @Inject constructor(val engine: Engine) : ViewModel() {
}