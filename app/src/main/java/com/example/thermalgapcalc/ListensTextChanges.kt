package com.example.thermalgapcalc


import com.example.thermalgapcalc.TextWatchers.CylinderParamsTextWatcher

interface ListensTextChanges {
    fun updatePosition(pos:Int)
    fun setTextWatcher(cylinderParamsTextWatcher: CylinderParamsTextWatcher)
    fun enableTextWatcher()
    fun disableTextWatcher()
}