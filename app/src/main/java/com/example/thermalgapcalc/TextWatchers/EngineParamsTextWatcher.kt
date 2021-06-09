package com.example.thermalgapcalc.TextWatchers

import android.text.Editable
import android.text.TextWatcher
import com.example.thermalgapcalc.DEFAULT_PARAM
import com.example.thermalgapcalc.POINT_SIG

class EngineParamsTextWatcher(val textChange: (CharSequence?) -> Unit) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        if (s!!.isNotEmpty() && s.toString() != POINT_SIG) {
            textChange(s)
        }else{
            textChange(DEFAULT_PARAM)
        }
    }
}