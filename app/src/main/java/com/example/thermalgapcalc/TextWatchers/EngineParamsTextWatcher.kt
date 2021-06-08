package com.example.thermalgapcalc.TextWatchers

import android.text.Editable
import android.text.TextWatcher

class EngineParamsTextWatcher(val textChange: (CharSequence?) -> Unit) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        textChange(s)
    }

    override fun afterTextChanged(s: Editable?) {

    }
}