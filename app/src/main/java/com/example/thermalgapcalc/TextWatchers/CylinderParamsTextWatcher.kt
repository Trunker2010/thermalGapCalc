package com.example.thermalgapcalc.TextWatchers

import android.text.Editable
import android.text.TextWatcher
import com.example.thermalgapcalc.DEFAULT_PARAM
import com.example.thermalgapcalc.POINT_SIG

class CylinderParamsTextWatcher(val textChange: (pos: Int, CharSequence?, hashCode: Int) -> Unit) :
    TextWatcher {
    private var position: Int = 0

    fun updatePosition(position: Int) {
        this.position = position
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        val hasCode = s.hashCode()
        if (s!!.isNotEmpty() && s.toString() != POINT_SIG) {
            textChange(position, s, hasCode)
        } else {
            textChange(position, DEFAULT_PARAM, hasCode)
        }
    }
}